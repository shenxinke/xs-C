package com.xswq.consumer.activity.systemSettings;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xswq.consumer.R;

public class HelpCenterActivity extends Activity {

    private TextView textView;
    private String text = "帮助中心\n1.用户\nQ：怎么注册？\nA：输入手机号、密码并获取短信验证码，即可完成注册。\n\n\nQ：我的密码忘记了，怎么找回密码？\nA：输入手机号获取短信验证码，并输入新密码，验证成功后即完成密码重置。\n\n\nQ：游客登录和注册后登录有什么区别？\nA：游客登录只能体验前两节课的故事剧情，注册并登录后可解锁其他故事剧情。\n\n\n2.故事剧情\nQ：页面上的花藤和花苞是什么意思？\nA：花藤代表课程列表，未开的花苞代表未解锁的课程，半开的花苞代表已解锁但未观看过的课程；每个课程（花苞）中都包含故事、游戏、课件、习题四种内容，且每四节课会出现一次测验，检验学习成果。\n\n" +
            "Q：页面上的风车是什么？\nA：风车代表小朋友的错题本，当小朋友在课程中有做错的题，会纳入到错题本中，在错题本中正确解答，即可清除错题本中的这个题目。如果错题本中没有错题，系统会根据小朋友已经完成的课程进度随机生成10道题作为每日练习。做完后如果需要更多练习，可以点击刷新按钮消耗水滴来生成更多习题。\n\n\n3.对弈切磋\nQ：什么是升降级，什么是友谊赛？\nA：升降赛影响在平台的棋力等级，友谊赛不影响棋力等级，只计入历史对局。\n\n\nQ：什么是本地对弈？\nA：本地对弈是指家长和孩子可以在同一台设备上交替落子，完成对弈。\n\n\nQ：升降级中的星星是什么？\n" +
            "A：星星代表等级的提升进度，升降级对弈胜利后可获得星星，获得足够当前等级的星星后，即可升级。\n\n\n4.精彩活动\nQ：怎么参加精彩活动？\nA：可以通过消耗活动券参加，每期活动规则不同，所设活动奖励也不同。每次参与活动都可获得积分，每日积分获取有上限（10积分）。活动期截至后将统计最终排名，用户根据所得名次获得奖励。\n\n\nQ：活动券怎么获得？\nA：可以在商城购买，也可以通过登录奖励或达成某些成就获得。\n\n\n5.个人信息\nQ：“棋盘棋子皮肤”和“行棋特效”是什么？\nA：用户可以点击主页面左上角的个人头像后在皮肤特效板块中使用积累的水滴解锁各种“棋盘棋子皮肤”和“行棋特效”，前者可以改变用户所使用的棋盘和棋子的外观，后者可以在对弈时根据用户的行为（如提子、打劫等）触发不同的动画特效，增加对弈乐趣。“棋盘棋子皮肤”和“行棋特效”也可通过登录奖励和成就获得。\n\n\nQ：如何修改“头像”和“头像框”\nA：用户可以点击主页面左上角的个人头像后在基本信息界面点击头像下方的“画笔”按钮，进入修改页面后可以调整所使用的头像和头像框。用户可以通过积累的水滴解锁新的“头像”和“头像框”，也可以通过登录奖励和成就获得。";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView = findViewById(R.id.system_help_center_text);
        textView.setText(text);
        ImageView mShutDown = findViewById(R.id.set_up_shut_down);
        mShutDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
}
