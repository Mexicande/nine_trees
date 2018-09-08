package cn.com.laifenqicash.ui.js;

import android.webkit.JavascriptInterface;

import cn.com.laifenqicash.AppApplication;
import cn.com.laifenqicash.api.Urls;
import cn.com.laifenqicash.common.Constants;
import cn.com.laifenqicash.utils.SPUtil;

/**
 * Created by apple on 2017/8/1.
 */

public class JsInteration {

    @JavascriptInterface
    public String getToken() {
        return AppApplication.getToken();

    }

}
