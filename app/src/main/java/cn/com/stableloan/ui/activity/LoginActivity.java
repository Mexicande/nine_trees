package cn.com.stableloan.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.gyf.barlibrary.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.meituan.android.walle.WalleChannelReader;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.api.ApiService;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.bean.Login;
import cn.com.stableloan.common.Constants;
import cn.com.stableloan.interfaceutils.OnRequestDataListener;
import cn.com.stableloan.interfaceutils.VerListener;
import cn.com.stableloan.ui.fragment.dialogfragment.VerificationFragment;
import cn.com.stableloan.utils.ActivityStackManager;
import cn.com.stableloan.utils.AppUtils;
import cn.com.stableloan.utils.CaptchaTimeCount;
import cn.com.stableloan.utils.CommonUtil;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.RegexUtils;
import cn.com.stableloan.utils.SPUtil;
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
    @Bind(R.id.login_phone_r2)
    RelativeLayout loginPhoneR2;
    private Login_DeviceDialog dialog;
    private String phone;
    private CaptchaTimeCount captchaTimeCount;

    private int oldNew = 0;
    private  KProgressHUD hud;
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

    }

    private void initView() {
        slideview.addSlideListener(() -> {
            if (layoutCode.getVisibility() == View.VISIBLE) {
                String code = etCode.getText().toString();
                if (!TextUtils.isEmpty(code) && code.length() == 4) {
                    verCode(code);
                    slideview.reset();
                } else {
                    slideview.reset();
                    ToastUtils.showToast(AppApplication.getApp(), "验证码错误");
                }
            } else {
                EventBus.getDefault().post(new Login(1));
                setResult(100);
                finish();
            }
        });
         hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);

    }
    private void verPhone() {
        phone = etPhone.getText().toString();
        boolean b = CommonUtil.checkPhone(phone, true);
        if (b) {
            VerificationFragment verification = new VerificationFragment();
            verification.show(getSupportFragmentManager(), "ver");
        }
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
                    if(oldNew==1){
                        String token = date.getString("token");
                        String userphone = date.getString("userphone");
                        SPUtil.putString(LoginActivity.this, Urls.lock.TOKEN, token);
                        SPUtil.putString(LoginActivity.this, Urls.lock.USER_PHONE, userphone);
                        SPUtil.putString(LoginActivity.this,Constants.VIP,date.getString(Constants.VIP));
                        layoutCode.setVisibility(View.GONE);
                        slideview.setVisibility(View.VISIBLE);
                    }else {
                        layoutCode.setVisibility(View.VISIBLE);
                        getCode();
                    }



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
                        SPUtil.putString(LoginActivity.this,Constants.VIP,date.getString(Constants.VIP));
                        EventBus.getDefault().post(new Login(1));
                        setResult(100);
                        finish();

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


    @Override
    public void success() {
        isOldUser();
    }



    @OnClick({R.id.back, R.id.bt_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_code:
                verPhone();
                break;
            default:
                break;
        }
    }





    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                v.setFocusable(false);
                v.setFocusableInTouchMode(true);
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}