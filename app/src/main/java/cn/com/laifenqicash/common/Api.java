package cn.com.laifenqicash.common;

import android.content.Context;

import org.json.JSONObject;

import cn.com.laifenqicash.interfaceutils.OnRequestDataListener;

/**
 * Created by apple on 2018/3/23.
 */

public class Api {



    public static void getReQuest(String url, final Context context, JSONObject s, final OnRequestDataListener listener) {
        QuestionPost.newExcuteJsonPost(url, context, s,listener);
    }

}
