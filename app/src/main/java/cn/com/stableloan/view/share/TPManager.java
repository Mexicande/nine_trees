package cn.com.stableloan.view.share;

/**
 * Created by lujun on 2015/9/2.
 */
public class TPManager {

    private static TPManager mInstance;

    public static TPManager getInstance(){
        if (mInstance == null){
            synchronized (TPManager.class){
                if (mInstance == null){
                    mInstance = new TPManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * Init share config, you should call thie method in your own Application class
     * @param wxAppId 微信 AppId
     * @param wxAppSecret 微信 AppSecret
     */
    public void initAppConfig(
                              String wxAppId, String wxAppSecret,String qqAppId, String qqAppSecret){
        WXAppId = wxAppId;
        WXAppSecret = wxAppSecret;
        QQAppId = qqAppId;
        QQAppSecret = qqAppSecret;
    }



    public String getQQAppId() {
        return QQAppId;
    }

    public String getQQAppSecret() {
        return QQAppSecret;
    }

    public String getWXAppId() {
        return WXAppId;
    }

    public String getWXAppSecret() {
        return WXAppSecret;
    }

    private String QQAppId = "";
    private String QQAppSecret = "";
    private String WXAppId = "";
    private String WXAppSecret = "";
}
