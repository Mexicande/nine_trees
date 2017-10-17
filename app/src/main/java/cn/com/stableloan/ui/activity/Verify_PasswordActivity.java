package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.luck.picture.lib.tools.ScreenUtils;
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
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.PicStatusEvent;
import cn.com.stableloan.utils.EncryptUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.RoundButton;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

public class Verify_PasswordActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.layout_go)
    LinearLayout layoutGo;
    @Bind(R.id.et_PassWord)
    FormEditText etPassWord;
    @Bind(R.id.tv_fail)
    TextView tvFail;
    @Bind(R.id.tv_forgetPW)
    TextView tvForgetPW;
    @Bind(R.id.bt_Validation)
    RoundButton btValidation;
    @Bind(R.id.main)
    LinearLayout main;
    private int FILD_NU = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__password);
        ButterKnife.bind(this);
        initToolbar();
        setListener();

    }
    private int lastYPos = 0;

    private void setListener() {

/*        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //1.获取main在窗体的可视区域
                main.getWindowVisibleDisplayFrame(rect);
                //2.获取main在窗体的不可见视区域高度，在键盘没有弹起时
                //main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int hight = main.getRootView().getHeight() - rect.bottom;

                //3.不可见区域大于100；说明键盘弹出来了，
                if (hight > 100) {
                    int[] ints = new int[2];

                    //4.获取etPassWord的窗体坐标，算出main需要滚动的高度
                    btValidation.getLocationInWindow(ints);

                    int scrollHeight = (ints[1]
                            + btValidation.getHeight() + ScreenUtils
                            .dip2px(Verify_PasswordActivity.this, 2)) - rect.bottom;

                    scrollHeight += lastYPos;


                    // int i = (ints[1] + btValidation.getHeight()+) - rect.bottom;
                    // 5.让界面整体上移键盘的高度
                    main.scrollTo(0, scrollHeight);

                    lastYPos = scrollHeight;

                } else {
                    //6.不可见区域小于100，说明键盘隐藏了，把界面下移，移动到原有高度
                    main.scrollTo(0, 0);
                    lastYPos = 0;

                }
            }
        });*/


    }

    private void initToolbar() {
        titleName.setText("身份验证");
        etPassWord.setTransformationMethod(PasswordTransformationMethod
                .getInstance());

    }


    @OnClick({R.id.layout_go, R.id.bt_Validation, R.id.tv_forgetPW})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_go:
                finish();
                break;
            case R.id.bt_Validation:
                VerifyPassWord();
                break;
            case R.id.tv_forgetPW:
                startActivity(new Intent(Verify_PasswordActivity.this, ForgetWordActivity.class).putExtra("from", "Verify_PasswordActivity"));
                break;
        }
    }

    private KProgressHUD hud;

    private void VerifyPassWord() {
        String password = etPassWord.getText().toString();
        String token = (String) SPUtils.get(this, "token", "1");
        if (!password.isEmpty()) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait.....")
                    .setCancellable(true)
                    .show();
            String md5ToString = EncryptUtils.encryptMD5ToString(password);
            Map<String, String> parms = new HashMap<>();
            parms.put("token", token);
            parms.put("password", md5ToString);
            JSONObject jsonObject = new JSONObject(parms);
            OkGo.<String>post(Urls.NEW_URL + Urls.Login.USER_INFOMATION)
                    .tag(this)
                    .upJson(jsonObject)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            hud.dismiss();
                            if (s != null) {
                                try {
                                    JSONObject json = new JSONObject(s);
                                    String isSuccess = json.getString("isSuccess");
                                    if ("1".equals(isSuccess)) {
                                        String from = getIntent().getStringExtra("from");
                                        if (from != null) {
                                            String signature = json.getString("signature");
                                            SPUtils.put(Verify_PasswordActivity.this, "signature", signature);
                                            if ("unLock".equals(from)) {
                                                CreateGestureActivity.launch(Verify_PasswordActivity.this);
                                                finish();
                                            } else if ("userinformation".equals(from)) {
                                                UserInformationActivity.launch(Verify_PasswordActivity.this);
                                                finish();
                                            } else if ("safe".equals(from)) {
                                                SafeActivity.launch(Verify_PasswordActivity.this);
                                                finish();
                                            } else if ("UserInformation".equals(from)) {
                                                EventBus.getDefault().post(new InformationEvent("ok"));
                                                finish();
                                            } else if ("PicStatus".equals(from)) {
                                                EventBus.getDefault().post(new PicStatusEvent("update"));
                                                finish();
                                            } else if ("CardUpload".equals(from)) {
                                                EventBus.getDefault().post(new InformationEvent("CardUpload"));
                                                finish();
                                            } else if ("informationStatus".equals(from)) {
                                                EventBus.getDefault().post(new InformationEvent("informationStatus"));
                                                finish();
                                            } else if ("integarl".equals(from)) {
                                                IdentityinformationActivity.launch(Verify_PasswordActivity.this);
                                                finish();
                                            } else if ("bankinformation".equals(from)) {
                                                EventBus.getDefault().post(new InformationEvent("bankinformation"));
                                                finish();
                                            } else if ("updatePassword".equals(from)) {
                                                startActivity(new Intent(Verify_PasswordActivity.this, UpdatePassWordActivity.class).putExtra("password", md5ToString));
                                                finish();
                                            } else if("CreateGesture".equals(from)){
                                                CreateGestureActivity.launch(Verify_PasswordActivity.this);
                                                finish();
                                            }
                                        }

                                    } else {
                                        FILD_NU++;
                                        tvFail.setText(FILD_NU + "次密码输入错误");
                                        String msg = json.getString("msg");
                                        ToastUtils.showToast(Verify_PasswordActivity.this, msg);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
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
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
