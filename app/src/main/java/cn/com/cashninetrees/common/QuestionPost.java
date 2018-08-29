package cn.com.cashninetrees.common;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.cashninetrees.R;
import cn.com.cashninetrees.interfaceutils.OnRequestDataListener;
import okhttp3.Call;


/**
 * Created by admin on 2018/3/19.
 */

public class QuestionPost {

    protected static void newExcuteJsonPost(String url, final Context context, JSONObject  s, final OnRequestDataListener listener){
        final String netError = context.getString(R.string.error_net);
        OkGo.<String>post(url)
                .tag(context)
                .upJson(s.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        if(response.body()!=null){
                            try {
                                JSONObject jsonObject=new JSONObject(s);
                                int error_code = jsonObject.getInt("error_code");
                                if(error_code==0){
                                    JSONObject data = jsonObject.getJSONObject("data");

                                    listener.requestSuccess(0, data);
                                }else {
                                    listener.requestFailure(-1, jsonObject.getString("error_message"));
                                }

                            } catch (JSONException ignored) {

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
