package cn.com.stableloan;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import java.util.logging.Level;

import cn.com.stableloan.bean.UserBean;
import cn.com.stableloan.utils.TinyDB;

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

        sInstance=AppApplication.getsInstance();
        super.onCreate();
        OkGo.init(this);
        try {
            OkGo.getInstance()
                    .debug("OkGo", Level.INFO, true)
                    .setCacheMode(CacheMode.NO_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserBean userBean=new UserBean();
        userBean.setNickname("小开发仔");
        userBean.setUserphone("15600575837");
        TinyDB tinyDB=new TinyDB(getApplicationContext());
        tinyDB.putObject("user",userBean);
    }
}

