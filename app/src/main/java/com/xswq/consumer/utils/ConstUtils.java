package com.xswq.consumer.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xswq.consumer.config.Const;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 常用工具类
 */
public class ConstUtils {

    private static long lastClickTime;



    /**
     * 设置文本
     */
    public static void setTextString(String string, TextView textView) {
        if (!TextUtils.isEmpty(getStringNoEmpty(string))) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(string);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * 对字符串判空
     */
    public static String getStringNoEmpty(String string) {
        if (null == string || string.length() == 0 || Const.CONS_STR_NULL.equals(string)) {
            string = " ";
        }
        return string;
    }

    /**
     * 空string改0
     */
    public static String changeEmptyStringToZero(String string) {
        if (null == string || string.length() == 0 || Const.CONS_STR_NULL.equals(string)) {
            string = "0";
        }
        return string;
    }


    /**
     * 开始一个按钮的倒计时
     *
     * @param button 控件(TextView类型)
     */
    public static void startCountDown(final Button button) {
        button.setEnabled(false);
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setTextSize(10);
                button.setText((millisUntilFinished / 1000) + "s后重新获取");
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setText("重新获取");
            }
        }.start();
    }


    /**
     * 时间戳转换成字符窜
     *
     * @param milSecond
     * @param pattern
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String formatSeconds(long seconds) {
        String timeStr = seconds + "秒";
        if (seconds > 60) {
            long second = seconds % 60;
            long min = seconds / 60;
            timeStr = min + "分" + second + "秒";
            if (min > 60) {
                min = (seconds / 60) % 60;
                long hour = (seconds / 60) / 60;
                timeStr = hour + "小时" + min + "分" + second + "秒";
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    long day = (((seconds / 60) / 60) / 24);
                    timeStr = day + "天" + hour + "小时" + min + "分" + second + "秒";
                }
            }
        }
        return timeStr;
    }

    /**
     * 判断字符串是否有效
     *
     * @param strr
     * @return 无效返回空字符串
     */
    public static String signString(String strr) {
        if (null == strr) {
            strr = "";
        } else {
            if ("null".equals(strr)) {
                strr = "";
            }
            if ("".equals(strr)) {
                strr = "";
            }
        }
        return strr;
    }

    /**
     * 判断数字字符串是否有效
     *
     * @param strr
     * @return 无效返回空字符串
     */
    public static String zeroString(String strr) {
        if (null == strr) {
            strr = "0";
        } else {
            if ("null".equals(strr)) {
                strr = "0";
            }
            if ("".equals(strr)) {
                strr = "0";
            }
        }
        return strr;
    }

    /**
     * Data转string时间
     * formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     *
     * @return
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * 获取中文字符长度
     *
     * @param s
     * @return
     */
    public static double getLength(String s) {
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }
        }
        //进位取整
        return Math.ceil(valueLength);
    }

    /**
     * 计算棋力
     */
    public static String getChessLevel(int level) {
        String levels = null;
        if (level == 0) {
            levels = "0K";
        } else if (0 < level && level < 26) {
            levels = (26 - level) + "K";
        } else if (level >= 26) {
            levels = (level - 25) + "D";
        }
        return levels;
    }

    /**
     * 按钮防爆点击
     *
     * @return b
     */

    public static boolean isClickableSuper() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 按钮防爆点击
     *
     * @return b
     */
    public static boolean isClickable() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
