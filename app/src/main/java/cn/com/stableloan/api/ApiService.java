package cn.com.stableloan.api;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.interfaceutils.OnRequestDataListener;
import okhttp3.Call;


/**
 * Created by apple on 2018/4/13.
 */

public class ApiService {
    /**
     * @param params
     * @param listener
     * banner
     */
    public static void GET_SERVICE(String url,JSONObject params, final OnRequestDataListener listener) {
        newExcuteJsonPost(url,params,listener);
    }

    private static void newExcuteJsonPost(String url,  JSONObject params, final OnRequestDataListener listener){
        final String netError = AppApplication.getApp().getString(R.string.error_net);
        OkGo.<String>post(url)
                .tag(AppApplication.getApp())
                .upJson(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        if(response.body()!=null){
                            try {
                                JSONObject jsonObject=new JSONObject(s);
                                int code = jsonObject.getInt("error_code");
                                if(code==0){
                                    listener.requestSuccess(code, jsonObject);
                                }else {
                                    listener.requestFailure(code, jsonObject.getString("error_message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            listener.requestFailure(-1, netError);
                        }
                    }

                    @Override
                    public void onError(Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        listener.requestFailure(-1, netError);

                    }
                });


    }

}