package com.xswq.consumer.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * windows工具类
 */
public class WindowUtils {
	public static int EXACT_SCREEN_HEIGHT;
	public static int EXACT_SCREEN_WIDTH;
	/**
	 * 
	 * @return 获取屏幕密度比
	 */
	public static float getDesity(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
		if(activity!=null){
			activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		}
		// 屏幕密度（0.75 / 1.0 / 1.5）
		float density = metric.density;
		return density;
	}

	/**
	 * 
	 * @return 获取屏幕宽
	 */
	public static int getScreenWidth(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
		if(activity!=null){
			activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		}
		return metric.widthPixels;
	}

	/**
	 * 
	 * @return 获取屏幕高
	 */
	public static int getScreenHeight(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
		if(activity!=null){
			activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
			EXACT_SCREEN_HEIGHT = metric.heightPixels;
			EXACT_SCREEN_WIDTH = metric.widthPixels;
		}
		return metric.heightPixels;
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param context
	 * @param pxValue
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值
	 * 
	 */
	public int dp2px(int dpVal, Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param context
	 * @param dipValue
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param context
	 * @param pxValue
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param context
	 * @param spValue
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 返回是否为竖屏
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isScreenOriatationPortrait(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}

	/**
	 * 返回是否为横屏
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isScreenOriatationHorizontal(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	/**
	 * 当点击其他View时隐藏软键盘
	 * @param activity
	 * @param ev
	 * @param excludeViews  点击这些View不会触发隐藏软键盘动作
	 */
	public static final void hideInputWhenTouchOtherView(Activity activity, MotionEvent ev, List<View> excludeViews){


		if (ev.getAction() == MotionEvent.ACTION_DOWN){
			if (excludeViews != null && !excludeViews.isEmpty()){
				for (int i = 0; i < excludeViews.size(); i++){
					if (isTouchView(excludeViews.get(i), ev)){
						return;
					}
				}
			}
			View v = activity.getCurrentFocus();
			if (isShouldHideInput(v, ev)){
				InputMethodManager inputMethodManager = (InputMethodManager)
						activity.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (inputMethodManager != null){
					inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}

		}
	}

	public static final boolean isTouchView(View view, MotionEvent event){
		if (view == null || event == null){
			return false;
		}
		int[] leftTop = {0, 0};
		view.getLocationInWindow(leftTop);
		int left = leftTop[0];
		int top = leftTop[1];
		int bottom = top + view.getHeight();
		int right = left + view.getWidth();
		if (event.getRawX() > left && event.getRawX() < right
				&& event.getRawY() > top && event.getRawY() < bottom){
			return true;
		}
		return false;
	}

	public static final boolean isShouldHideInput(View v, MotionEvent event){
		if (v != null && (v instanceof EditText)){
			return !isTouchView(v, event);
		}
		return false;
	}



	public static boolean checkPermission(Context context, String permission) {
		boolean result = false;
		if (Build.VERSION.SDK_INT >= 23) {
			try {
				Class<?> clazz = Class.forName("android.content.Context");
				Method method = clazz.getMethod("checkSelfPermission", String.class);
				int rest = (Integer) method.invoke(context, permission);
				if (rest == PackageManager.PERMISSION_GRANTED) {
					result = true;
				} else {
					result = false;
				}
			} catch (Exception e) {
				result = false;
			}
		} else {
			PackageManager pm = context.getPackageManager();
			if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
				result = true;
			}
		}
		return result;
	}
	public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String deviceId = null;
			if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
				deviceId = tm.getDeviceId();
			}
			String mac = null;
			FileReader fstream = null;
			try {
				fstream = new FileReader("/sys/class/net/wlan0/address");
			} catch (FileNotFoundException e) {
				fstream = new FileReader("/sys/class/net/eth0/address");
			}
			BufferedReader in = null;
			if (fstream != null) {
				try {
					in = new BufferedReader(fstream, 1024);
					mac = in.readLine();
				} catch (IOException e) {
				} finally {
					if (fstream != null) {
						try {
							fstream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			json.put("mac", mac);
			if (TextUtils.isEmpty(deviceId)) {
				deviceId = mac;
			}
			if (TextUtils.isEmpty(deviceId)) {
				deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}
			json.put("device_id", deviceId);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



}
