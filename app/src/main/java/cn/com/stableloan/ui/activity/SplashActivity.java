package cn.com.stableloan.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.lang.ref.WeakReference;

import cn.com.stableloan.utils.SharedPreferencesUtil;

/**
 * 闪屏页
 *
 */
public class SplashActivity extends AppCompatActivity {
    private SwitchHandler mHandler = new SwitchHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler.sendEmptyMessageDelayed(3, 1000);


    }
    private static class SwitchHandler extends Handler {
        private WeakReference<SplashActivity> mWeakReference;

        SwitchHandler(SplashActivity activity) {
            mWeakReference = new WeakReference<SplashActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = mWeakReference.get();
            if (activity != null) {
                MainActivity.launch(activity);
                activity.finish();
            }
        }
    }
    private void setWelcome( ){
        boolean isFirstOpen = SharedPreferencesUtil.getBoolean(this, SharedPreferencesUtil.FIRST_OPEN, true);
        // 如果是第一次启动，则先进入功能引导页
        if (isFirstOpen) {
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }else {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }
}
