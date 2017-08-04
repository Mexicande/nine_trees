package cn.com.stableloan.ui.js;

import android.webkit.JavascriptInterface;

import cn.com.stableloan.AppApplication;
import cn.com.stableloan.utils.SPUtils;

/**
 * Created by apple on 2017/8/1.
 */

public class JsInteration {

    @JavascriptInterface
    public String getToken() {

        return AppApplication.getToken();

    }

}
