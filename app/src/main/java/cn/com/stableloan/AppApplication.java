package cn.com.stableloan;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.https.HttpsUtils;
import com.qiniu.android.storage.UploadManager;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import okhttp3.OkHttpClient;


/**
 * Created by apple on 2017/5/20.
 */

public class AppApplication extends Application {
    //dao

    public static String today_date = "";

    private static AppApplication instance;

    public static SharedPreferences sp;

    public static UploadManager uploadManager;

    public static UploadManager getUploadManager(){
        return uploadManager;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        initTypeface();

        OkGo.init(this);
        try {
            OkGo.getInstance()
                    .setCertificates()
                    .debug("OkGo", Level.INFO, true)
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




    private static List<Activity> myActivity = new ArrayList<>();
    private static Map<String,Activity> destoryMap = new HashMap<>();
    public void addToList(Activity activity){

        myActivity.add(activity);

    }
    public static void addDestoryActivity(Activity activity,String activityName) {
        destoryMap.put(activityName,activity);
    }
    /**
     *销毁指定Activity
     */

    public static void destoryActivity(String activityName) {
        Set<String> keySet=destoryMap.keySet();
        for (String key:keySet){
            destoryMap.get(key).finish();
        }
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity activity : myActivity){
            activity.finish();
        }
    }
}

