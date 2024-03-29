package cn.udesk.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import java.io.File;

import cn.udesk.R;
import cn.udesk.camera.callback.ErrorListener;
import cn.udesk.camera.callback.UdeskCameraListener;
import udesk.core.UdeskConst;
import udesk.core.utils.UdeskUtils;

/**
 * Created by user on 2018/3/10.
 */

public class UdeskCameraActivity extends Activity {

    UdeskCameraView udeskCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udesk_activity_small_camera);
        initView();
    }


    private void initView() {
        udeskCameraView = (UdeskCameraView) findViewById(R.id.udesk_cameraview);
        //设置视频保存路径
        udeskCameraView.setSaveVideoPath(UdeskUtils.getDirectoryPath(getApplicationContext(), UdeskConst.FileVideo));
        //设置只能录像或只能拍照或两种都可以（默认两种都可以）
        udeskCameraView.setFeatures(UdeskCameraView.BUTTON_STATE_BOTH);

        udeskCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                Log.i("udesksdk", "open camera error");

                Intent mIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putBoolean(UdeskConst.Camera_Error, true);
                mIntent.putExtra(UdeskConst.SEND_BUNDLE, bundle);
                UdeskCameraActivity.this.setResult(Activity.RESULT_OK, mIntent);
                UdeskCameraActivity.this.finish();
            }

            @Override
            public void AudioPermissionError() {
                Log.i("udesksdk", "AudioPermissionError");
                Toast.makeText(UdeskCameraActivity.this.getApplicationContext(), getString(R.string.udesk_audio_permission_error), Toast.LENGTH_SHORT).show();
            }
        });

        udeskCameraView.setCameraLisenter(new UdeskCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {

                if (bitmap != null) {
                    String path = UdeskUtils.saveBitmap(UdeskCameraActivity.this.getApplicationContext(), bitmap);
                    finishActivity(null, path);
                } else {
                    finish();
                }

            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                finishActivity(url, null);
            }
        });

        udeskCameraView.setCloseListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        udeskCameraView.onResume();
    }

    @Override
    protected void onPause() {
        udeskCameraView.onPause();
        super.onPause();

    }


    private void finishActivity(String url, String picturepath) {
        Intent mIntent = new Intent();
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(url)) {
            bundle.putString(UdeskConst.SEND_SMALL_VIDEO, UdeskConst.SMALL_VIDEO);
            bundle.putString(UdeskConst.PREVIEW_Video_Path, url);
        } else if (!TextUtils.isEmpty(picturepath)) {
            bundle.putString(UdeskConst.SEND_SMALL_VIDEO, UdeskConst.PICTURE);
            bundle.putString(UdeskConst.BitMapData, picturepath);
        }
        mIntent.putExtra(UdeskConst.SEND_BUNDLE, bundle);
        UdeskCameraActivity.this.setResult(Activity.RESULT_OK, mIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        udeskCameraView.ondestory();
        super.onDestroy();
    }
}
