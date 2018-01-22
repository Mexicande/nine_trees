package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.meituan.android.walle.WalleChannelReader;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.DesBean;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.MessageEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.model.UserInfromBean;
import cn.com.stableloan.ui.fragment.ThreeElementsFragment;
import cn.com.stableloan.utils.ActivityStackManager;
import cn.com.stableloan.utils.EncryptUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.RegexUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.aes.Des4;
import cn.com.stableloan.utils.editext.PowerfulEditText;
import cn.com.stableloan.utils.ras.RSA;
import cn.com.stableloan.view.dialog.SettingPassWordDialog;
import cn.com.stableloan.view.supertextview.SuperButton;
import okhttp3.Call;
import okhttp3.Response;

public class SettingPassWordActivity extends AppCompatActivity {

    @Bind(R.id.layout)
    RelativeLayout layout;
    @Bind(R.id.et_SettingPassWord)
    PowerfulEditText etSettingPassWord;
    @Bind(R.id.bt_login)
    SuperButton btLogin;
    @Bind(R.id.checkbox)
    AppCompatCheckBox checkbox;
    @Bind(R.id.layoutCheck)
    LinearLayout layoutCheck;
    private String userPhone;
    private SettingPassWordDialog selfDialog;



    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingPassWordActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pass_word);
        ImmersionBar.with(this).statusBarColor(R.color.frame_color)
                .statusBarAlpha(0.8f)
                .fitsSystemWindows(true)
                .init();
        ButterKnife.bind(this);

        setListener();


    }

    private void setListener() {

        userPhone = getIntent().getStringExtra("userPhone");
        int insure = getIntent().getIntExtra("is_insure", 0);

        if (insure == 1) {
            layoutCheck.setVisibility(View.VISIBLE);
        }
        etSettingPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSettingPassWord.getText().toString().length() > 5) {
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
    }

    private KProgressHUD hud;

    @OnClick({R.id.layout, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout:
                exit();
                break;
            case R.id.bt_login:
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(getResources().getString(R.string.wait))
                        .setCancellable(true)
                        .show();
                String strPassWord = etSettingPassWord.getText().toString();
                boolean match = RegexUtils.isMatch(RegexUtils.number_letter_underline, strPassWord);

                if (!strPassWord.isEmpty() && match) {
                    String channel = WalleChannelReader.getChannel(getApplicationContext());

                    HashMap<String, String> params = new HashMap<>();
                    String md5ToString = EncryptUtils.encryptMD5ToString(strPassWord);
                    params.put("userphone", userPhone);
                    params.put("password", md5ToString);
                    params.put("channel", channel);
                    params.put("terminal", "1");


                    JSONObject object = new JSONObject(params);

                    String Deskey = null;
                    String sign = null;
                    String deskey = null;
                    try {
                        int random = new Random().nextInt(10000000) + 89999999;
                        LogUtils.i("random", random);
                        Deskey = Des4.encode(object.toString(), String.valueOf(random));
                        String publicKey = getResources().getString(R.string.public_key);

                        deskey = RSA.encrypt(String.valueOf(random), Urls.PUCLIC_KEY + publicKey);
                        String privateKey = getResources().getString(R.string.private_key);
                        sign = RSA.sign(deskey, Urls.PRIVATE_KEY + privateKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    DesBean bean = new DesBean();
                    bean.setData(Deskey);
                    bean.setDeskey(deskey);
                    final Gson gson = new Gson();
                    String json = gson.toJson(bean);
                    OkGo.<String>post(Urls.NEW_Ip_url + Urls.Login.SettingPassWord)
                            .tag(this)
                            .headers("sign", sign)
                            .upJson(json)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    hud.dismiss();
                                    UserInfromBean fromJson = gson.fromJson(s, UserInfromBean.class);
                                    int errorCode = fromJson.getError_code();
                                    if (errorCode == 0) {


                                        SPUtils.put(SettingPassWordActivity.this, Urls.lock.TOKEN, fromJson.getData().getToken());

                                        UserBean userBean = new UserBean();
                                        userBean.setNickname(fromJson.getData().getNickname());
                                        userBean.setUserphone(fromJson.getData().getUserphone());
                                        userBean.setIdentity(fromJson.getData().getIdentity());
                                        SPUtils.put(SettingPassWordActivity.this, Urls.lock.USER_PHONE, fromJson.getData().getUserphone());

                                        EventBus.getDefault().post(new MessageEvent(userBean.getNickname(), userBean.getUserphone()));
                                        TinyDB tinyDB = new TinyDB(SettingPassWordActivity.this);
                                        tinyDB.putObject(fromJson.getData().getUserphone(), userBean);
                                        AppApplication.destoryActivity("login");
                                        ActivityStackManager.getInstance().popActivity(LoginActivity.class);
                                        EventBus.getDefault().post(new InformationEvent("user3"));
                                        if(checkbox.isChecked()){
                                            String link = getIntent().getStringExtra("link");
                                            Intent intent=new Intent(SettingPassWordActivity.this,HtmlActivity.class);
                                            intent.putExtra("Insurance","1");
                                            intent.putExtra("link",link);
                                            startActivity(intent);
                                        }
                                        finish();
                                    } else {

                                        ToastUtils.showToast(SettingPassWordActivity.this, fromJson.getError_message());
                                    }
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    ToastUtils.showToast(SettingPassWordActivity.this, "服务器出错了，请稍后再试");
                                    hud.dismiss();
                                }
                            });
                } else {
                    hud.dismiss();
                    ToastUtils.showToast(this, "格式不正确,请重新输入");
                }

                break;
            default:
                break;
        }
    }

    private void exit() {
        selfDialog = new SettingPassWordDialog(this);
        selfDialog.setYesOnclickListener("否", new SettingPassWordDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {

                selfDialog.dismiss();

            }
        });
        selfDialog.setNoOnclickListener("退出", () -> {

            selfDialog.dismiss();
            finish();
        });
        selfDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return super.onKeyDown(keyCode, event);
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
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
