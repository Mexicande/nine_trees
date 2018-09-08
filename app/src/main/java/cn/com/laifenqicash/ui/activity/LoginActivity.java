package cn.com.laifenqicash.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.laifenqicash.AppApplication;
import cn.com.laifenqicash.R;
import cn.com.laifenqicash.api.ApiService;
import cn.com.laifenqicash.api.Urls;
import cn.com.laifenqicash.base.BaseActivity;
import cn.com.laifenqicash.bean.Login;
import cn.com.laifenqicash.common.Constants;
import cn.com.laifenqicash.interfaceutils.OnRequestDataListener;
import cn.com.laifenqicash.ui.fragment.dialogfragment.VerificationFragment;
import cn.com.laifenqicash.utils.ActivityStackManager;
import cn.com.laifenqicash.utils.CaptchaTimeCount;
import cn.com.laifenqicash.utils.CodeUtils;
import cn.com.laifenqicash.utils.CommonUtil;
import cn.com.laifenqicash.utils.SPUtil;
import cn.com.laifenqicash.utils.ToastUtils;
import cn.com.laifenqicash.utils.editext.PowerfulEditText;
import cn.com.laifenqicash.view.dialog.Login_DeviceDialog;
import cn.com.laifenqicash.view.supertextview.SuperButton;

/**
 * @author apple
 * 登陆
 */
public class LoginActivity extends BaseActivity {


    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.verify_iv)
    ImageView verifyIv;
    @Bind(R.id.ed_phone)
    PowerfulEditText edPhone;
    @Bind(R.id.result)
    ImageView result;
    @Bind(R.id.ed_code)
    PowerfulEditText edCode;
    @Bind(R.id.bt_code)
    Button btCode;
    @Bind(R.id.layout_code)
    RelativeLayout layoutCode;
    @Bind(R.id.bt_login)
    SuperButton btLogin;
    @Bind(R.id.et_Result)
    EditText etResult;
    @Bind(R.id.layout_Result)
    RelativeLayout layoutResult;

    private Login_DeviceDialog dialog;
    private String phone;
    private CaptchaTimeCount captchaTimeCount;
    private CodeUtils codeUtils;
    private String yanZhengResult;
    private String etYanZhengCode;
    private String yanZhengCode;
    private int oldNew = 1;
    private KProgressHUD hud;
    private ImmersionBar mImmersionBar;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.white)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();
        captchaTimeCount = new CaptchaTimeCount(Constants.MILLIS_IN_TOTAL, Constants.COUNT_DOWN_INTERVAL, btCode, this);
        initView();
        setListener();
        initYanzheng();

    }

    private void setListener() {
        edPhone.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if (!etResult.getText().toString().isEmpty()&& s.toString().length() == 11) {
                    btLogin.setEnabled(true);
                    btLogin.setUseShape();
                } else {
                    btLogin.setEnabled(false);
                    btLogin.setUseShape();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edPhone.getText().toString().length() == 11 && !s.toString().isEmpty() ) {
                    btLogin.setEnabled(true);
                    btLogin.setUseShape();
                } else {
                    btLogin.setEnabled(false);
                    btLogin.setUseShape();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edCode.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if (edPhone.getText().toString().length() == 11 && s.toString().length()==4 ) {
                    btLogin.setEnabled(true);
                    btLogin.setUseShape();
                } else {
                    btLogin.setEnabled(false);
                    btLogin.setUseShape();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initView() {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);

    }

    /**
     * isOldUser
     * 新老用户
     */
    private void isOldUser() {
        hud.show();
        phone = edPhone.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userphone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiService.GET_SERVICE(Urls.Login.IS_OLD_USER, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                hud.dismiss();
                try {
                    JSONObject date = data.getJSONObject("data");
                    oldNew = date.getInt("isolduser");
                    if (oldNew == 1) {
                        String token = date.getString("token");
                        String userphone = date.getString("userphone");
                        SPUtil.putString(LoginActivity.this, Urls.lock.TOKEN, token);
                        SPUtil.putString(LoginActivity.this, Urls.lock.USER_PHONE, userphone);
                        SPUtil.putString(LoginActivity.this, Constants.VIP, date.getString(Constants.VIP));
                        layoutCode.setVisibility(View.GONE);
                        EventBus.getDefault().post(new Login(1));
                        setResult(100);
                        finish();
                    } else {
                        layoutResult.setVisibility(View.GONE);
                        layoutCode.setVisibility(View.VISIBLE);
                        getCode();
                        btLogin.setEnabled(false);
                        btLogin.setUseShape();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                hud.dismiss();
                ToastUtils.showToast(AppApplication.getApp(), msg);
            }
        });
    }


    /**
     * 验证码获取
     */
    private void getCode() {
        captchaTimeCount.start();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userphone", phone);
            jsonObject.put("terminal", Constants.terminal);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiService.GET_SERVICE(Urls.Login.GET_CODE, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject date = data.getJSONObject("data");
                    String msg = date.getString("msg");
                    String isSucess = date.getString("isSuccess");
                    if ("1".equals(isSucess)) {
                        //  slideview.setVisibility(View.VISIBLE);
                    }
                    ToastUtils.showToast(AppApplication.getApp(), msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(AppApplication.getApp(), msg);
            }
        });

    }

    /**
     * 验证码效验
     */
    private void verCode(String code) {

        hud.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userphone", phone);
            jsonObject.put("code", code);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiService.GET_SERVICE(Urls.Login.CHECK_CODE, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                hud.dismiss();

                try {
                    JSONObject date = data.getJSONObject("data");
                    String msg = date.getString("msg");
                    String isSucess = date.getString("isSuccess");
                    if ("1".equals(isSucess)) {
                        String token = date.getString("token");
                        String userphone = date.getString("userphone");
                        SPUtil.putString(LoginActivity.this, Urls.lock.TOKEN, token);
                        SPUtil.putString(LoginActivity.this, Urls.lock.USER_PHONE, userphone);
                        SPUtil.putString(LoginActivity.this, Constants.VIP, date.getString(Constants.VIP));
                        EventBus.getDefault().post(new Login(1));
                        setResult(100);
                        finish();
                    }
                    ToastUtils.showToast(AppApplication.getApp(), msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                hud.dismiss();

                ToastUtils.showToast(AppApplication.getApp(), msg);
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String user = getIntent().getStringExtra("user");
            if (!TextUtils.isEmpty(user)) {
                ActivityStackManager.getInstance().popAllActivity();
                MainActivity.launch(this);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initYanzheng() {
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        verifyIv.setImageBitmap(bitmap);
        yanZhengCode = codeUtils.getCode();
        yanZhengResult = codeUtils.getResult() + "";
    }

    @OnClick({R.id.back, R.id.verify_iv, R.id.bt_code, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                String user = getIntent().getStringExtra("user");
                if (!TextUtils.isEmpty(user)) {
                    ActivityStackManager.getInstance().popAllActivity();
                    MainActivity.launch(this);
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.verify_iv:
                initYanzheng();
                break;
            case R.id.bt_code:
                getCode();
                break;
            case R.id.bt_login:
                if (oldNew == 1) {
                    etYanZhengCode = etResult.getText().toString().trim();
                    if (TextUtils.isEmpty(etYanZhengCode)) {
                        ToastUtils.showToast(this, "请输入图片里的结果");
                        return;
                    }
                    if (!yanZhengResult.equals(etYanZhengCode)) {
                        ToastUtils.showToast(this, "图片结果输入有误");
                        etResult.getText().clear();
                        initYanzheng();
                    } else {
                        isOldUser();
                    }
                } else {
                    String code = edCode.getText().toString();
                    verCode(code);
                }

                break;
            default:
                break;
        }
    }
}