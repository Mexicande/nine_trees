package cn.com.stableloan;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.logging.Level;

import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.utils.TinyDB;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by apple on 2017/5/20.
 */

public class AppApplication extends Application {

    private static AppApplication sInstance;

    public static AppApplication getsInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        Utils.init(this);

        CrashReport.initCrashReport(getApplicationContext(), "e0e8b8baa1", false);

        sInstance=AppApplication.getsInstance();
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .build()
        );
        OkGo.init(this);
        try {
            OkGo.getInstance()
                    .debug("OkGo", Level.INFO, true)
                    .setCacheMode(CacheMode.NO_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

