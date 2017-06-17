package cn.com.stableloan.api;

/**
 * Created by apple on 2017/5/20.
 */

public interface Urls{

    String STATUS_WordLogin                 =           "1";
    String STATUS_MessageLogin              =           "3";


    String CardBack="http://www.shoujiweidai.com/Card/index.html";
    String puk_URL="http://123.56.29.65:8090/anwendai/Home/ApiLogin/";
    interface Login{
        //验证码发送
        String SEND_MESSAGE="http://123.56.29.65:8090/anwendai/Home/ChuanglanSmsApi/sendSMS";
        String LOGIN="Login";

    }

    interface  HOME_FRAGMENT{
        String BANNER_HOT="GetBanner";
        String PRODUCT_LIST="GetProducts";
    }

    interface register{

        String REGSTER  = "Registered";

        String FORGETWORD="ForgetPassword";
    }

    interface product{
        //产品详情
        String Productdetail="GetProductDetail";
        String GetSlotdetail ="GetSlotdetail";
        String ClassProduct="ClassificationList";
        String ProductList="ProductList";
        String ProDuctScreening="Screening";

    }

    interface update{
        String UPDATE_NICK="Modify";
        String UPDATE_Word="Modify";
        String UPDATE_PROFRSSION="Modify";
    }
    interface user{
        String USERT_INFO="Personal";
    }
    interface  notice{
        String Announcement="Announcement";
    }

    interface  LOTTERY{
        String GetLottery="GetLottery";


    }
}
