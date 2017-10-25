package cn.com.stableloan.api;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.stableloan.AppApplication;
import cn.com.stableloan.utils.SPUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by apple on 2017/10/25.
 */

public class DateStatisticsUtils {

    public  static void addApplyDate(Context context,String productId){

        String token = (String) SPUtils.get(context, "token", "1");
        Map<String, String> parms1 = new HashMap<>();
        parms1.put("token", token);
        parms1.put("id", productId);
        parms1.put("terminal", "1");
        JSONObject jsonObject = new JSONObject(parms1);
        OkGo.post(Urls.NEW_Ip_url+Urls.DataStatistics.APPLY)
                .tag(context)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                    }
                });
    }
}
