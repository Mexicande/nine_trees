package cn.com.stableloan.utils.constant;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import cn.com.stableloan.api.Urls;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by apple on 2017/7/2.
 */

public class QiNiu_Token {


    private String to;

    public String getToken(Context context){

        OkGo.<String>post(Urls.NEW_URL+Urls.Pictrue.GET_QINIUTOKEN)
                .tag(context)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                            to=s;

                    }
                });


        return to;
    }

}
