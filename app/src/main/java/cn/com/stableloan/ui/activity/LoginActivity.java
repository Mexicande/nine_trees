package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.api.ApiService;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.common.Constants;
import cn.com.stableloan.interfaceutils.OnRequestDataListener;
import cn.com.stableloan.interfaceutils.VerListener;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.fragment.dialogfragment.VerificationFragment;
import cn.com.stableloan.utils.ActivityStackManager;
import cn.com.stableloan.utils.CaptchaTimeCount;
import cn.com.stableloan.utils.CommonUtil;
import cn.com.stableloan.utils.StatusBarUtil;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.SlideView;
import cn.com.stableloan.view.dialog.Login_DeviceDialog;
import cn.com.stableloan.view.supertextview.SuperButton;

/**
 * @author apple
 *         登陆
 */
public class LoginActivity extends AppCompatActivity implements VerListener {


    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.bt_code)
    Button btCode;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.layout_code)
    RelativeLayout layoutCode;
    @Bind(R.id.slideview)
    SlideView slideview;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.tv_card)
    TextView tvCard;
    @Bind(R.id.et_card)
    EditText etCard;
    @Bind(R.id.bt_login)
    SuperButton btLogin;
    @Bind(R.id.layout_name)
    LinearLayout layoutName;
    @Bind(R.id.login_phone_r2)
    RelativeLayout loginPhoneR2;
    private Login_DeviceDialog dialog;
    private String phone;
    private final int Flag_User = 3000;
    private final int LOTTERY_CODE = 500;
    private CaptchaTimeCount captchaTimeCount;

    private int oldNew = 0;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
        captchaTimeCount = new CaptchaTimeCount(Constants.MILLIS_IN_TOTAL, Constants.COUNT_DOWN_INTERVAL, btCode, this);
        initView();

    }

    private void initView() {
        String code = etCode.getText().toString();
        slideview.addSlideListener(() -> {
            if (layoutCode.getVisibility() == View.VISIBLE) {
                if (!TextUtils.isEmpty(code) && code.length() == 4) {
                    verCode(code);
                } else {
                    slideview.reset();
                    ToastUtils.showToast(AppApplication.getApp(), "验证码错误");
                }
            } else {
                loginPhoneR2.setVisibility(View.GONE);
                layoutName.setVisibility(View.VISIBLE);
                slideview.setVisibility(View.GONE);
                slideview.reset();
            }
        });

    }

    /**
     * 验证码效验
     */
    private void verCode(String code) {

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
                try {
                    JSONObject date = data.getJSONObject("data");
                    String msg = date.getString("msg");
                    String isSucess = date.getString("isSucess");
                    if ("1".equals(isSucess)) {
                        layoutName.setVisibility(View.VISIBLE);
                        slideview.setVisibility(View.GONE);
                        loginPhoneR2.setVisibility(View.GONE);
                    } else {
                        slideview.reset();
                    }
                    ToastUtils.showToast(AppApplication.getApp(), msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                slideview.reset();
                ToastUtils.showToast(AppApplication.getApp(), msg);
            }
        });

    }

    private void initViewDialog(int title, int desc) {
        dialog = new Login_DeviceDialog(this);
        dialog.setTitle(getResources().getString(title));
        dialog.setMessage(getResources().getString(desc));
        dialog.setYesOnclickListener("知道了", () -> dialog.dismiss());

        dialog.show();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String message = getIntent().getStringExtra("message");
            String from = getIntent().getStringExtra("from");
            if (from != null) {
                if (("user").equals(from)) {
                    UserBean userBean = new UserBean();
                    setResult(Flag_User, new Intent().putExtra("user", userBean));
                    finish();
                } else if (("123").equals(from)) {
                    setResult(LOTTERY_CODE, new Intent().putExtra("Loffery", "1"));
                    finish();
                } else if (("user1").equals(from)) {
                    UserBean userBean = new UserBean();
                    setResult(4000, new Intent().putExtra("user", userBean));
                    finish();
                } else if (("user2").equals(from)) {
                    EventBus.getDefault().post(new InformationEvent("user2"));
                    finish();
                } else {
                    if (message != null) {
                        ActivityStackManager.getInstance().popAllActivity();
                        MainActivity.launch(this);
                        finish();
                    } else {
                        finish();
                    }
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void success() {
        isOldUser();
    }

    /**
     * isOldUser
     * 新老用户
     */
    private void isOldUser() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userphone", phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiService.GET_SERVICE(Urls.Login.IS_OLD_USER, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject date = data.getJSONObject("data");
                    oldNew = date.getInt("isolduser");
                    fillData(oldNew);
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
     * 新老用户
     *
     * @param isolduser 1：老用户 0：新用户
     */
    private void fillData(int isolduser) {
        if (isolduser == 1) {
            if (layoutCode.getVisibility() == View.VISIBLE) {
                layoutCode.setVisibility(View.GONE);
            }
            slideview.setVisibility(View.VISIBLE);
        } else {
            layoutCode.setVisibility(View.VISIBLE);
            layoutName.setVisibility(View.GONE);
            getCode();
        }

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
                    String isSucess = date.getString("isSucess");
                    if ("1".equals(isSucess)) {
                        slideview.setVisibility(View.VISIBLE);
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


    @OnClick({R.id.back, R.id.bt_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                String from = getIntent().getStringExtra("from");
                String message = getIntent().getStringExtra("message");
                if (from != null) {
                    if (("user").equals(from)) {
                        UserBean userBean = new UserBean();
                        setResult(Flag_User, new Intent().putExtra("user", userBean));
                        finish();
                    } else if (("123").equals(from)) {
                        setResult(LOTTERY_CODE, new Intent().putExtra("Loffery", "1"));
                        finish();
                    } else if (("user1").equals(from)) {
                        UserBean userBean = new UserBean();
                        setResult(4000, new Intent().putExtra("user", userBean));
                        finish();
                    } else if (("user2").equals(from)) {
                        EventBus.getDefault().post(new InformationEvent("user2"));
                        finish();
                    } else {
                        if (message != null) {
                            ActivityStackManager.getInstance().popAllActivity();
                            MainActivity.launch(this);
                            finish();
                        } else {
                            finish();
                        }
                    }
                } else {
                    finish();
                }
                break;
            case R.id.bt_code:
                verPhone();
                break;
            default:
                break;
        }
    }

    private void verPhone() {
        phone = etPhone.getText().toString();
        boolean b = CommonUtil.checkPhone(phone, true);
        if (b) {
            VerificationFragment verification = new VerificationFragment();
            verification.show(getSupportFragmentManager(), "ver");
        }
    }

    @OnClick(R.id.bt_login)
    public void onViewClicked() {
    }
}