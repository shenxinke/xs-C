package com.xswq.consumer.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itheima.updatelib.PatchUtil;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.main.MainActivity;
import com.xswq.consumer.bean.NewMobileVersionBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.dialog.AbstractVersionCheckDialog;
import com.xswq.consumer.dialog.TipsDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 版本更新
 */
public class NewMobileVersionUtils {
    private Context context;
    private TipsDialog tipsDialog;
    private TextView progressText;
    private ProgressBar progressBar;
    private String writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static int loadingProgress;//下载进度数
    private Handler mHandler = new Handler();

    public NewMobileVersionUtils(Context context, NewMobileVersionBean.DataBean data, int appVersion) {
        this.context = context;

        int forcedUpdatin = data.getForcedUpdating();
        int incrementalUpdating = data.getIncrementalUpdating();
        String versionContent = data.getVersionContent();
        int version = data.getVersion();


        if (Const.INTEGER_1 == forcedUpdatin) {
            //强更
            if (version - appVersion <= 5 && incrementalUpdating == 1) {
                forcedUpdating(versionContent, appVersion + "_" + version + ".patch");
            } else {
                forcedUpdating(versionContent, null);
            }
        } else {
            //弱更
            if (version - appVersion <= 5 && incrementalUpdating == 1) {
                weakMore(versionContent, appVersion + "_" + version + ".patch");
            } else {
                weakMore(versionContent, null);
            }
        }
    }

    /**
     * 强更
     */
    private void forcedUpdating(String versionContent, String path) {
        remindVersionUpdateDialog(true, versionContent, path,true);
    }

    /**
     * 弱更
     */
    private void weakMore(String versionContent, String path) {
        remindVersionUpdateDialog(false, versionContent, path,false);
    }

    /**
     * 版本更新弹出框
     */
    private void remindVersionUpdateDialog(boolean flag, String versionContent, final String path,boolean isCompel) {
        if (tipsDialog == null) {
            AbstractVersionCheckDialog dialog = new AbstractVersionCheckDialog((Activity) context, flag, versionContent,isCompel) {
                @Override
                public void onSureClick() {
                    setDialog(Gravity.CENTER, (WindowUtils.getScreenWidth((Activity) context) / 6) * 5, R.layout.dialog_tips_mid, 0, path);
                }
            };
            dialog.showDialog();
        }
    }

    /**
     * 下载进度条
     */
    private void setDialog(int grivate, int width, int layout, int anim, String path) {
        tipsDialog = TipsDialog.creatTipsDialog(context, width, layout, grivate, anim);
        progressBar = tipsDialog.findViewById(R.id.down_pb);
        progressText = tipsDialog.findViewById(R.id.tvId);
        tipsDialog.setCancelable(false);
        tipsDialog.show();
        tipsDialog.setCanceledOnTouchOutside(false);
        getWritePermission(path);
    }

    /**
     * 获取读写权限
     */
    private void getWritePermission(String path) {
        if (EasyPermissions.hasPermissions(context, writePermission)) {
            //下载APK
            if (!TextUtils.isEmpty(path)) {
                uploadApkFile(ContactUrl.APK_INCREMENT_PATH + path, path, true);
            } else {
                uploadApkFile(ContactUrl.APK_PATH, ContactUrl.APP_NAME, false);
            }
        } else {
            EasyPermissions.requestPermissions((Activity) context, "请打开存储权限", 200, writePermission);
        }
    }

    /**
     * 下载APK文件
     */
    private void uploadApkFile(final String path, final String name, final boolean type) {
        OkHttpUtils.get().url(path)
                .tag(context)
                .build()
                .execute(new FileCallBack(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), name) {
                    @Override
                    public void inProgress(float v, long l, int id) {
                        loadingProgress = (int) (v * 100);
                        progressBar.setProgress(loadingProgress);
                        progressText.setText("下载中:" + loadingProgress + "%");
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("User", "e =" + e.getMessage());
                        ToastUtils.show("下载失败");
                        tipsDialog.dismiss();
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        tipsDialog.dismiss();
                        if (type) {
                            upApkFile(file);
                        } else {
                            installNewApk(file);
                        }
                    }
                });
    }

    /**
     * 合并增量安装包
     */
    private void upApkFile(final File patchFile) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), 0);
            final String oldPath = appInfo.sourceDir;

            final File newApkFile = new File(Environment.getExternalStorageDirectory(), ContactUrl.APP_NAME);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int result = PatchUtil.patch(oldPath, newApkFile.getAbsolutePath(), patchFile.getAbsolutePath());
                    if (result == 0) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                installNewApk(newApkFile);
                            }
                        });
                    }

                }
            }).start();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转安装
     */
    private void installNewApk(File apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        /**由于没有在Activity环境下启动Activity,设置下面的标签*/
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        /**判读版本是否在7.0以上*/
        if (Build.VERSION.SDK_INT >= Const.INTEGER_24) {
            /**参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件*/
            Uri apkUri = FileProvider.getUriForFile(context, "xswlpackgeName.fileProvider", apkPath);
            /**添加这一句表示对目标应用临时授权该Uri所代表的文件*/
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkPath), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
        mHandler.removeCallbacksAndMessages(null);
        ((Activity) context).finish();
    }

}
