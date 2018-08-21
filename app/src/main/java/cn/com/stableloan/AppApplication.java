package cn.com.stableloan;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.meituan.android.walle.WalleChannelReader;
import com.qiniu.android.storage.UploadManager;
import com.rong360.app.crawler.CrawlerManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import cn.com.stableloan.api.Urls;
import cn.com.stableloan.common.Constants;
import cn.com.stableloan.model.ProductBean;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtil;
import cn.com.stableloan.view.update.AppUpdateUtils;


/**
 * Created by apple on 2017/5/20.
 */

public class AppApplication extends Application {
    //dao
    public static ProductBean mProductBean;


    private static AppApplication instance;

    public static SharedPreferences sp;

    public static UploadManager uploadManager;

    public static UploadManager getUploadManager(){
        return uploadManager;
    }


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
        initTypeface();

        uploadManager=new UploadManager();
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
        CrashReport.initCrashReport(getApplicationContext(), "e0e8b8baa1", false);

        //只能被本应用访问
        sp = super.getSharedPreferences("eSetting", Context.MODE_PRIVATE);

        CrawlerManager.initSDK(this);
        initUmeng();


    }

    private void initUmeng() {

        UMConfigure.init(this, Urls.KEY.UMENG_KEY, channel, UMConfigure.DEVICE_TYPE_PHONE, Urls.KEY.UMENG_PUSHKEY);
        PushAgent mPushAgent = PushAgent.getInstance(this);


        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                LogUtils.i("device_token="+deviceToken+"");

                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //声音
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);//呼吸灯


        MiPushRegistar.register(this,"2882303761517590311", "5331759030311");
        HuaWeiRegister.register(this);
        MeizuRegister.register(this, "1001232", "8e1c83beb7e24dcca7e1dcd257cf7143");




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

    public static void addDestoryActivity(Activity activity,String activityName) {
        destoryMap.put(activityName,activity);
    }

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

