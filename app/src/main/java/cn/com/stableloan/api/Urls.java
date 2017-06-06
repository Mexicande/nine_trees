package cn.com.stableloan.api;

/**
 * Created by apple on 2017/5/20.
 */

public interface Urls{

    interface Login{

         String SEND_MESSAGE="http://sjd.xianjindaikuan.cn/haiqian/Home/ChuanglanSmsApi/sendSMS";


    }

    interface  main{
        String PRODUCT_LIST="http://www.anwendai.com/anwendai/Home/ApiLogin/GetProduct";
    }
    interface register{

        String REGSTER  =   "http://www.anwendai.com/anwendai/Home/ApiLogin/Registered";
    }

}
