package cn.com.stableloan.ui.fragment;

import android.content.Intent;
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
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
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
import cn.com.stableloan.model.DesBean;
import cn.com.stableloan.model.MessageCode;
import cn.com.stableloan.model.MessageEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.model.UserInfromBean;
import cn.com.stableloan.model.WelfareBean;
import cn.com.stableloan.ui.activity.ForgetWordActivity;
import cn.com.stableloan.ui.activity.HtmlActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.utils.EncryptUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TimeUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.aes.Des4;
import cn.com.stableloan.utils.editext.PowerfulEditText;
import cn.com.stableloan.utils.ras.RSA;
import cn.com.stableloan.view.RoundButton;
import okhttp3.Call;
import okhttp3.Response;

public class LoginFragment extends Fragment {

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
    @Bind(R.id.bt_VisiableButton)
    RoundButton btVisiableButton;
    private boolean Atest = false;
    private boolean PowerfulEditText = false;

    private String gtcode = "";
    private String status = "2";
    private String AdressIp = "";

    private String times = "";
    private final int Flag_User = 3000;
    private final int LOTTERY_CODE = 500;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        gt3GeetestUtils = GT3GeetestUtils.getInstance(getActivity());
        ButterKnife.bind(this, view);
        setGt3GeetestUtilsListener();
        long date = (long) SPUtils.get(getActivity(), "date", 1111111111111L);
        boolean today = TimeUtils.isToday(date);
        if (today) {
            llBtnType.setVisibility(View.VISIBLE);
        } else {
            Atest = true;
            SPUtils.remove(getActivity(), "date");
            llBtnType.setVisibility(View.GONE);
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
                        btVisiableButton.setVisibility(View.GONE);
                        btLogin.setVisibility(View.VISIBLE);
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
                        btVisiableButton.setVisibility(View.GONE);
                        btLogin.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setGt3GeetestUtilsListener() {
        gt3GeetestUtils.getGeetest(Urls.Ip_url+Urls.Login.captchaURL, Urls.Ip_url+Urls.Login.validateURL, null);
        gt3GeetestUtils.getGeetest(Urls.Ip_url+Urls.Login.captchaURL, Urls.Ip_url+Urls.Login.validateURL, null);
        String ip = (String) SPUtils.get(getActivity(), "ip", "");

        if (ip != null) {
            AdressIp = ip;
        }
        String unique = (String) SPUtils.get(getActivity(), "unique", "1212");
        LogUtils.i("ipAddress", AdressIp);
        times = unique;
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
                LogUtils.i("极验------", error);
                gt3GeetestUtils.getGeetest(Urls.Ip_url+Urls.Login.captchaURL, Urls.Ip_url+Urls.Login.validateURL, null);

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
                    OkGo.post(Urls.Ip_url+Urls.Login.validateURL)
                            .tag(this)
                            .upJson(object)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson gson = new Gson();
                                    MessageCode messageCode = gson.fromJson(s, MessageCode.class);
                                    if (messageCode.getError_code() == 0) {
                                        Atest = true;
                                        status = "1";
                                        gt3GeetestUtils.gt3TestFinish();
                                        gtcode = messageCode.getData().getGtcode();
                                    } else {
                                        gt3DialogOnError("验证失败，请重新验证");

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
        params.put("gtcode", gtcode);
        params.put("status", status);
        params.put("unique", times);
        JSONObject object = new JSONObject(params);
        String Deskey = null;
        String sign = null;
        String deskey = null;
        try {
            int random = new Random().nextInt(10000000) + 89999999;
            LogUtils.i("random", random);
            Deskey = Des4.encode(object.toString(), String.valueOf(random));
            deskey = RSA.encrypt(String.valueOf(random), Urls.PUCLIC_KEY);
            sign = RSA.sign(deskey, Urls.PRIVATE_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DesBean bean = new DesBean();
        bean.setData(Deskey);
        bean.setDeskey(deskey);
        final Gson gson = new Gson();

        String json = gson.toJson(bean);

        OkGo.<String>post(Urls.Ip_url + Urls.Login.LOGIN)
                .tag(this)
                .headers("sign", sign)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        hud.dismiss();
                        UserInfromBean infromBean = gson.fromJson(s, UserInfromBean.class);

                        if (infromBean.getError_code() == 0) {
                            SPUtils.put(getActivity(), "token", infromBean.getData().getToken());
                            SPUtils.put(getActivity(), "login", true);
                            UserBean userBean = new UserBean();
                            userBean.setNickname(infromBean.getData().getNickname());
                            userBean.setUserphone(infromBean.getData().getUserphone());
                            userBean.setIdentity(infromBean.getData().getIdentity());

                            EventBus.getDefault().post(new MessageEvent(userBean.getNickname(), userBean.getUserphone()));
                            TinyDB tinyDB = new TinyDB(getActivity());
                            tinyDB.putObject("user", userBean);
                            String from = getActivity().getIntent().getStringExtra("from");
                            WelfareBean.DataBean welfare = (WelfareBean.DataBean) getActivity().getIntent().getSerializableExtra("welfare");
                            if (from != null) {
                                Log.i("from------", "from");
                                if (from.equals("user")) {
                                    getActivity().setResult(Flag_User, new Intent().putExtra("user", userBean));
                                    getActivity().finish();
                                } else if (from.equals("123")) {
                                    //EventBus.getDefault().post(new InformationEvent("user2"));
                                    getActivity().setResult(LOTTERY_CODE, new Intent().putExtra("Loffery", "123"));
                                    getActivity().finish();
                                } else if (from.equals("user1")) {
                                    getActivity().setResult(4000, new Intent().putExtra("user", userBean));
                                    getActivity().finish();
                                } else if (from.equals("user2")) {
                                    getActivity().setResult(4000, new Intent().putExtra("user", userBean));
                                    getActivity().finish();
                                }
                            } else if (welfare != null) {
                                startActivity(new Intent(getActivity(), HtmlActivity.class).putExtra("welfare", welfare));
                                getActivity().finish();

                            } else {
                                getActivity().finish();
                            }
                        } else {
                            long timeMillis = System.currentTimeMillis();
                            SPUtils.put(getActivity(), "date", timeMillis);
                            llBtnType.setVisibility(View.VISIBLE);
                            Atest = false;
                            gt3GeetestUtils.getGeetest(Urls.Ip_url+Urls.Login.captchaURL, Urls.Ip_url+Urls.Login.validateURL, null);
                            ToastUtils.showToast(getActivity(), infromBean.getError_message());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hud.dismiss();
                            }
                        });
                    }
                });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_freeRegistered, R.id.tv_forgetPassWord, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_freeRegistered:
                LoginActivity.switchmultibutton.setSelectedTab(0);
                break;
            case R.id.tv_forgetPassWord:
                ForgetWordActivity.launch(getActivity());
                break;
            case R.id.bt_login:
                if (Atest) {
                    loginUser();
                } else {
                    ToastUtils.showToast(getActivity(), "为了你的账户安全，请点击按钮进行验证");
                }

                break;
        }
    }
}
