package cn.com.stableloan.api;

/**
 * Created by apple on 2017/5/20.
 */

public interface Urls{

    String STATUS_WordLogin                 =           "1";
    String STATUS_MessageLogin              =           "3";


    String CardBack="http://www.shoujiweidai.com/Card/index.html";
    String puk_URL="http://test.api.shoujijiekuan.com/Home/ApiLogin/";

    String NEW_URL="http://test.api.shoujijiekuan.com/Home/";
    interface Login{
        //验证码发送
        String SEND_MESSAGE="http://test.api.shoujijiekuan.com/Home/ChuanglanSmsApi/sendSMS";
        String LOGIN="Login";
        String USER_INFOMATION="Mine/Locking";
        String GET_SIGNATURE="Mine/GetSignature";
        String Immunity="Mine/Immunity";



    }

    interface  Identity{
        String GetIdentity="Identity/GetIdentity";
        String Getbank="Identity/Getbank ";
        String GetOccupation="Identity/GetOccupation";

        String AddIdentity="Identity/AddIdentity";

        String Addbank="Identity/Addbank";
        String AddOccupation="Identity/AddOccupation";
    }
    interface STATUS{
        String GetStatus="Status/GetStatus";

        String GetPictrueStatus="Status/GetPictrueStatus";
        String Getsetting="SaveSetting/Getsetting";
        String Save_Setting="SaveSetting/setting";


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
        String USER_STATUS="Status/GetStatus";

    }
    interface  notice{
        String Announcement="Announcement";
    }

    interface  LOTTERY{
        String GetLottery="GetLottery";
    }

    interface Pictrue{
        String GET_QINIUTOKEN="Mine/GetToken";

        String UpLoadImage="Pictrue/AddIdcard";

        String Get_Pictrue="Pictrue/GetPictrue";
    }
}
