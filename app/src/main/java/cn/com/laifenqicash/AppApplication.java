package cn.com.laifenqicash;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Process;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import cn.com.laifenqicash.api.Urls;
import cn.com.laifenqicash.common.Constants;
import cn.com.laifenqicash.utils.SPUtil;
import cn.com.laifenqicash.view.update.AppUpdateUtils;


/**
 * Created by apple on 2017/5/20.
 */

public class AppApplication extends Application {

    private static AppApplication instance;

    public static SharedPreferences sp;



    /**
     * 主线程Handler.
     */
    public static Handler mHandler;
    public static AppApplication sApp;
    String channel="test";
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        instance = this;
        mHandler = new Handler();
        AVOSCloud.initialize(this,Urls.KEY.LEAN_ID,Urls.KEY.LEAN_KEY);
        channel = WalleChannelReader.getChannel(this.getApplicationContext());
        String versionName = AppUpdateUtils.getVersionName(this);

        HttpHeaders headers = new HttpHeaders();
        headers.put("channel", channel);
        headers.put("os", versionName);
        headers.put("terminal", Constants.terminal);
        headers.put("linkType", Constants.LINK_TYPE);
        OkGo.init(this);
        try {
            OkGo.getInstance()
                    .debug("OkGo", Level.INFO, true)
                    .setCertificates()
                    //公共header不支持中文
                    .addCommonHeaders(headers);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //只能被本应用访问
        sp = super.getSharedPreferences("eSetting", Context.MODE_PRIVATE);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                initTypeface();
                initUmeng();
                CrashReport.initCrashReport(getApplicationContext(), "4e26e2b9fe", false);
            }
        }.start();



    }

    private void initUmeng() {

        UMConfigure.init(this, Urls.KEY.UMENG_KEY, channel, UMConfigure.DEVICE_TYPE_PHONE, null);

    }


    private void initTypeface(){
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, Typeface.createFromAsset(getAssets(), "fonts/pingfang.ttf"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static AppApplication getApp(){
        return instance;
    }

    public static String getToken(){

        return SPUtil.getString(instance, Urls.lock.TOKEN, "1");

    }


    private static List<Activity> myActivity = new ArrayList<>();
    private static Map<String,Activity> destoryMap = new HashMap<>();


    /**
     *销毁指定Activity
     */

    public static void destoryActivity(String activityName) {
        Set<String> keySet=destoryMap.keySet();
        for (String key:keySet){
            if(destoryMap.get(key)!=null){
                destoryMap.get(key).finish();
            }
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

