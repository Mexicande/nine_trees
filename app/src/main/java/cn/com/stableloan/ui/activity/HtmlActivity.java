package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mancj.slideup.SlideUp;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.DateStatisticsUtils;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.bean.cash.CashActivityBean;
import cn.com.stableloan.model.Banner_HotBean;
import cn.com.stableloan.model.Product_DescBean;
import cn.com.stableloan.model.WelfareBean;
import cn.com.stableloan.model.WelfareShutBean;
import cn.com.stableloan.model.clsaa_special.Class_Special;
import cn.com.stableloan.ui.js.JsInteration;
import cn.com.stableloan.utils.DownAPKService;
import cn.com.stableloan.utils.NetworkUtils;
import cn.com.stableloan.utils.SPUtil;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.top_menu.MenuItem;
import cn.com.stableloan.utils.top_menu.TopRightMenu;
import cn.com.stableloan.view.dialog.SafeInformationDialog;
import cn.com.stableloan.view.share.StateListener;
import cn.com.stableloan.view.share.TPManager;
import cn.com.stableloan.view.share.WXManager;
import cn.com.stableloan.view.share.WXShareContent;
import okhttp3.Call;
import okhttp3.Response;

public class HtmlActivity extends UmengNotifyClickActivity {

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
    @Bind(R.id.error_content)
    LinearLayout errorContent;
    @Bind(R.id.bt_share)
    ImageView btShare;
    @Bind(R.id.layout_wx)
    LinearLayout layoutWx;
    @Bind(R.id.layout_friend)
    LinearLayout layoutFriend;
    @Bind(R.id.cash_slideView)
    RelativeLayout cashSlideView;
    private SlideUp slideUp;
    private boolean umeng=false;

    private WebView mWebView;
    private WelfareBean.DataBean welfare;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, HtmlActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        ButterKnife.bind(this);
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.colorPrimary)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();
        mWebView = new WebView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(lp);
        mContainer.addView(mWebView, 0);
        initSlide();
        CheckInternet();
    }


    @Override
    public void onResume() {
        super.onResume();
        Bundle bun = getIntent().getExtras();
        if(bun!=null){
            String title = bun.getString("title");
            String link = bun.getString("link");
            if(link!=null){
                umeng=true;
                titleName.setText(title);
                getDate(link);
            }
        }

    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        if(body!=null){
            try {
                JSONObject jsonObject=new JSONObject(body);
                JSONObject extra = jsonObject.getJSONObject("extra");
                String title = extra.getString("title");
                String link = extra.getString("link");
                if(link!=null){
                    umeng=true;
                    titleName.setText(title);
                    getDate(link);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void CheckInternet() {

        boolean available = NetworkUtils.isAvailable(this);
        if (available) {
            Product_DescBean.DataBean desc = (Product_DescBean.DataBean) getIntent().getSerializableExtra("product");
            if (desc != null) {
                DateStatisticsUtils.addApplyDate(this, String.valueOf(desc.getId()));
                titleName.setText(desc.getPname());
                String link = desc.getLink();
                getDate(link);
            }
            String advertising = getIntent().getStringExtra("advertising");
            if (advertising != null) {
                getDate(advertising);
            }
            welfare = (WelfareBean.DataBean) getIntent().getSerializableExtra("welfare");
            if (welfare != null) {
                titleName.setText(welfare.getName());
                getUrl(welfare.getId());
                setListen();
            }
            Class_Special.DataBean.MdseBean aClass = (Class_Special.DataBean.MdseBean) getIntent().getSerializableExtra("class");
            if (aClass != null) {
                DateStatisticsUtils.addApplyDate(this, String.valueOf(aClass.getId()));
                titleName.setText(aClass.getMdse_name());
                getDate(aClass.getMdse_h5_link());
            }

            CashActivityBean.DataBean.ActivityBean cashActivityBean = (CashActivityBean.DataBean.ActivityBean) getIntent().getSerializableExtra("cashActivityBean");
            if (cashActivityBean != null) {
                titleName.setText(cashActivityBean.getTitle());
                getDate(cashActivityBean.getUrl());
            }
            String insurance = getIntent().getStringExtra("Insurance");
            if(insurance!=null){
                String link = getIntent().getStringExtra("link");
                getDate(link);
                setListen();
            }
            String link = getIntent().getStringExtra("link");
            if(!TextUtils.isEmpty(link)){
                String title = getIntent().getStringExtra("title");
                titleName.setText(title);
                getDate(link);
            }

        } else {
            ToastUtils.showToast(this, "网络异常");
        }
    }

    private void getUrl(int id) {

        String   token = SPUtil.getString(this, Urls.lock.TOKEN);
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("id", String.valueOf(id));
        final JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Urls.Ip_url + Urls.LOTTERY.Welfare)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (!s.isEmpty()) {
                            Gson gson = new Gson();
                            WelfareShutBean shutBean = gson.fromJson(s, WelfareShutBean.class);
                            if (shutBean.getError_code() == 0) {
                                errorContent.setVisibility(View.GONE);
                                getDate(shutBean.getData().getLink());
                            } else {
                                errorContent.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });


    }

    private void getDate(String url) {
        if (url != null) {
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setBlockNetworkImage(false);
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webSettings.setGeolocationEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setDatabaseEnabled(true);
            webSettings.setAppCacheEnabled(true);
            webSettings.setSupportZoom(false);
            webSettings.setNeedInitialFocus(false);
            webSettings.setLoadsImagesAutomatically(true);
            webSettings.setBuiltInZoomControls(false);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mWebView.getSettings().setMixedContentMode(
                        WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            mWebView.loadUrl(url);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    if (parseScheme(url)) {

                    } else {

                        WebView.HitTestResult hitTestResult = view.getHitTestResult();
                        if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                            view.loadUrl(url);
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();

                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    String insurance = getIntent().getStringExtra("Insurance");
                    if(insurance!=null){
                        titleName.setText(view.getTitle());
                    }

                }
            });

            mWebView.setWebChromeClient(new MyWebChromeClient());
            mWebView.addJavascriptInterface(new JsInteration(), "android");

        }
    }


    public boolean parseScheme(String url) {
        //支付宝支付
        if (url.startsWith("alipays:") || url.startsWith("alipay")) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            } catch (Exception e) {
                e.printStackTrace();

/*
                Uri uri = Uri.parse(url);
                    Intent intent;
                try {
                    intent = Intent.parseUri(url,
                            Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    startActivity(intent);
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }*/

            }
            return true;
        } else if (url.contains("qqapi")) {
            try {
                Uri uri = Uri.parse(url);
                Intent intent;
                intent = Intent.parseUri(url,
                        Intent.URI_INTENT_SCHEME);
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setComponent(null);
                startActivity(intent);
            } catch (Exception e) {
                ToastUtils.showToast(this, "请安装最新版腾讯QQ");
            }
            return true;
        } else if (url.contains("tmast://appdetails?")) {
            try {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(HtmlActivity.this, "请安装最新版应用宝", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (url.endsWith(".apk")) {
                downloadApk(url);
            return true;

        } else if(url.startsWith("weixin://wap/pay?") || url.startsWith("weixin")|| url.startsWith("wechat")){
            try{

                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));

            }catch(Exception e) {

            e.printStackTrace();
                ToastUtils.showToast(this,e.getMessage());
         }
            return true;
        }else {
            return  false;
        }

    }

    /**
     * 应用内拦截下载
     */
    private void downloadApk(String url) {

        Intent intent = new Intent(this, DownAPKService.class);
        intent.putExtra("apk_url", url);
        startService(intent);

    }


    @OnClick({R.id.iv_back, R.id.web_container,R.id.layout_wx, R.id.layout_friend,R.id.layoutGo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if(umeng){
                    startActivity(new Intent(this, MainActivity.class));
                }
                finish();
                break;
            case R.id.web_container:
                if (welfare != null) {
                    getUrl(welfare.getId());
                }
                break;
            case R.id.layout_wx:
                shareWechat(WXShareContent.WXSession);

                break;
            case R.id.layout_friend:
                shareWechat(WXShareContent.WXTimeline);

                break;
            case R.id.layoutGo:
                slideUp.hide();
                break;
            default:

                break;
        }
    }



    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                if (mProgressBar.getVisibility() != View.VISIBLE) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
            }
        }
    }

    private void initSlide() {
        slideUp = new SlideUp.Builder(cashSlideView)
                .withListeners(new SlideUp.Listener.Slide() {
                    @Override
                    public void onSlide(float percent) {
                    }
                })
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();

    }

    private void setListen() {
        btShare.setVisibility(View.VISIBLE);
        TPManager.getInstance().initAppConfig(Urls.KEY.WEICHAT_APPID, null, null, null);
        wxManager = new WXManager(this);
        StateListener<String> wxStateListener = new StateListener<String>() {
            @Override
            public void onComplete(String s) {
                ToastUtils.showToast(HtmlActivity.this, s);
            }

            @Override
            public void onError(String err) {
                ToastUtils.showToast(HtmlActivity.this, err);
            }

            @Override
            public void onCancel() {
                ToastUtils.showToast(HtmlActivity.this, "取消");
            }
        };

        wxManager.setListener(wxStateListener);

        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShared_Collection();
            }
        });
    }

    /**
     * 分享菜单
     */
    private void setShared_Collection() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("跳过"));
        menuItems.add(new MenuItem("分享"));
        TopRightMenu mTopRightMenu = new TopRightMenu(this,R.layout.html_item_popup_menu_list);
        mTopRightMenu
                //显示菜单图标，默认为true
                .showIcon(false)     //显示菜单图标，默认为true
                .dimBackground(true)           //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setWidth(320)
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        switch (position) {
                            case 0:
                                exit();
                                break;
                            case 1:
                                slideUp.show();
                                break;
                            default:
                                break;
                        }

                    }
                })
                .showAsDropDown(btShare, -225, 0);
    }
    private SafeInformationDialog safeSettingDialog;
    private void exit() {

        safeSettingDialog = new SafeInformationDialog(this);
        safeSettingDialog.setTitle("跳过保险");
        safeSettingDialog.setMessage("如跳过申请\n"+"稍后可在“福利”-“免费百万险”\n"+"中获取或查询");
        safeSettingDialog.setYesOnclickListener("跳过", new SafeInformationDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                safeSettingDialog.dismiss();
                WelfareBean.DataBean welfare = (WelfareBean.DataBean) getIntent().getSerializableExtra("welfare");
                if(welfare!=null){
                    finish();
                }else {
                    MainActivity.launch(HtmlActivity.this);
                    finish();
                }
            }
        });
        safeSettingDialog.setNoOnclickListener("留下", new SafeInformationDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {


                safeSettingDialog.dismiss();

            }
        });
        safeSettingDialog.show();

    }

    /**
     * 微信分享
     */
    private WXManager wxManager;

    private void shareWechat(int scence) {

        WXShareContent contentWX = new WXShareContent();
        contentWX.setScene(scence)
                .setType(WXShareContent.share_type.WebPage)
                .setWeb_url(Urls.share.htmlInsurance)
                .setTitle("安稳钱包")
                .setDescription("最高百万保险我送你了,当意外在所难免，尽可能安稳的回到正轨")
                .setImage_url(Urls.logoUrl);
        wxManager.share(contentWX);

    }


    @Override
    public void onBackPressed() {
        if (slideUp!=null&&slideUp.isVisible()) {
            slideUp.hide();
        } else {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                if(umeng){
                    startActivity(new Intent(this, MainActivity.class));
                }
                finish();

            }
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
}
