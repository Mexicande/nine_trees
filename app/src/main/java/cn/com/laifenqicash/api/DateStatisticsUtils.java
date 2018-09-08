package cn.com.laifenqicash.api;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.laifenqicash.utils.SPUtil;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author apple
 * @date 2017/10/25
 *
 */

public class DateStatisticsUtils {

    public  static void addApplyDate(Context context,String productId){

        String token = SPUtil.getString(context, Urls.lock.TOKEN, "1");
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
