package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.bean.CodeMessage;
import cn.com.stableloan.utils.CaptchaTimeCount;
import cn.com.stableloan.utils.Constants;
import cn.com.stableloan.utils.EncryptUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.RegexUtils;
import cn.com.stableloan.utils.ToastUtils;
import cxy.com.validate.IValidateResult;
import cxy.com.validate.Validate;
import cxy.com.validate.ValidateAnimation;
import cxy.com.validate.annotation.Index;
import cxy.com.validate.annotation.NotNull;
import cxy.com.validate.annotation.Password1;
import cxy.com.validate.annotation.RE;
import okhttp3.Call;
import okhttp3.Response;

public class ForgetWordActivity extends BaseActivity implements IValidateResult {


    @Index(1)
    @NotNull(msg = "手机号不能为空！")
    @RE(re = RE.phone, msg = "手机号格式不正确")
    @Bind(R.id.et_phone)
    EditText etMessage;

    @Index(2)
    @NotNull(msg = "验证码不能为空！")
    @Bind(R.id.et_CodeMessage)
    EditText etCodeMessage;

    @Index(3)
    @NotNull(msg = "密码不为能空！")
    @RE(re = RE.number_letter_underline, msg = "密码格式不正确")
    @Password1()
    @Bind(R.id.et_password)
    EditText etPassword;


    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bt_getCode)
    Button btGetCode;
    @Bind(R.id.et_Confirm_Password)
    EditText etConfirmPassword;

    private CodeMessage message;


    private CaptchaTimeCount captchaTimeCount;

    private String MessageCode = null;

    private String phone;

    public static void launch(Context context) {

        context.startActivity(new Intent(context, ForgetWordActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Validate.reg(this);
        captchaTimeCount = new CaptchaTimeCount(Constants.Times.MILLIS_IN_TOTAL, Constants.Times.COUNT_DOWN_INTERVAL, btGetCode, this);
        initToolbar();
    }

    private void initToolbar() {
        etConfirmPassword.setVisibility(View.GONE);
        titleName.setText("忘记密码");
        ivBack.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.iv_back, R.id.bt_getCode, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_save:
                Validate.check(ForgetWordActivity.this, ForgetWordActivity.this);
                break;
            case R.id.bt_getCode:
                getCodeMessage();
                break;
            default:
                break;
        }
    }

    /**
     * 验证码获取
     */
    private void getCodeMessage() {
        phone = etMessage.getText().toString();

        if (RegexUtils.isMobileExact(phone)) {
            captchaTimeCount.start();

            HashMap<String, String> params = new HashMap<>();
            params.put("userPhone", phone);
            params.put("status", "1");

            JSONObject jsonObject = new JSONObject(params);
            OkGo.post(Urls.Login.SEND_MESSAGE)
                    .tag(this)
                    .upJson(jsonObject.toString())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String status = jsonObject.getString("status");
                                if (status.equals("1")) {
                                    MessageCode = jsonObject.getString("check");
                                    ToastUtils.showToast(ForgetWordActivity.this, jsonObject.getString("msg"));
                                } else {
                                    ToastUtils.showToast(ForgetWordActivity.this, jsonObject.getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } else {
            ToastUtils.showToast(this, "请输入正确的手机号");
        }

    }

    /**
     * 修改密码请求
     */

    private KProgressHUD hud;

    private void setPassWord() {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(true)
                .show();
        String confirmPassword = etPassword.getText().toString();
        String tel = etMessage.getText().toString();
        String code = etCodeMessage.getText().toString();
        if (tel.equals(phone) && MessageCode != null && code.equals(MessageCode)) {
            String md5ToString = EncryptUtils.encryptMD5ToString(confirmPassword);
            HashMap<String, String> params = new HashMap<>();
            params.put("userphone", phone);
            params.put("password", md5ToString);
            JSONObject jsonObject = new JSONObject(params);
            OkGo.post(Urls.puk_URL + Urls.register.FORGETWORD)
                    .tag(this)
                    .upJson(jsonObject.toString())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject object = new JSONObject(s);

                                String success = object.getString("isSuccess");
                                if(success.equals("1")){
                                    hud.dismiss();
                                    String string = object.getString("msg");

                                    ToastUtils.showToast(ForgetWordActivity.this, string);
                                    finish();
                                } else {
                                    String string = object.getString("msg");
                                    hud.dismiss();

                                    ToastUtils.showToast(ForgetWordActivity.this, string);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

        } else {
            ToastUtils.showToast(this, "手机号或验证码不正确");
        }
        hud.dismiss();

    }

    @Override
    public void onValidateSuccess() {
        setPassWord();
    }

    @Override
    public void onValidateError(String s, EditText editText) {
        if (editText != null)
            editText.setFocusable(true);
        ToastUtils.showToast(this, s);
    }

    @Override
    public Animation onValidateErrorAnno() {
        return ValidateAnimation.horizontalTranslate();

    }
}
