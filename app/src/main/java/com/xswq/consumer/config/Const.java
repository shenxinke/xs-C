package com.xswq.consumer.config;

import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * 魔法值常量类
 */
public class Const {

    /**
     * 微信
     */
    public static final String APP_ID = "wx3b1584e17601e9a0";
    public static IWXAPI WX_API;
    public static final String APP_SERECET = "49a658557c3df106395eada934792c2c";

    /**
     * udesk客服的key和 id
     */
    public final static String UDESK_DOMAIN = "xianshou.udesk.cn/";
    public final static String APPID = "b4d0d200c203425c";
    public final static String UDESK_SECRETKEY = "0b64032fc681c7decb41852c6f049f9b";


    public static String TOKEN = "";
    public static String UID = "";
    public static String SKINID = "";
    public static String EFFECTID = "";
    public static boolean IS_SHOW_ANNOUNCEMENT = true;
    public static boolean IS_SHOW_RED_DOT ;

    /**
     * 字符串魔法值1
     */
    public static final String STR0 = "0";
    public static final String STR1 = "1";
    public static final String STR2 = "2";
    public static final String STR3 = "3";
    public static final String STR4 = "4";
    public static final String STR5 = "5";
    public static final String STR6 = "6";
    public static final String STR7 = "7";
    public static final String STR8 = "8";
    public static final String STR9 = "9";
    public static final String STR10 = "10";
    public static final String STR11 = "11";
    public static final String STR12 = "12";
    public static final String STR13 = "13";
    public static final String STR14 = "14";
    public static final String STR15 = "15";
    public static final String CONS_STR_NULL = "null";
    public static final String CONS_STR_ERROR = "服务器开小差了,稍后试试吧!";
    public static final String CONS_STR_USER_PAST_DUE = "用户过期，请重新登录!";
    public static final String CONS_STR_CODE_SEND = "验证码已发送!";
    public static final String CONS_STR_BUG = "请购买关卡!";
    public static final String CONS_STR_SAVE_SUCCESSFULLY = "保存成功!";
    /**
     * 整形魔法值
     */
    public static final int INTEGER_1 = 1;
    public static final int INTEGER_6 = 6;
    public static final int INTEGER_11 = 11;
    public static final int INTEGER_16 = 16;
    public static final int INTEGER_19 = 19;
    public static final int INTEGER_24 = 24;
    public static final int INTEGER_27 = 27;
    public static final int INTEGER_29 = 29;
    public static final int INTEGER_800 = 800;

    public static final String CONS_STR_PLAY = "play";
    public static final String CONS_STR_LOCAL_PLAY = "localPlay";
    public static final String CONS_STR_ACTIVITY_PLAY = "activityPlay";

    /**
     * 提醒更新的下次提示状态
     */
    public static boolean ISGETNEWVERSION = true;
}
