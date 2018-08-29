package cn.com.cashninetrees.utils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.cashninetrees.api.ApiService;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.interfaceutils.OnRequestDataListener;

/**
 *
 * @author apple
 * @date 2018/5/11
 * 统计提取
 */

public class OnClickStatistics {

    public static  void buriedStatistics(String token,String type){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",type);
            jsonObject.put("token",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiService.GET_SERVICE(Urls.CLICK, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {

            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

}

