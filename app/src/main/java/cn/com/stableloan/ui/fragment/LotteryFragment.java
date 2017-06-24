package cn.com.stableloan.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.MainActivity;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.statuslayout.FadeViewAnimProvider;
import cn.com.stableloan.view.statuslayout.StateLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LotteryFragment extends ImmersionFragment {


    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.web_progress_bar)
    ProgressBar webProgressBar;
    @Bind(R.id.web_container)
    FrameLayout webContainer;
    @Bind(R.id.stateLayout)
    StateLayout stateLayout;

    private WebView mWebView;

    private static final int LOTTERY_CODE = 500;
    private static final int LOTTERY_SNED = 5000;


    public LotteryFragment() {
        // Required empty public constructor
    }

    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity()).statusBarColor(R.color.colorPrimary)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lottery, container, false);
        ButterKnife.bind(this, view);
        stateLayout.setViewSwitchAnimProvider(new FadeViewAnimProvider());
        titleName.setText("彩票");
        mWebView = new WebView(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(lp);
        webContainer.addView(mWebView, 0);
        stateLayout.setErrorAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckInternet();
            }
        });
        CheckInternet() ;
        return view;

    }



    private KProgressHUD hud;

    private void CheckInternet() {
        stateLayout.showProgressView();
        String token = (String) SPUtils.get(getActivity(), "token", "1");

        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        final JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Urls.puk_URL + Urls.LOTTERY.GetLottery)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        stateLayout.showContentView();
                        try {
                            JSONObject object = new JSONObject(s);
                            String isSuccess = object.getString("isSuccess");
                            if (isSuccess.equals("1")) {
                                stateLayout.setVisibility(View.GONE);
                                String qian = object.getString("qian");
                                String date = object.getString("data");
                                String hou = object.getString("hou");
                                getDate(qian+date+hou);

                            }else {
                                stateLayout.showErrorView();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        stateLayout.showErrorView();
                    }
                });

    }

    private void getDate(String url) {
        //String html = getIntent().getStringExtra("html");
        if (url != null) {
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setGeolocationEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setDatabaseEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            webSettings.setAllowFileAccess(true);
            webSettings.setAppCacheEnabled(true);
            webSettings.setDisplayZoomControls(false);


            if (Build.VERSION.SDK_INT >= 21) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            mWebView.loadUrl(url);

            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    LogUtils.i("webView",url);
                    if (parseScheme(url)) {

                    } else {
                        view.loadUrl(url);
                    }

                    return true;
                }
            });

            mWebView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {

                        mWebView.goBack();

                        return true;

                    }
                    return false;
                }
            });
            mWebView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        webProgressBar.setVisibility(View.GONE);
                    } else {
                        if (webProgressBar.getVisibility() != View.VISIBLE)
                            webProgressBar.setVisibility(View.VISIBLE);
                        webProgressBar.setProgress(newProgress);
                    }
                }
            });



        }
    }


    public boolean parseScheme(String url) {
        if (url.contains("platformapi/startapp")) {
            try {
                Uri uri = Uri.parse(url);
                Intent intent;
                intent = Intent.parseUri(url,
                        Intent.URI_INTENT_SCHEME);
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setComponent(null);
                startActivity(intent);
            } catch (Exception e) {
                ToastUtils.showToast(getActivity(),"请安装最新版支付宝");
            }
            return true;
        } else if(url.contains("qqapi")){
            try {
                Uri uri = Uri.parse(url);
                Intent intent;
                intent = Intent.parseUri(url,
                        Intent.URI_INTENT_SCHEME);
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setComponent(null);
                startActivity(intent);
            } catch (Exception e) {
                ToastUtils.showToast(getActivity(),"请安装最新版腾讯QQ");
            }
            return true;
        }else {
            return false;
        }
    }

    //qwallet100703379


/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOTTERY_SNED:
                if (LOTTERY_CODE == resultCode) {
                    String loffery = data.getStringExtra("Loffery");
                    if (!loffery.equals("1")) {
                        CheckInternet();
                    }else {
                        Main1Activity.navigationController.setSelect(0);
                    }
                }
                break;
        }
    }*/

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
