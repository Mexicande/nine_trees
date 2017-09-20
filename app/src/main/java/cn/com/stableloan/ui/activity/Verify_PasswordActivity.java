package cn.com.stableloan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.MessageEvent;
import cn.com.stableloan.model.PicStatusEvent;
import cn.com.stableloan.utils.EncryptUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

public class Verify_PasswordActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.layout_go)
    LinearLayout layoutGo;
    @Bind(R.id.et_PassWord)
    FormEditText etPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__password);
        ButterKnife.bind(this);
        initToolbar();

    }

    private void initToolbar() {
        titleName.setText("身份验证");
        etPassWord.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
    }


    @OnClick({R.id.layout_go, R.id.bt_Validation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_go:
                finish();
                break;
            case R.id.bt_Validation:
                VerifyPassWord();
                break;
        }
    }
    private  KProgressHUD hud;
    private void VerifyPassWord() {
        String password = etPassWord.getText().toString();
        String token = (String) SPUtils.get(this, "token", "1");
        if(!password.isEmpty()){
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait.....")
                    .setCancellable(true)
                    .show();
            String md5ToString = EncryptUtils.encryptMD5ToString(password);
            Map<String,String> parms=new HashMap<>();
            parms.put("token",token);
            parms.put("password",md5ToString);
                JSONObject jsonObject = new JSONObject(parms);
                OkGo.<String>post(Urls.NEW_URL+Urls.Login.USER_INFOMATION)
                        .tag(this)
                        .upJson(jsonObject)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                hud.dismiss();
                                    if(s!=null){
                                        try {
                                            JSONObject json = new JSONObject(s);
                                            String isSuccess = json.getString("isSuccess");
                                            if("1".equals(isSuccess)){
                                                String from = getIntent().getStringExtra("from");
                                                if(from!=null){
                                                    String signature = json.getString("signature");
                                                    SPUtils.put(Verify_PasswordActivity.this,"signature",signature);
                                                    if("unLock".equals(from)){
                                                        CreateGestureActivity.launch(Verify_PasswordActivity.this);
                                                        finish();
                                                    }else if ("userinformation".equals(from)){
                                                        UserInformationActivity.launch(Verify_PasswordActivity.this);
                                                        finish();
                                                    }else if("safe".equals(from)){
                                                        SafeSettingActivity.launch(Verify_PasswordActivity.this);
                                                        finish();
                                                    }else if("UserInformation".equals(from)){
                                                        EventBus.getDefault().post(new InformationEvent("ok"));
                                                        finish();
                                                    }else if("PicStatus".equals(from)){
                                                        EventBus.getDefault().post(new PicStatusEvent("update"));
                                                        finish();
                                                    }
                                                    else if("CardUpload".equals(from)){
                                                        EventBus.getDefault().post(new InformationEvent("CardUpload"));
                                                        finish();
                                                    }
                                                    else if("informationStatus".equals(from)) {
                                                        EventBus.getDefault().post(new InformationEvent("informationStatus"));
                                                        finish();
                                                    }else if("integarl".equals(from)){
                                                       IdentityinformationActivity.launch(Verify_PasswordActivity.this);
                                                        finish();
                                                    }else if("bankinformation".equals(from)){
                                                        EventBus.getDefault().post(new InformationEvent("bankinformation"));
                                                        finish();
                                                    }
                                                }

                                            }else {
                                                String msg = json.getString("msg");
                                                ToastUtils.showToast(Verify_PasswordActivity.this,msg);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                Verify_PasswordActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hud.dismiss();
                                    }
                                });
                            }
                        });


        }
    }


}
