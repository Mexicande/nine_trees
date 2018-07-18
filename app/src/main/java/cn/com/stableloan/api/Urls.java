package cn.com.stableloan.api;

/**
 * Created by apple on 2017/5/20.
 */

public interface Urls {
    String API="http://";

    String Ip_url=API+"api.anwenqianbao.com/v1/";

    String NEW_Ip_url=API+"api.anwenqianbao.com/v2/";

    String puk_URL=API+"api.shoujijiekuan.com/Home/ApiLogin/";
    String HTML_URL=API+"m.anwenqianbao.com/";

    String NEW_URL=API+"api.shoujijiekuan.com/Home/";
    String logoUrl="http://p2y9on3t5.bkt.clouddn.com/iv_logo.png";

    /**
     * 第三方认证
     */
    String API_ID ="3846f6f1e99e4f1b958111fda257c368";
    String NUMBER_ONE=                                                                              "1";
    String NUMBER_TWO=                                                                              "2";

    /**
     * 埋点统计
     */
    String CLICK =NEW_Ip_url+"statistics/click";


    interface Update{

        String APP_UPDATE =API+"api.shoujijiekuan.com/Home/ApiLogin/Version";

    }

    interface Login{

        /**
         * 新老用户验证
         */
        String IS_OLD_USER =NEW_Ip_url+ "quick/isOldUser";
        /**
         * 验证码获取
         */
        String GET_CODE=Ip_url+"sms/getcode";
        /**
         * 验证码效验
         */
        String CHECK_CODE =Ip_url+"sms/checkCode";


    }
    interface  Vip{
        /**
         * Vip中心
         */
        String vipService=NEW_Ip_url+"vip/vipService";
        /**
         * vip产品列表
         */
        String VIPPRODUCT =NEW_Ip_url+"vip/product";
        /**
         * 信贷报告
         */
        String CREDIT_INFO =NEW_Ip_url+"vip/creditInfo";
        /**
         * 双色球
         */
        String LOTTERY =NEW_Ip_url+"vip/lottery";
        /**
         *
         * 信用卡
         */
        String CREDIT=NEW_Ip_url+"vip/creditCard";
        /**
         * 支付
         *
         */
        String PAY=NEW_Ip_url+"recharge/pay";
        /**
         * 支付方式
         */
        String PAYMENT =NEW_Ip_url+"recharge/payment";
        /**
         * 消息通知
         */
        String NOTICE_NEWS=NEW_Ip_url+"vip/news";
    }


    interface CreditrePort{

        String PRIVATE_KEY  = "MIICXAIBAAKBgQCRMcGMrZIKu956ubKD2kDFzRtSP13ycgRtPVz+MvseTje0YgGl" +
                "ht+zGuPgCD9KgW44FFdwGS2Wp7Ir/zzEpInTPlKWjXGZP06iwlu+7p04ktgK7TB6" +
                "NjF83gHB/T//OrliHrX1l/Jzh3LFHwCAxT6fJt1Ja6eziGvxtLPT94TkcwIDAQAB" +
                "AoGAKsYYugPsUUM1cLxCLfvfNyaMlPdcCu+yBCieu7hzKGNsn7R7vbL1NgOG/FoR" +
                "ozZsLRM5CyovtwFiSPnhgiDjBSnvugLBPLKcQJX3PUb9dHOpfwXZCOg/CMwbb8fg" +
                "9bPt36oBfJYIydQjP3/9iM9t1sdtIbd/fHCL6Ry/iGcsvyECQQDA1ezZ0DlV25QX" +
                "FbL1IvufITnvcfK2KwGc4By71cWZ/tnczxGh4/YiTym7V58IR+XW326UwPdW4Mi0" +
                "xtuJagmxAkEAwMDo8VzvysfxDs0hdwxFjksZ0htPEG9FLjhp2Vu5jm+swyPBt+eW" +
                "pFvstM6YIq570y6D3Mkq2zu20F5nJnm1YwJAdxur0F3tDDs0nY2pnACfqwq63ktj" +
                "v2GQ/XTwSpUgGJ5xsxGzsms7/LUo8a6NbG/8Z1xa0Ubff6oYTpEFyTrWAQJBAJPa" +
                "phSWpH2Y1yjyYswtxqD6rKjFN+W0ZI2qyk7nlDNVKGFbaTpHU/9pX+3lVz+rNeJt" +
                "GMrgKJaYfIfjEh6qV18CQAOSaCWwd26ZE5zed045dz01Yf9zPvxYXeFm4cqK584M" +
                "Z6J84qIslgp9fhOrLv/Q4mos1CZTWzDhFbDM73NlabQ=";
        String APP_ID=                                                                   "1000053";
        String APP_NAME=                                                                 "anwenqianbao";
    }

    interface  Identity{
        String GetIdentity="person/getIdentity";
        String Getbank="person/getBank ";
        String GetOccupation="person/getOccupation";

        String AddIdentity="person/addIdentity";

        String Addbank="person/addBank";

        String AddOccupation="person/addOccupation";

    }
    interface STATUS{
        String GetCerftication="accredit/status";
        String Getsetting=NEW_URL+"SaveSetting/Getsetting";
        String Save_Setting="SaveSetting/setting";
    }
    interface share{
        String htmlInsurance="http://event.anwenqianbao.com/m/landing5/index.html?channel=zengxian";
    }

    interface  HOME_FRAGMENT{
        String BANNER_HOT="GetBanner";
        String Class_Product_List="project/list";   //专题
        String SPECKILL="second/kill";
        String HOT_NEW_PRODUCT="product/home";
    }

    interface product{
        /**
         * 产品详情
         */
        String PRODUCT_DETAIL =NEW_Ip_url+"product/detail";
        /**
         * 平台详情
         */
        String GetSlotdetail =puk_URL+"GetSlotdetail";
        String ClassProduct="project/information";
        String ProductList="ProductList";
        String ProTagFlow="product/getlabels";
        String ProductSelect="product/screening";
        /**
         * 我的收藏
         */
        String ProductCollectionList=NEW_Ip_url+"product/getlist";
        /**
         * 收藏
         */
        String PRODUCT_COLLECTION =Ip_url+"product/collection";
    }
    interface update{
        String DELETE_DATE="set/updatePeriod";
    }
    interface user{
        String USERT_INFO=Ip_url+"person/getPerson";
        /**
         * 申请材料
         */
        String USER_STATUS=Ip_url+"status/getStatus";
        /**
         * 意见反馈
         */
        String FEEDBACK=NEW_Ip_url+"feedback/opinion";
        String DEVICE="set/getComDevice";
        String DELETE_DEVICE="set/delComDevice";
        String OPEN_DEVICE="set/fetchComDevice";
    }
    interface  notice{
        /**
         * 消息通知
         */
        String Announcement=puk_URL+"Announcement";
    }
    interface  LOTTERY{
        String GetLottery="welfare/getwelfare";

        String Welfare="welfare/shunt";
    }

    interface Pictrue{
        String GET_QINIUTOKEN="Mine/GetToken";

        String UpLoadImage="picture/addPicture";

        String Get_Pictrue="picture/getPicture";
    }
    interface KEY{
        /**
         * 微信
         */
        String WEICHAT_APPID="wxd8a11cffdc98f84b";
        /**
         * QQ
         */
        String QQ_APPID="1106239350";
        String PageWeb="http://m.anwenqianbao.com/#/details?pId=";
        String SHARE_INCODE="http://m.anwenqianbao.com/#/login?inviteCode=";
        String LEAN_KEY="1Xractc2sikvNt5m6r9GP7sK";
        String LEAN_ID="9zeHS4F8GAHi97Fkg0p80FYV-gzGzoHsz";

        String UMENG_KEY="5a14ef858f4a9d5bd300006c";


        String UMENG_PUSHKEY="1994921d0d6a190063cb94825f7dd8c4";

    }
    interface  Integarl{
        String getAccumulatePoints="point/getAccumulatePoints";
        String getActivity="activity/getActivity";
        String GETCASH="cash/getCash";
        String OUTCASH="cash/getCashOut";
        String EXCHANGEPOINTS="point/exchangePoints";
        String CASH_DETAIL="cash/detail";
    }
    interface Dialog{
        String advertising="popup/getPopup";
        String GETUSERPOPUP="popup/getUserPopup";
    }
    interface Invite{
        String INVITE_FRIENDS="invite/smsInvite";
        String INVITE_LIST=Ip_url+"invite/inviteList";
    }
    interface DateChange{
        // 个人信息
        int      NAME=                                                                              1;  // 姓名
        int      IDCARD=                                                                            2;
        int      SEX=                                                                               3;
        int      AGE=                                                                               4;
        int      CONTACT_PHONE1=                                                                    6;
        int      CONTACT_PHONE2=                                                                    7;
        int      ADDRESS=                                                                           8;
        //银行信息
        int      DEBIT_CARD   =                                                                     9;
        int      DEBIT_NAME   =                                                                     10;
        int      DEBIT_PHONE  =                                                                     11;
        int      CREDIT_CARD  =                                                                     13;
        int      CREDIT_NAME  =                                                                     14;
        int      CREDIT_PHONE =                                                                     15;
        //职业信息
                    // 公司信息
        int      COMPANY_NAME =                                                                     16;
        int      COMPANY_ADRESS =                                                                   17;
        int      COMPANY_EMAIL =                                                                    18;
        int      COMPANY_SALART =                                                                   19;
        int      COMPANY_TELEPHONE =                                                                20;
                    // 企业信息
        int      BUSSINESS_NAME =                                                                   21;
        int      BUSSINESS_ADRESS =                                                                 22;
        int      BUSSINESS_EMAIL =                                                                  23;
        int      BUSSINESS_SALART =                                                                 24;
        int      BUSSINESS_TELEPHONE =                                                              25;
                    // 其他信息
        int      STUHENT_NAME =                                                                     26;
        int      STUHENT_ADRESS =                                                                   27;
        int      STUHENT_TELEPHONE =                                                                28;

    }

    interface DataStatistics{
        String APPLY       =                                                                   "product/apply";
    }
    interface SettingResultCode{
        int    SAFE_DATE   =                                                                    200;
    }

    interface lock{
        String  TOKEN =                                                                          "token";
        String  USER_PHONE =                                                                     "phone";
    }
    interface ERROR_CODE{
        int FREEZING_CODE=                                                                      1136;
        /**token失效**/
        int LOSE_CODE=                                                                          2;
    }
    interface REQUEST_CODE{
        int PULLBLIC_CODE                                                                          =100;
    }
}
