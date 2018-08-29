package cn.com.cashninetrees.common;

/**
 *
 * @author Sym
 * @date 15/12/23
 *
 *
 */
public class Constants {

    public static final String GESTURE_PASSWORD = "GesturePassword";
    public static final String terminal = "1";
    public static final String LINK_TYPE = "1";
    /** 启动页显示时间 **/
    public static final int LAUCHER_DIPLAY_MILLIS = 2000;
    /** 倒计时时间 **/
    public static final int MILLIS_IN_TOTAL = 60000;
    /** 时间间隔 **/
    public static final int COUNT_DOWN_INTERVAL = 1000;

    public static final String VIP = "vip";

    /**
     * 支付
     */
    public static final  int WECHAT_TYPE=1;
    public static final  int ALIPAY_TYPE=2;
    public static final  String WEICHAT_ID="e051831188f1181e89ae75facc19d6ce";
    //余额不足时支付成功
    public static final String INTENT_EXTRA_PAY_SUCESS = "com.xinhe.intent.action.PAY_SUCESS";
    //充值成功
    public static final String INTENT_EXTRA_RECHARGE_SUCESS = "com.xinhe.intent.action.RECHARGE_SUCESS";
    //登录成功
    public static final String INTENT_EXTRA_LOGIN_SUCESS = "com.xinhe.intent.action.LOGIN_SUCESS";



    /**
     * 埋点
     */
    /** banner点击 **/
    public static final String  BANNER_CLICK="1";
    /** 首页Vip **/
    public static final String  VIP_CLICK="6";
    /** 上班族 **/
    public static final  String WORK="2";
    /** 企业主 **/
    public static final  String ENTREPRENEUR ="3";
    /** 逍遥客 **/
    public static final  String FREE="4";
    /** 充值 **/
    public static final  String Recharge="7";
    /** Adialog广告 **/
    public static final  String ADIALOG="8";





}
