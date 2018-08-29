package cn.com.cashninetrees.common;

import android.content.Context;

import org.json.JSONObject;

import cn.com.cashninetrees.interfaceutils.OnRequestDataListener;

/**
 * Created by apple on 2018/3/23.
 */

public class Api {



    public static void getReQuest(String url, final Context context, JSONObject s, final OnRequestDataListener listener) {
        QuestionPost.newExcuteJsonPost(url, context, s,listener);
    }

}
