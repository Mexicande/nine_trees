package cn.com.stableloan.ui.activity.integarl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.utils.cache.ACache;

public class SettingChangDateActivity extends BaseActivity {
    private ACache aCache;
    private String lo = "密码解锁";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_chang_date);






    }

 /*   *//**
     * 解锁方式
     *//*
    private void settingLock() {
        aCache = ACache.get(this);
        lo = aCache.getAsString("lock");
        if (lo != null) {
            if (lo.equals("on")) {
                lock.setText(list[0]);
            } else {
                lock.setText(list[1]);
            }
        }
        if (!lo.equals(lock.getText().toString())) {
            if (lock.getText().toString().equals(list[0])) {
                aCache.put("lock", "on");
            } else {
                aCache.put("lock", "off");
            }
        }
        if (lock.getText().toString().equals(list[0])) {
            aCache.put("lock", "on");
        } else {
            aCache.put("lock", "off");
        }
    }*/


}
