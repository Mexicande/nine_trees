package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.Banner_HotBean;
import cn.com.stableloan.model.Class_ListProductBean;
import cn.com.stableloan.model.Product_DescBean;
import cn.com.stableloan.utils.NetworkUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class HtmlActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.web_progress_bar)
    ProgressBar mProgressBar;


    @Bind(R.id.web_container)
    FrameLayout mContainer;

    private WebView mWebView;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, HtmlActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        ButterKnife.bind(this);
        mWebView = new WebView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(lp);
        mContainer.addView(mWebView, 0);
        CheckInternet();
    }

    private void CheckInternet() {

        boolean available = NetworkUtils.isAvailable(this);
        if (available) {
            Product_DescBean desc= (Product_DescBean) getIntent().getSerializableExtra("product");
            if(desc!=null){
                titleName.setText(desc.getPlatformdetail().getPl_name());
                ivBack.setVisibility(View.VISIBLE);
                String link = desc.getProduct().getLink();
                getDate(link);
            }
            Banner_HotBean.AdvertisingBean extra = (Banner_HotBean.AdvertisingBean) getIntent().getSerializableExtra("Advertising");
            if(extra!=null){
                titleName.setText(extra.getAdvername());
                ivBack.setVisibility(View.VISIBLE);
                getDate(extra.getApp());
            }
            Banner_HotBean.RecommendsBean hotbean = (Banner_HotBean.RecommendsBean) getIntent().getSerializableExtra("hotbean");
            if(hotbean!=null){
                titleName.setText(hotbean.getName());
                ivBack.setVisibility(View.VISIBLE);
                getDate(hotbean.getApp());
            }
            Product_DescBean desc1= (Product_DescBean) getIntent().getSerializableExtra("Strate");
            if(desc1!=null){
                titleName.setText(desc1.getPlatformdetail().getPl_name()+"攻略");
                ivBack.setVisibility(View.VISIBLE);

                String url = desc1.getProduct().getRaiders_connection();
                getDate(url);
            }
            String bank = getIntent().getStringExtra("bank");
            if(bank!=null){
                titleName.setText("信用卡分类");
                ivBack.setVisibility(View.VISIBLE);
                getDate(bank);
            }
          /*  if(product!=null){
                titleName.setText(product.getAdvername());
                ivBack.setVisibility(View.VISIBLE);
                getDate(product.getApp());
            }*/


        } else {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("网络异常，请检查网络")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    })
                    .show();
        }
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
                        view.loadUrl(url);
                        return false;
                    }
                });
                mWebView.setWebChromeClient(new MyWebChromeClient());


        }
    }
    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                if (mProgressBar.getVisibility() != View.VISIBLE)
                    mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContainer.removeAllViews();
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
