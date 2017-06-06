package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
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
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bt_getCode)
    Button btGetCode;
    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.et_CodeMessage)
    EditText etCodeMessage;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_Confirm_Password)
    EditText etConfirmPassword;
    @Bind(R.id.bt_save)
    Button btSave;

    private CodeMessage message;


    private int from;
    private CaptchaTimeCount captchaTimeCount;

    private String MessageCode = null;

    private String phone;

    public static void launch(Context context) {

        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        captchaTimeCount = new CaptchaTimeCount(Constants.Times.MILLIS_IN_TOTAL, Constants.Times.COUNT_DOWN_INTERVAL, btGetCode, this);
        initView();

        String url="http://47.93.197.52:8080/anwendai/Home/Api/Registered";

        HashMap<String, String> params = new HashMap<>();
        params.put("userphone", "18500634223");
        params.put("password", "ca017724dae8cfcdc2d97fa28671db61");

        JSONObject jsonObject = new JSONObject(params);
        OkGo.post(url).tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtils.i("-----------", s + "---" + response.toString());
                             /*   PlarformInfo info = gson.fromJson(s, PlarformInfo.class);
                                LogUtils.i("-----------",info.toString());*/
                    }
                });


    }

    private void initView() {
        from = getIntent().getIntExtra("from", 0);
        if (from == 0) {
            titleName.setText("找回密码");
            etConfirmPassword.setVisibility(View.GONE);
        } else {
            titleName.setText("注册");
        }
        ivBack.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.iv_back, R.id.bt_save, R.id.bt_getCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_save:
                if (from==1) {
                    fromRegister();
                } else {
                    fromForgetPassWord();
                }

                break;
            case R.id.bt_getCode:
                getCodeMessage();
                break;
        }
    }

    /**
     * 忘记密码
     */
    private void fromForgetPassWord() {
        String tel = etMessage.getText().toString();

        if (tel.equals(phone)) {
            if (message.getCheck() != null || !etCodeMessage.equals(message.getCheck())) {
                if (!RegexUtils.isPassWord(etPassword.getText().toString())) {
                    //忘记密码

                } else {
                    ToastUtils.showToast(this, "密码格式不正确");
                }

            } else {
                ToastUtils.showToast(this, "验证码错误");

            }

        } else {
            ToastUtils.showToast(this, "手机号不正确");
        }


    }

    /**
     * 注册
     */
    private void fromRegister() {
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String tel = etMessage.getText().toString();
        if (tel.equals(phone)) {
            if (message.getCheck() != null || !etCodeMessage.equals(message.getCheck())) {
                if (!RegexUtils.isPassWord(password)||!RegexUtils.isPassWord(confirmPassword)){
                    if(!password.isEmpty()&&!confirmPassword.isEmpty()&&password.equals(confirmPassword)){
                        String md5ToString = EncryptUtils.encryptMD5ToString(confirmPassword);
                        LogUtils.i("MD5",md5ToString);
                        OkGo.post(Urls.register.REGSTER)
                                .tag(this)
                                .params("userphone",phone)
                                .params("password",md5ToString)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        LogUtils.i("注册",s);
                                        try {
                                            JSONObject object=new JSONObject(s);
                                            if("true".equals(object.getString("isSuccess"))){
                                                SPUtils.put(RegisterActivity.this,"token",object.getString("token"));
                                                finish();

                                            }else {
                                                ToastUtils.showToast(RegisterActivity.this,"系统异常,请稍后再试");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                    }else {
                        ToastUtils.showToast(this,"两次密码不一致");
                    }
                    //忘记密码
                } else {
                    ToastUtils.showToast(this, "密码格式不正确");
                }
            } else {
                ToastUtils.showToast(this, "验证码错误");

            }

        } else {
            ToastUtils.showToast(this, "手机号不正确");
        }



    }

    private void getCodeMessage() {
        phone = etMessage.getText().toString();
        captchaTimeCount.start();
        OkGo.post(Urls.Login.SEND_MESSAGE)
                .tag(this)
                .params("userPhone", etMessage.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtils.i("login-code", s);
                        Gson gson = new Gson();
                        message = gson.fromJson(s, CodeMessage.class);
                        LogUtils.i(message.toString());
                        if (message.getStatus() == 1) {
                            ToastUtils.showToast(RegisterActivity.this, message.getMsg());
                        } else {
                            ToastUtils.showToast(RegisterActivity.this, message.getMsg());
                        }
                    }
                });


    }

    @OnClick(R.id.bt_save)
    public void onViewClicked() {
    }
}
