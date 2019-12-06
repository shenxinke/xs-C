package com.xswq.consumer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import com.xswq.consumer.R;

/**
 * 版本更新弹框提示
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class TipsDialog extends Dialog {
	private Context context = null;
	private static LayoutInflater inflater;
	private static TipsDialog tipsDialog = null;

	public TipsDialog(Context context) {
		super(context);
		this.context = context;
	}

	public TipsDialog(Context context, int theme) {
		super(context, theme);
		inflater = LayoutInflater.from(context);
	}
	/**
	 * 
	 * @param context
	 *            activity上下�?
	 * @param mwidth
	 *            dialog宽度
	 * @param layout
	 *            dialog布局文件
	 * @param grivate
	 *            dialog位子
	 * @return
	 */
	public static TipsDialog creatTipsDialog(Context context, int mwidth,
                                             int layout, int grivate, int anim) {
		tipsDialog = new TipsDialog(context, R.style.tipsDialog_style);
		tipsDialog.setContentView(layout);
		Window w = tipsDialog.getWindow();
		// 添加动画
		w.setWindowAnimations(anim);
		// 此处可以设置dialog显示的位�?
		w.setGravity(grivate);
		// dialog显示的宽�?
		LayoutParams lay = tipsDialog.getWindow().getAttributes();
		lay.width = mwidth;
		return tipsDialog;
	}

	public void dialogDismiss() {
		tipsDialog.dismiss();
	}
}
