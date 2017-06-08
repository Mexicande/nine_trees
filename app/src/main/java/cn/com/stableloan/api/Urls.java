package cn.com.stableloan.api;

/**
 * Created by apple on 2017/5/20.
 */

public interface Urls{
    String puk_URL="http://47.93.197.52:8080/anwendai/Home/ApiLogin/";
    interface Login{
        //验证码发送
        String SEND_MESSAGE="http://47.93.197.52:8080/anwendai/Home/ChuanglanSmsApi/sendSMS";

        String LOGIN="Login";
    }

    interface  HOME_FRAGMENT{
        String BANNER_HOT="GetBanner";
        String PRODUCT_LIST="GetProducts";
    }

    interface register{

        String REGSTER  = "Registered";
    }

    interface product{
        //产品详情
        String PRODUCT_DESC ="GetProductDetail";

    }

    interface update{
        String UPDATE_NICK="Modify";
        String UPDATE_Word="Modify";
    }
    interface user{
        String USERT_INFO="Personal";

    }

}
