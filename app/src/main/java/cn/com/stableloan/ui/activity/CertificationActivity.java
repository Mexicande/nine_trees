package cn.com.stableloan.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

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
import cn.com.stableloan.ui.activity.settingdate.DeviceActivity;
import cn.com.stableloan.ui.fragment.ThreeElementsFragment;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.supertextview.SuperTextView;
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
    @Bind(R.id.layout_go)
    LinearLayout layoutGo;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.alipay)
    SuperTextView alipay;
    @Bind(R.id.taobao)
    SuperTextView taobao;
    @Bind(R.id.mobile)
    SuperTextView mobile;
    @Bind(R.id.contact)
    SuperTextView contact;
    @Bind(R.id.loction)
    SuperTextView loction;
    private static  final  int IMAGE_RESULT=110;
    private static final int WITHDRAW_CODE = 1;

    private String phone;
    private KProgressHUD hud;
    private CrawlerStatus crawlerStatus = new CrawlerStatus();
    private  Certification certification=new Certification();
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
        titleName.setText("授权材料");


        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getResources().getString(R.string.wait))
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
                        certification = gson.fromJson(s, Certification.class);
                        int error_code = certification.getError_code();
                        hud.dismiss();
                        if (error_code == 0) {

                            Certification.DataBean data = certification.getData();


                            crawlerStatus.real_name = data.getName();//姓名
                            crawlerStatus.id_card = data.getIdcard();//身份证
                            crawlerStatus.cellphone = data.getUserphone();//手机

                                if (data.getAliStatus() == 1) {
                                    alipay.setLeftBottomString(data.getAliaccount());
                                    Drawable drawable = ContextCompat.getDrawable(CertificationActivity.this, R.drawable.button_succeed);
                                    alipay.setTextBackground(drawable);
                                    alipay.setRightString("已完成");
                                }else {
                                    Drawable drawable = ContextCompat.getDrawable(CertificationActivity.this, R.drawable.button_fail);
                                    alipay.setTextBackground(drawable);
                                    alipay.setRightString("未完成");
                                }

                                if (data.getTaobaoStatus() == 1) {
                                    Drawable drawable = ContextCompat.getDrawable(CertificationActivity.this, R.drawable.button_succeed);
                                    taobao.setTextBackground(drawable);
                                    taobao.setRightString("已完成");
                                }else {
                                    Drawable drawable = ContextCompat.getDrawable(CertificationActivity.this, R.drawable.button_fail);
                                    taobao.setTextBackground(drawable);
                                    taobao.setRightString("未完成");
                                }
                                if (data.getCapStatus() == 1) {
                                    Drawable drawable = ContextCompat.getDrawable(CertificationActivity.this, R.drawable.button_succeed);
                                    mobile.setTextBackground(drawable);
                                    mobile.setRightString("已认证");
                                }else {
                                    Drawable drawable = ContextCompat.getDrawable(CertificationActivity.this, R.drawable.button_fail);
                                    mobile.setTextBackground(drawable);
                                    mobile.setRightString("未完成");
                                }

                        }else {
                            ToastUtils.showToast(CertificationActivity.this, certification.getError_message());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                                hud.dismiss();

                    }
                });

    }

    private void initView() {

        AndPermission.with(CertificationActivity.this)
                .requestCode(200)
                .permission(Manifest.permission.READ_CONTACTS)
                .callback(listener)
                .start();

        phone = (String) SPUtils.get(CertificationActivity.this, Urls.lock.USER_PHONE, "1");
        crawlerStatus.privatekey = Urls.CreditrePort.PRIVATE_KEY;
        crawlerStatus.merchant_id = Urls.CreditrePort.APP_ID;
        // "1000053";
        crawlerStatus.appname = Urls.CreditrePort.APP_NAME;
        //  "CreditReport";
        crawlerStatus.taskid = String.valueOf(System.currentTimeMillis());

        crawlerStatus.obtainExtraParams().put("user_id", phone);



    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
                Drawable drawable = ContextCompat.getDrawable(CertificationActivity.this, R.drawable.button_succeed);
                contact.setTextBackground(drawable);
                contact.setRightString("已认证");
                }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                // TODO ...
                Drawable drawable = ContextCompat.getDrawable(CertificationActivity.this, R.drawable.button_fail);
                contact.setTextBackground(drawable);
                contact.setRightString("未认证");
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        hud.dismiss();

    }

    @OnClick({R.id.mobile, R.id.alipay, R.id.taobao, R.id.layout_go})
    public void onViewClicked(View view) {
        Certification.DataBean data = certification.getData();
        switch (view.getId()) {
            case R.id.layout_go:
                String from = getIntent().getStringExtra("from");

                if(from!=null&&"cash".equals(from)){
                    Intent intent=new Intent();
                    setResult(WITHDRAW_CODE,intent);
                }else {
                    Intent intent=new Intent();
                    intent.putExtra("ok", 1);
                    setResult(IMAGE_RESULT,intent);
                }
                finish();
                break;
            case R.id.mobile:
                if(data.getName()==null||data.getIdcard()==null||data.getUserphone()==null){
                    ThreeElementsFragment recordFragment = ThreeElementsFragment.newInstance();
                    recordFragment.show(getSupportFragmentManager(),"recordFragment");
                    recordFragment.setCancelable(false);
                }else {

                    CrawlerCallBack callBack = new CrawlerCallBack() {
                        @Override
                        public void onStatus(CrawlerStatus crawlerStatus) {
                            LogUtils.i("mobile", crawlerStatus.status);
                            switch (crawlerStatus.status) {
                                case 2:
                                    ToastUtils.showToast(CertificationActivity.this, "认证需要几分钟,请稍后再来查看");
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    ToastUtils.showToast(CertificationActivity.this, "抓取失败,请重新抓取");
                                    break;
                                default:
                                    break;
                            }
                        }
                    };
                    crawlerStatus.type = "mobile";
                    CrawlerManager.getInstance().startCrawlerByType(callBack, crawlerStatus);
                }
                break;
            case R.id.taobao:
                if(data.getName()==null||data.getIdcard()==null||data.getUserphone()==null){
                    ThreeElementsFragment recordFragment = ThreeElementsFragment.newInstance();
                    recordFragment.show(getSupportFragmentManager(),"recordFragment");
                    recordFragment.setCancelable(false);
                }else {

                    CrawlerCallBack taobaoCallBack = new CrawlerCallBack() {
                        @Override
                        public void onStatus(CrawlerStatus crawlerStatus) {
                            LogUtils.i("taobao", crawlerStatus.status);
                            switch (crawlerStatus.status) {
                                case 2:
                                    ToastUtils.showToast(CertificationActivity.this, "认证需要几分钟,请稍后再来查看");
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    ToastUtils.showToast(CertificationActivity.this, "认证失败,请重新认证");
                                    break;
                                default:
                                    break;
                            }
                        }
                    };
                    crawlerStatus.type = "taobao";
                    CrawlerManager.getInstance().startCrawlerByType(taobaoCallBack, crawlerStatus);
                }
                break;
            case R.id.alipay:
                String mApiKey = Urls.Api_Id;
                String mThemeColor = "#ff9500";
                MxParam mxParam = new MxParam();
                mxParam.setUserId(phone);
                mxParam.setApiKey(mApiKey);
                mxParam.setThemeColor(mThemeColor);  // SDK里页面主色调
                mxParam.setFunction(MxParam.PARAM_FUNCTION_ALIPAY); // 功能名
                MoxieSDK.getInstance().start(this, mxParam, new MoxieCallBack() {
                    @Override
                    public boolean callback(MoxieContext moxieContext, MoxieCallBackData moxieCallBackData) {
                        if (moxieCallBackData != null) {
                            LogUtils.i("moxieCallBackData", moxieCallBackData.toString());
                            LogUtils.i("code", moxieCallBackData.getCode());
                            int code = moxieCallBackData.getCode();
                            switch (code) {
                                case 3:
                                    ToastUtils.showToast(CertificationActivity.this, "认证需要几分钟,请稍后再来查看");
                                    break;
                                case 2:
                                    ToastUtils.showToast(CertificationActivity.this, "认证失败,请重新认证");
                                    break;
                                default:
                                    break;
                            }
                            moxieContext.finish();
                            return true;
                        }
                        return false;
                    }
                });

                break;

            default:
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String from = getIntent().getStringExtra("from");
            if("cash".equals(from)){
                Intent intent=new Intent();
                setResult(WITHDRAW_CODE,intent);
            }else {
                Intent intent=new Intent();
                intent.putExtra("ok", 1);
                setResult(IMAGE_RESULT,intent);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CrawlerManager.getInstance().unRegisterAllCallBack();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Urls.REQUEST_CODE.PULLBLIC_CODE){
            getStatus();
        }
    }
}
