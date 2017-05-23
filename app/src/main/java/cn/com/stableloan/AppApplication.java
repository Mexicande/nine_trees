package cn.com.stableloan;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import java.util.logging.Level;

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
        super.onCreate();
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

