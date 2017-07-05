package cn.com.stableloan;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.qiniu.android.storage.UploadManager;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import cn.com.stableloan.other.dao.AllInfo;
import cn.com.stableloan.other.dao.DaoMaster;
import cn.com.stableloan.other.dao.DaoSession;
import cn.com.stableloan.other.dao.User;

/**
 * Created by apple on 2017/5/20.
 */

public class AppApplication extends Application {
    //dao
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public static User user;
    public static AllInfo today;
    public static String today_date = "";

    private static AppApplication instance;

    public static SharedPreferences sp;

    public static UploadManager uploadManager;

    public static UploadManager getUploadManager(){
        return uploadManager;
    }


    @Override
    public void onCreate() {
        Utils.init(this);
        super.onCreate();
        initTypeface();

        OkGo.init(this);
        try {
            OkGo.getInstance()
                    .debug("OkGo", Level.INFO, true)
                    .setRetryCount(0)
                    .setCacheMode(CacheMode.NO_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        uploadManager=new UploadManager();
        instance = this;

        CrashReport.initCrashReport(getApplicationContext(), "e0e8b8baa1", true);
        sp = super.getSharedPreferences("eSetting", Context.MODE_PRIVATE);//只能被本应用访问

    }


    private void initTypeface(){
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, Typeface.createFromAsset(getAssets(), "fonts/msyh.ttf"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static AppApplication getApp(){
        return instance;
    }




    public DaoMaster getDaoMaster(Context context){
        if (daoMaster == null){
            DaoMaster.OpenHelper helper =
                    new DaoMaster.DevOpenHelper(context,"easywork.db",null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession(Context context){
        if (daoSession == null){
            if (daoMaster == null){
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    List<Activity> myActivity = new ArrayList<>();
    public void addToList(Activity activity){
        myActivity.add(activity);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity activity : myActivity){
            activity.finish();
        }
    }
}

