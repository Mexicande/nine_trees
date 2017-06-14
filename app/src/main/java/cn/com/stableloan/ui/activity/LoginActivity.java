package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
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
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.utils.CaptchaTimeCount;
import cn.com.stableloan.utils.Constants;
import cn.com.stableloan.utils.EncryptUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.RegexUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cxy.com.validate.IValidateResult;
import cxy.com.validate.Validate;
import cxy.com.validate.ValidateAnimation;
import cxy.com.validate.annotation.Index;
import cxy.com.validate.annotation.NotNull;
import cxy.com.validate.annotation.RE;
import ezy.boost.update.UpdateUtil;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 登录注册页
 */

public class LoginActivity extends BaseActivity implements IValidateResult {
    /* @Bind(R.id.nts)
     NavigationTabStrip nts;*/

    @Bind(R.id.nts)
    TabLayout nts;

    @Index(1)
    @NotNull(msg = "手机号不能为空！")
    @RE(re = RE.phone, msg = "手机号格式不正确")
    @Bind(R.id.et_phone)
    EditText etPhone;


    @Index(2)
    @NotNull(msg = "不能为空！")
    @Bind(R.id.et_lock)
    EditText etLock;

    @Bind(R.id.bt_getCode)
    Button btGetCode;
    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.register_button)
    Button registerButton;
    @Bind(R.id.tv_forget)
    TextView tvForget;
    @Bind(R.id.iv_phone)
    ImageView ivPhone;
    @Bind(R.id.iv_lock)
    ImageView ivLock;

    private String phone;

    private String code;

    private boolean Flag = false;
    private final int Flag_User = 3000;

    private CaptchaTimeCount captchaTimeCount;

    private String MessageCode = null;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    private CodeMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        etLock.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        Validate.reg(this);
        captchaTimeCount = new CaptchaTimeCount(Constants.Times.MILLIS_IN_TOTAL, Constants.Times.COUNT_DOWN_INTERVAL, btGetCode, this);

    }

    private void initView() {

        etLock.addTextChangedListener(watcher);
        etPhone.addTextChangedListener(watcher1);

        nts.addTab(nts.newTab().setText("密码登陆"));
        nts.addTab(nts.newTab().setText("短信登陆"));
        nts.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getSwitch(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
       /* nts.setTitles("短信登陆","密码登陆");
        nts.setTabIndex(0, true);*/

    }

    private void getSwitch(int position) {
        switch (position) {
            case 0:
                etLock.setTransformationMethod(PasswordTransformationMethod
                        .getInstance());
                ivPhone.setImageResource(R.mipmap.ic_phone);
                ivLock.setImageResource(R.mipmap.ic_lock);
                Flag = false;
                etLock.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                etPhone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                etLock.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                btGetCode.setVisibility(View.GONE);
                tvForget.setVisibility(View.VISIBLE);
                etPhone.getText().clear();
                etLock.getText().clear();
                etPhone.setHint("手机号");
                etLock.setHint("密码");
                break;
            case 1:
                etLock.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivPhone.setImageResource(R.mipmap.iv_messgephone);
                ivLock.setImageResource(R.mipmap.iv_messagelock);
                Flag = true;
                etPhone.setHint("请输入手机号码");
                etLock.setHint("验证码");
                etPhone.getText().clear();
                etLock.getText().clear();
                etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});

                etLock.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

                etLock.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                etPhone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                btGetCode.setVisibility(View.VISIBLE);
                tvForget.setVisibility(View.GONE);
                break;

        }

    }

    /**
     * 密码
     */
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            if (!etPhone.getText().toString().isEmpty()) {
                loginButton.setEnabled(true);
                loginButton.setBackgroundResource(R.drawable.corner_btn);
                loginButton.setTextColor(getResources().getColor(R.color.white));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!etPhone.getText().toString().isEmpty() && !etLock.getText().toString().isEmpty()) {
                loginButton.setEnabled(true);
            } else {
                loginButton.setEnabled(false);
                loginButton.setBackgroundResource(R.drawable.login_button_start);

            }
        }
    };
    /**
     * 手机号
     */
    private TextWatcher watcher1 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            if (!etLock.getText().toString().isEmpty()) {
                loginButton.setEnabled(true);
                loginButton.setBackgroundResource(R.drawable.login_button_up);

            } else {

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!etPhone.getText().toString().isEmpty() && !etLock.getText().toString().isEmpty()) {
                loginButton.setEnabled(true);
                loginButton.setBackgroundResource(R.drawable.login_button_up);
                loginButton.setTextColor(getResources().getColor(R.color.white));
            } else {
                loginButton.setEnabled(false);
                loginButton.setBackgroundResource(R.drawable.login_button_start);
            }

        }
    };


    @OnClick({R.id.bt_getCode, R.id.login_button, R.id.register_button, R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_getCode:
                phone = etPhone.getText().toString();
                if (etPhone.getText().toString().isEmpty()) {
                    ToastUtils.showToast(this, "请填写手机号码");
                } else if (!RegexUtils.isMobileExact(etPhone.getText().toString())) {
                    ToastUtils.showToast(this, "手机号格式");
                } else {
                    getCodeMessage();
                }
                break;
            case R.id.login_button:
                Validate.check(LoginActivity.this, LoginActivity.this);
                break;
            case R.id.tv_forget:
                ForgetWordActivity.launch(this);
                break;
            case R.id.register_button:
                UpdateUtil.clean(this);
                RegisterActivity.launch(this);
                break;
        }
    }

    /**
     * 登陆
     */
    private void setLogin() {

        // 验证码登陆
        if (Flag) {
            HashMap<String, String> params = new HashMap<>();
            params.put("userphone", etPhone.getText().toString());
            params.put("status", "3");
            JSONObject jsonObject = new JSONObject(params);
            String tel = etPhone.getText().toString();
            String cd = etLock.getText().toString();
            if (phone.equals(tel)) {
                if (MessageCode != null) {
                    if (!cd.isEmpty() && cd.equals(MessageCode)) {
                        Login(jsonObject.toString());
                    } else {
                        ToastUtils.showToast(this, "手机号或验证码错误");
                    }
                } else {
                    ToastUtils.showToast(this, "手机号或验证码错误");
                }
            } else {
                ToastUtils.showToast(this, "手机号或验证码错误");
            }
        } else {
            //账号密码登陆
            //隐藏密码

            String pass = etLock.getText().toString();
            if (!pass.isEmpty()) {
                String md5ToString = EncryptUtils.encryptMD5ToString(pass);
                HashMap<String, String> params = new HashMap<>();
                params.put("userphone", etPhone.getText().toString());
                params.put("password", md5ToString);
                params.put("status", "1");
                JSONObject jsonObject = new JSONObject(params);
                Login(jsonObject.toString());
            }

        }

    }

    private KProgressHUD hud;


    private void Login(String json) {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(true)
                .show();
        OkGo.post(Urls.puk_URL + Urls.Login.LOGIN)
                .tag(this)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            try {
                                JSONObject object = new JSONObject(s);
                                String success = object.getString("isSuccess");
                                if (success.equals("1")) {
                                    SPUtils.put(LoginActivity.this, "token", object.getString("token"));
                                    SPUtils.put(LoginActivity.this, "login", true);
                                    getUserInfo();
                                } else {
                                    String string = object.getString("msg");
                                    ToastUtils.showToast(LoginActivity.this, string);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showToast(LoginActivity.this, "服务器异常,请稍后再试");

                        }
                        hud.dismiss();

                    }
                });
    }

    private void getUserInfo() {
        String token = (String) SPUtils.get(this, "token", "1");
        if (!token.isEmpty()) {
            HashMap<String, String> params = new HashMap<>();
            params.put("token", token);
            JSONObject jsonObject = new JSONObject(params);
            OkGo.post(Urls.puk_URL + Urls.user.USERT_INFO)
                    .tag(this)
                    .upJson(jsonObject.toString())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject object = new JSONObject(s);
                                String success = object.getString("isSuccess");
                                if (success.equals("1")) {
                                    Gson gson = new Gson();
                                    UserBean bean = gson.fromJson(s, UserBean.class);
                                    TinyDB tinyDB = new TinyDB(LoginActivity.this);
                                    tinyDB.putObject("user", bean);
                                    String from = getIntent().getStringExtra("from");
                                    if (from != null && from.equals("user")) {
                                        setResult(Flag_User, new Intent().putExtra("user", bean));

                                        finish();
                                    } else {
                                        finish();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

    }

    /**
     * 获取验证码
     */
    private void getCodeMessage() {
        captchaTimeCount.start();

        HashMap<String, String> params = new HashMap<>();
        params.put("userPhone", etPhone.getText().toString());
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
                                ToastUtils.showToast(LoginActivity.this, jsonObject.getString("msg"));
                            } else {
                                ToastUtils.showToast(LoginActivity.this, jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //返回事件

        String from = getIntent().getStringExtra("from");
        if (from != null && from.equals("user")) {
            //MainActivity.launch(this);
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        } else {
            finish();
        }
    /*    UserBean user = (UserBean) SPUtils.get(this, "user", UserBean.class);
            if(user!=null){
                finish();
            }else {
                MainActivity.launch(this);
                finish();
            }*/
    }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onValidateSuccess() {
        setLogin();
    }


    @Override
    public void onValidateError(String msg, EditText editText) {
        if (editText != null)
            editText.setFocusable(true);
        ToastUtils.showToast(this, msg);
    }

    @Override
    public Animation onValidateErrorAnno() {
        return ValidateAnimation.horizontalTranslate();
    }
}
