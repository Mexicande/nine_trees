package cn.com.stableloan.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.moxie.client.manager.MoxieCallBack;
import com.moxie.client.manager.MoxieCallBackData;
import com.moxie.client.manager.MoxieContext;
import com.moxie.client.manager.MoxieSDK;
import com.moxie.client.model.MxParam;
import com.rong360.app.crawler.CrawlerCallBack;
import com.rong360.app.crawler.CrawlerManager;
import com.rong360.app.crawler.CrawlerStatus;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.Certification;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 第三方认证
 */
public class CertificationActivity extends BaseActivity {
    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.text_goBack)
    TextView textGoBack;
    @Bind(R.id.layout_go)
    LinearLayout layoutGo;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.alipay)
    SuperTextView alipay;
   /* @Bind(R.id.jd)
    SuperTextView jd;*/
    @Bind(R.id.mobile)
    SuperTextView mobile;
    @Bind(R.id.contact)
    SuperTextView contact;
    @Bind(R.id.taobao)
    SuperTextView taobao;

    private String phone;
    private KProgressHUD hud;
    private CrawlerStatus crawlerStatus = new CrawlerStatus();

    public static void launch(Context context) {
        context.startActivity(new Intent(context, CertificationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        getStatus();
        initView();

    }

    private void getStatus() {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(true)
                .show();
        String token = (String) SPUtils.get(this, "token", "1");

        OkGo.<String>post(Urls.Ip_url + Urls.STATUS.GetCerftication)
                .tag(this)
                .params("token", token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        Certification fromJson = gson.fromJson(s, Certification.class);
                        int error_code = fromJson.getError_code();
                        hud.dismiss();
                        if (error_code == 0) {
                            Certification.DataBean data = fromJson.getData();
                            if (data != null) {
                                if (data.getAliStatus() == 1) {
                                    alipay.setRightString("已认证");
                                } else if (data.getTaobaoStatus() == 1) {
                                    taobao.setRightString("已认证");
                                }else if(data.getCapStatus()==1){
                                    mobile.setRightString("已认证");
                                }
                            }

                        } else {
                            ToastUtils.showToast(CertificationActivity.this, fromJson.getError_message());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        CertificationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hud.dismiss();
                            }
                        });

                    }
                });

    }

    private void initView() {
        AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.READ_CONTACTS)
                .callback(listener)
                .start();

        TinyDB tinyDB = new TinyDB(this);
        UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
        phone = user.getUserphone();
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", phone);
        crawlerStatus.privatekey = Urls.CreditrePort.PRIVATE_KEY;
        crawlerStatus.merchant_id = Urls.CreditrePort.APP_ID;// "1000053";
        crawlerStatus.appname = Urls.CreditrePort.APP_NAME;//  "CreditReport";
        crawlerStatus.hashMap = params;
        crawlerStatus.taskid = String.valueOf(System.currentTimeMillis());

    }
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if(requestCode == 200) {
                contact.setRightString("已认证");
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if(requestCode == 200) {
                // TODO ...
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        hud.dismiss();
    }

    @OnClick({R.id.mobile, R.id.alipay, R.id.taobao})
    public void onViewClicked(View view) {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(true)
                .show();
        switch (view.getId()) {
            case R.id.mobile:

                CrawlerCallBack callBack = new CrawlerCallBack() {
                    @Override
                    public void onStatus(CrawlerStatus crawlerStatus) {
                        LogUtils.i("mobile", crawlerStatus.status);
                        switch (crawlerStatus.status) {
                            case 2:
                                ToastUtils.showToast(CertificationActivity.this,"认证需要几分钟,请稍后再来查看");
                                break;
                            case 3:
                                break;
                            case 4:
                                ToastUtils.showToast(CertificationActivity.this,"抓取失败,请重新抓取");
                                break;
                        }
                    }
                };
                crawlerStatus.type = "mobile";
                CrawlerManager.getInstance(this.getApplication()).startCrawlerByType(callBack, crawlerStatus);
                break;
            case R.id.taobao:
                CrawlerCallBack taobaoCallBack = new CrawlerCallBack() {
                    @Override
                    public void onStatus(CrawlerStatus crawlerStatus) {
                        LogUtils.i("taobao", crawlerStatus.status);
                        switch (crawlerStatus.status) {
                            case 2:
                                ToastUtils.showToast(CertificationActivity.this,"认证需要几分钟,请稍后再来查看");
                                break;
                            case 3:
                                break;
                            case 4:
                                ToastUtils.showToast(CertificationActivity.this,"认证失败,请重新认证");
                                break;
                        }
                    }
                };
                crawlerStatus.type = "taobao";
                CrawlerManager.getInstance(this.getApplication()).startCrawlerByType(taobaoCallBack, crawlerStatus);

                break;
            case R.id.alipay:
                String mApiKey = Urls.Api_Id;
                String mThemeColor= "#ff9500";
                MxParam mxParam = new MxParam();
                mxParam.setUserId(phone);
                mxParam.setApiKey(mApiKey);
                mxParam.setThemeColor(mThemeColor);  // SDK里页面主色调
                mxParam.setFunction(MxParam.PARAM_FUNCTION_ALIPAY); // 功能名
                MoxieSDK.getInstance().start(this, mxParam, new MoxieCallBack() {
                    @Override
                    public boolean callback(MoxieContext moxieContext, MoxieCallBackData moxieCallBackData) {
                        if (moxieCallBackData != null) {
                            LogUtils.i("moxieCallBackData",moxieCallBackData.toString());
                            LogUtils.i("code",moxieCallBackData.getCode());
                            int code = moxieCallBackData.getCode();
                            switch (code) {
                                case 3:
                                    ToastUtils.showToast(CertificationActivity.this, "认证需要几分钟,请稍后再来查看");
                                    break;
                                case 2:
                                    ToastUtils.showToast(CertificationActivity.this, "认证失败,请重新认证");
                                    break;
                            }
                            moxieContext.finish();
                            return true;
                        }
                        return false;
                    }
                });

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CrawlerManager.getInstance(this.getApplication()).unregistAllCallBack();
    }
}