package com.xswq.consumer.activity.systemSettings;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.xswq.consumer.R;
import com.xswq.consumer.utils.ConstUtils;

public class AboutUsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView mAboutUsText = findViewById(R.id.system_about_us_text);
        TextView mVersions = findViewById(R.id.system_about_us_versions);
        mAboutUsText.setText("Copyright  2017-2019" + "\n" + "北京先手网络科技有限公司 版权所有");
        ImageView mShutDown = findViewById(R.id.set_up_shut_down);
        mShutDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConstUtils.isClickable()) {
                    return;
                }
                finish();
                overridePendingTransition(0, 0);
            }
        });

        try {
            PackageInfo packageInfo = AboutUsActivity.this.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(AboutUsActivity.this.getPackageName(), 0);
            String localVersion = packageInfo.versionName;
            mVersions.setText("Version：V" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
