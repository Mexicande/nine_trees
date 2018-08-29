package cn.com.cashninetrees.ui.js;

import android.webkit.JavascriptInterface;

import cn.com.cashninetrees.AppApplication;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.common.Constants;
import cn.com.cashninetrees.utils.SPUtil;

/**
 * Created by apple on 2017/8/1.
 */

public class JsInteration {

    @JavascriptInterface
    public String getToken() {
        return AppApplication.getToken();

    }

}
