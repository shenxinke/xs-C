package com.xswq.consumer.config;

public class ContactUrl {
//    public final static String BASE_PATH = "http://192.168.2.114";
    public static String APP_NAME = "xsmj.apk";
    //正式环境
    private final static String BASE_PATH = "https://ca.xswq361.cn";
    public static String APK_PATH = "https://www.xswq361.cn/files/download/consumer/xsmj.apk";
    public static String APK_INCREMENT_PATH = "https://www.xswq361.cn/files/download/consumer/";
    //测试环境
//    public final static String BASE_PATH = "https://ct.xswq361.cn";
//    public static String APK_PATH  = "https://test.xswq361.cn/files/download/consumer/xsmj.apk";
//    public static String APK_INCREMENT_PATH = "https://test.xswq361.cn/files/download/consumer/";


    public final static String POST_CODE_PATH = BASE_PATH + "/consumerXSWQ/LoginController/sendMessage";//获取验证码
    public final static String POST_REGISTER_PATH = BASE_PATH + "/consumerXSWQ/LoginController/register";//注册
    public final static String POST_FORGETPASSWORD_PATH = BASE_PATH + "/consumerXSWQ/LoginController/forgetPassword";//修改密码
    public final static String POST_LOGIN_PATH = BASE_PATH + "/consumerXSWQ/LoginController/login";//登录
    public final static String POST_TOURIST_LOGIN_PATH = BASE_PATH + "/consumerXSWQ/LoginController/touristLogin";//游客登录
    public final static String POST_WX_LOGIN_PATH = BASE_PATH + "/consumerXSWQ/LoginController/wxreg";//微信登陆
    public final static String POST_WX_PAY_PATH = BASE_PATH + "/consumerXSWQ/pay/wxpay";//创建微信订单
    public final static String POST_SEKIGUCH_LIST_PATH = BASE_PATH + "/consumerXSWQ/SekiguchiController/sekiguchiList";//关卡列表
    public final static String POST_SEKIGUCH_BYGATE_NUM_PATH = BASE_PATH + "/consumerXSWQ/SekiguchiController/selectSekiguchiBygateNum";//查询当前关卡进度
    public final static String POST_STORY_BY_ID_PATH = BASE_PATH + "/consumerXSWQ/StoryController/getStoryById";//获取单个故事信息
    public final static String VIDEO_PATH = "http://vodyq6aqp0d.vod.126.net/vodyq6aqp0d/";//视频地址
    public final static String POST_VIDEO_BY_ID_PATH = BASE_PATH + "/consumerXSWQ/VideoController/getVideoById";//根据ID获取视频
    public final static String POST_INSERT_RECORD_PATH = BASE_PATH + "/consumerXSWQ/SekiguchiController/insertSekiguchiRecord";//添加闯关记录
    public final static String POST_GAME_PATH = "file:///android_asset/game/index.html?";//游戏
    public final static String POST_PLAY_CHESS_PATH = "file:///android_asset/xswl_local/exercises.html?";//习题
    public final static String POST_PLAY_TEST_PATH = "file:///android_asset/xswl_local/test.html?";//测试
    public final static String POST_USER_IN_PATH = BASE_PATH + "/consumerXSWQ/UserInfoController/getUserInfo";//获取用户信息
    public final static String POST_UPDATE_USER_IN_PATH = BASE_PATH + "/consumerXSWQ/UserInfoController/updateMyUserInfo";//更新用户信息
    public final static String POST_LEVEL_IN_FO_PATH = BASE_PATH + "/consumerXSWQ/UserInfoController/getLevelInfo";//个人中心棋力信息
    public final static String POST_ACHIEVEMENT_LIST_PATH = BASE_PATH + "/consumerXSWQ/AchievementController/getAchievementList";//成就列表
    public final static String POST_ACHIEVEMENT_AWARD_PATH = BASE_PATH + "/consumerXSWQ/AchievementController/getAchievementAward";//领取成就
    public final static String POST_HEAD_IMG_BRODER_PATH = BASE_PATH + "/consumerXSWQ/UserInfoController/getHeadImgOrBorder";//头像、头像框
    public final static String POST_SKIN_AND_EFFECT_PATH = BASE_PATH + "/consumerXSWQ/UserInfoController/getSkinAndEffect";//获取皮肤及行棋特效
    public final static String POST_PRODUCT_CLASSIFY_LIST_PATH = BASE_PATH + "/consumerXSWQ/MallController/getProductClassifyList";//获取商城分类信息
    public final static String POST_PRODUCT_LIST_PATH = BASE_PATH + "/consumerXSWQ/MallController/getProductList";//获取商城信息
    public final static String POST_PRODUCT_PATH = BASE_PATH + "/consumerXSWQ/MallController/purchaseProduct";//购买商品
    public final static String POST_SELECT_ORDER_PATH = BASE_PATH + "/consumerXSWQ/pay/selectOrder";//订单校验
    public final static String POST_USER_PRODUCT_PATH = BASE_PATH + "/consumerXSWQ/MallController/useProduct";//使用商品
    public final static String POST_GET_ORDER_LIST_PATH = BASE_PATH + "/consumerXSWQ/MallController/getOrderList";//购买记录
    public final static String POST_GET_DETAIL_PATH = BASE_PATH + "/consumerXSWQ/UserInfoController/getdetail";//我的棋谱
    public final static String POST_GET_SIGN_IN_LIST_PATH = BASE_PATH + "/consumerXSWQ/ActivityController/getSignInList";//登录奖励
    public final static String POST_RECEIVE_AWARD_PATH = BASE_PATH + "/consumerXSWQ/ActivityController/receiveAward";//领取奖励
    public final static String POST_RANDOM_BATTLE_PATH = BASE_PATH + "/consumerXSWQ/ChessController/randomBattle";//匹配接口
    public final static String POST_GAME_IN_FOR_BUY_ID_PATH = BASE_PATH + "/consumerXSWQ/ChessController/getGameInfoByUserId";//匹配接口
    public final static String POST_AI_MATCH_PATH = BASE_PATH + "/consumerXSWQ/ChessController/createAIMatch";//创建AI对弈
    public final static String POST_PLAY_PATH = "file:///android_asset/xswl_local/play.html?";//棋盘页面
    public final static String POST_ACTIVITY_PATH = "file:///android_asset/xswl_local/activity.html?";//活动页面
    public final static String POST_CHESS_MANUAL_PATH = "file:///android_asset/xswl_local/chessManual.html?";//我的棋谱
    public final static String POST_ACTIVITY_PLAY_PATH = "file:///android_asset/xswl_local/activityPlay.html?";//活动页面
    public final static String POST_LOCAL_PLAY_PATH = "file:///android_asset/xswl_local/localPlay.html?";//本地对弈
    public final static String POST_WRONG_QUESTION_PATH = "file:///android_asset/xswl_local/wrongQuestion.html?";//错题本
    public final static String POST_NEW_MOBILE_VERSION_PATH = BASE_PATH + "/consumerXSWQ/UpdateVersionController/getNewMobileVersion";//检查更新
    public final static String POST_UNLOCK_GATE_PATH = BASE_PATH + "/consumerXSWQ/SekiguchiController/unlockGate";//解锁关卡
    public final static String POST_ACTIVITY_NOW_PATH = BASE_PATH + "/consumerXSWQ/ActivityController/getActivityNow";//活动解锁
    public final static String POST_SELECT_ERROR_QUESTION_PATH = BASE_PATH + "/consumerXSWQ/QuestionController/selectErrorQuestion";//错题列表查询
    public final static String POST_GET_SHARE_INFO_PATH = BASE_PATH + "/consumerXSWQ/ShareController/getShareInfo";//分享详情
    public final static String POST_GET_SHARE_STATE_PATH = BASE_PATH + "/consumerXSWQ/ShareController/updateShareState";//领取分享奖励
    public final static String POST_STATISTICS = BASE_PATH + "/consumerXSWQ/StatisticsController/addModuleClick";//领取分享奖励
    public final static String POST_JUST_BUTTON = BASE_PATH + "/consumerXSWQ/SekiguchiController/addGatePassRecord";// 跳过记录接口
    public final static String POST_INSERT_LOGIN_RECORD = BASE_PATH + "/consumerXSWQ/UserInfoController/insertLoginRecord";//登录记录
    public final static String POST_CDK_CONVERT = BASE_PATH + "/consumerXSWQ/CDKController/CDKConvert";//CDK兑换
    public final static String POST_FEED_BACK_INFO = BASE_PATH + "/consumerXSWQ/FeedbackController/getFeedBackInfo";//意见反馈
    public final static String POST_SAVE_FEED_BACK_RECORD = BASE_PATH + "/consumerXSWQ/FeedbackController/saveFeedBackRecord";//提交意见反馈
    public final static String POST_GET_ANNOUNCEMENT = BASE_PATH + "/consumerXSWQ/AnnouncementControler/getAnnouncement";//获取公告图片
    public final static String POST_ADD_STORY_RECORD = BASE_PATH + "/consumerXSWQ/StoryController/addStoryRecord";//添加故事观看记录

}
