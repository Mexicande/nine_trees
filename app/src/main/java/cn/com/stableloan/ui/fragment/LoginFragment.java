package cn.com.stableloan.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gt3unbindsdk.GT3GeetestButton;
import com.example.gt3unbindsdk.GT3GeetestUtils;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.ui.activity.ForgetWordActivity;
import cn.com.stableloan.ui.activity.Login2Activity;
import cn.com.stableloan.utils.EncryptUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.aes.Des4;
import cn.com.stableloan.utils.aes.Security;
import cn.com.stableloan.utils.editext.PowerfulEditText;
import cn.com.stableloan.view.RoundButton;
import okhttp3.Call;
import okhttp3.Response;

public class LoginFragment extends Fragment {

    // 设置获取id，challenge，success的URL，需替换成自己的服务器URL
    private static final String captchaURL = "http://47.94.175.112:8081/v1/geetes/captcha";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://47.94.175.112:8081/v1/geetes/verification";
    GT3GeetestUtils gt3GeetestUtils;
    @Bind(R.id.et_phone)
    PowerfulEditText etPhone;
    @Bind(R.id.et_passWord)
    PowerfulEditText etPassWord;
    @Bind(R.id.bt_login)
    RoundButton btLogin;
    @Bind(R.id.tv_freeRegistered)
    TextView tvFreeRegistered;
    @Bind(R.id.tv_forgetPassWord)
    TextView tvForgetPassWord;
    @Bind(R.id.view_passWord)
    LinearLayout viewPassWord;
    @Bind(R.id.ll_btn_type)
    GT3GeetestButton llBtnType;
    private boolean Atest = false;
    private boolean PowerfulEditText = false;


    private String AdressIp = "";

    private String times = "";


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        gt3GeetestUtils = GT3GeetestUtils.getInstance(getActivity());

        HashMap<String, String> params = new HashMap<>();
        params.put("userphone", "18500634223");
        params.put("password", "000000");

        JSONObject object=new JSONObject(params);
        try {
            String encode = Des4.encode(object.toString());
            LogUtils.i("加密后--",encode);


            String hsxQ = Des4.decode("Fox9ohngGtlmSxorc2hsxQ");
            LogUtils.i("解密后--",hsxQ);
        } catch (Exception e) {
            e.printStackTrace();
        }



        setListener();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void setListener() {
        etPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPassWord.getText().toString().length() > 5) {
                    if (!PowerfulEditText) {
                        PowerfulEditText = true;
                    } else {
                        LogUtils.i("etPassWord", "222222");
                        gt3GeetestUtils.getGeetest(captchaURL, validateURL, null);
                        setGt3GeetestUtilsListener();
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPhone.getText().toString().length() == 11) {
                    if (!PowerfulEditText) {
                        PowerfulEditText = true;
                    } else {
                        LogUtils.i("etphone", "111111");
                        gt3GeetestUtils.getGeetest(captchaURL, validateURL, null);
                        setGt3GeetestUtilsListener();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setGt3GeetestUtilsListener() {
        gt3GeetestUtils.getGeetest(captchaURL, validateURL, null);
        String ip = (String) SPUtils.get(getActivity(), "ip", "");

        if (ip != null) {
            AdressIp = ip;
        }

        LogUtils.i("ipAddress", AdressIp);
        Random random = new Random();
        int i = random.nextInt(99999) + 10000;

        long l = System.currentTimeMillis();

        times = String.valueOf(i) + String.valueOf(l);

        gt3GeetestUtils.setGtListener(new GT3GeetestUtils.GT3Listener() {
            /**
             * Api1可以在这添加参数
             */
            @Override
            public Map<String, String> captchaHeaders() {
                HashMap<String, String> params = new HashMap<>();
                params.put("ip", AdressIp);
                params.put("type", "mobile");
                params.put("unique", times);
                return params;
            }

            /**
             * 验证过程中有错误会走这里
             */
            @Override
            public void gt3DialogOnError(String error) {

            }

            /**
             * 点击验证码的关闭按钮来关闭验证码
             */
            @Override
            public void gt3CloseDialog() {

            }

            /**
             * 点击屏幕关闭验证码
             */
            @Override
            public void gt3CancelDialog() {

            }

            /**
             * 拿到二次验证需要的数据
             */
            @Override
            public void gt3GetDialogResult(String result) {


            }


            /**
             * 自定义二次验证，当gtSetIsCustom为ture时执行这里面的代码
             */

            @Override
            public void gt3GetDialogResult(boolean success, String result) {
                if (success) {
                    HashMap<String, String> params = null;
                    try {
                        params = new HashMap<>();
                        JSONObject jsonObject = new JSONObject(result);
                        params.put("ip", AdressIp);
                        params.put("type", "mobile");
                        params.put("unique", times);
                        params.put("g_challenge", jsonObject.getString("geetest_challenge"));
                        params.put("g_seccode", jsonObject.getString("geetest_seccode"));
                        params.put("g_validate", jsonObject.getString("geetest_validate"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject object = new JSONObject(params);
                    OkGo.post(validateURL)
                            .tag(this)
                            .upJson(object)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    LogUtils.i("自定义二次验证", s);
                                    try {
                                        JSONObject jsonObject = new JSONObject(s);

                                        String code = jsonObject.getString("code");
                                        String error_code = jsonObject.getString("error_code");

                                        if ("200".equals(code) || "0".equals(error_code)) {
                                            btLogin.setEnabled(true);
                                            Atest = true;
                                            btLogin.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    loginUser();

                                                    ToastUtils.showToast(getActivity(), "验证成功");
                                                }
                                            });
                                            gt3GeetestUtils.gt3TestFinish();

                                        } else {
                                            gt3DialogOnError("验证失败，请重新验证");
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                    /**
                     *  利用异步进行解析这result进行二次验证，结果成功后调用gt3GeetestUtils.gt3TestFinish()方法调用成功后的动画，然后在gt3DialogSuccess执行成功之后的结果
                     * //
                     //          JSONObject res_json = new JSONObject(result);
                     //
                     //                Map<String, String> validateParams = new HashMap<>();
                     //
                     //                validateParams.put("geetest_challenge", res_json.getString("geetest_challenge"));
                     //
                     //                validateParams.put("geetest_validate", res_json.getString("geetest_validate"));
                     //
                     //                validateParams.put("geetest_seccode", res_json.getString("geetest_seccode"));
                     在二次验证结果验证完成之后，执行gt3GeetestUtils.gt3TestFinish()方法进行动画执行
                     */

                }

            }

            /**
             * 第一次次请求后数据
             */
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {

            }


            /**
             * 往二次验证里面put数据，是map类型,注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
             */
            @Override
            public Map<String, String> gt3SecondResult() {

                return null;
            }


            /**
             * 验证全部走完的回调，result为验证后的数据
             */
            //请求成功数据
            @Override
            public void gt3DialogSuccessResult(String result) {
//                Gt3GeetestTestMsg.setCandotouch(false);//这里设置验证成功后是否可以关闭

            }


            /**
             * 设置网络的头部信息
             */
            @Override
            public Map<String, String> validateHeaders() {
                return null;
            }


            /**
             * 设置是否自定义第二次验证ture为是 默认为false(不自定义)
             * 如果为false这边的的完成走gt3GetDialogResult(String result) ，后续流程SDK帮忙走完，不需要做操作
             * 如果为true这边的的完成走gt3GetDialogResult(boolean a, String result)，同时需要完成gt3GetDialogResult里面的二次验证，验证完毕记得关闭dialog,调用gt3GeetestUtils.gt3TestFinish();
             * 完成方法统一是gt3DialogSuccess
             */

            @Override
            public boolean gtSetIsCustom() {

                return true;
            }


            /**
             * 判断自定义按键是否被点击
             */

            @Override
            public void gtIsClick(boolean a) {
                if (a) {
                    //被点击
                }
            }

            /**
             * 当验证码放置10分钟后，重新启动验证码
             */
            @Override
            public void rege() {

//                gt3GeetestUtils.getGeetest(captchaURL,validateURL,null);

            }
        });

    }

    /**
     * 登陆
     */

    private KProgressHUD hud;
    private void loginUser() {

        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(true)
                .show();

        HashMap<String, String> params = new HashMap<>();

        String md5ToString = EncryptUtils.encryptMD5ToString(etPassWord.getText().toString());

        params.put("userphone", etPhone.getText().toString());
        params.put("password", md5ToString);
        JSONObject object = new JSONObject(params);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_freeRegistered, R.id.tv_forgetPassWord})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_freeRegistered:
                Login2Activity.switchmultibutton.setSelectedTab(0);
                break;
            case R.id.tv_forgetPassWord:
                ForgetWordActivity.launch(getActivity());
                break;
        }
    }
}
