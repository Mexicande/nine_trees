package cn.com.stableloan.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gt3unbindsdk.GT3GeetestButton;
import com.example.gt3unbindsdk.GT3GeetestUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.CodeMessage;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.MessageCode;
import cn.com.stableloan.model.MessageEvent;
import cn.com.stableloan.ui.activity.HtmlActivity;
import cn.com.stableloan.ui.activity.SettingPassWordActivity;
import cn.com.stableloan.utils.CaptchaTimeCount;
import cn.com.stableloan.utils.Constants;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.editext.PowerfulEditText;
import cn.com.stableloan.view.RoundButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    // 设置获取id，challenge，success的URL，需替换成自己的服务器URL
    GT3GeetestUtils gt3GeetestUtils;
    @Bind(R.id.et_phone)
    PowerfulEditText etPhone;
    @Bind(R.id.bt_getCodeLogin)
    RoundButton btGetCodeLogin;
    @Bind(R.id.layout_phoe)
    LinearLayout layoutPhoe;
    @Bind(R.id.layout_code)
    LinearLayout layoutCode;
    @Bind(R.id.change_phone)
    TextView changePhone;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.ll_btn_type1)
    GT3GeetestButton llBtnType1;
    @Bind(R.id.et_code)
    PowerfulEditText etCode;
    @Bind(R.id.bt_message_login)
    RoundButton btMessageLogin;
    @Bind(R.id.bt_getCode)
    Button btGetCode;
    private CaptchaTimeCount captchaTimeCount;

    private String AdressIp = "";

    private String times = "";
    private Context context;

    private String unique="";
    private String gtcode="";
    private String status="1";

    private boolean Atest = false;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        String unique = (String) SPUtils.get(getActivity(), "unique", null);
        if(unique!=null){
            times=unique;
        }else {
            Random random = new Random();
            int i = random.nextInt(99999) + 10000;
            long l = System.currentTimeMillis();
            times = String.valueOf(i) + String.valueOf(l);
        }
        captchaTimeCount = new CaptchaTimeCount(Constants.Times.MILLIS_IN_TOTAL, Constants.Times.COUNT_DOWN_INTERVAL,btGetCode , getActivity());
        gt3GeetestUtils = GT3GeetestUtils.getInstance(getActivity());

        setListener();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            //相当于Fragment的onPause
            Atest=false;

          //  gt3GeetestUtils = GT3GeetestUtils.getInstance(getActivity());

           // setGt3GeetestUtilsListener();

        } else {
           // gt3GeetestUtils = GT3GeetestUtils.getInstance(getActivity());

            // 相当于Fragment的onResume
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setListener() {

        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutCode.setVisibility(View.GONE);
                layoutPhoe.setVisibility(View.VISIBLE);
                btGetCodeLogin.setEnabled(false);
            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPhone.getText().length() == 11) {
                    GT3GeetestListener();
                    btGetCodeLogin.setEnabled(true);
                    btGetCodeLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Atest) {
                                getMessage();
                            } else {
                                ToastUtils.showToast(getActivity(), "为了你的账户安全，请点击按钮进行验证");
                            }

                        }
                    });
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etCode.getText().toString().length() == 4) {
                    btMessageLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void GT3GeetestListener() {
        gt3GeetestUtils.getGeetest(Urls.Ip_url+ Urls.Login.captchaURL, Urls.Ip_url+ Urls.Login.validateURL, null);

        gt3GeetestUtils.getGeetest(Urls.Ip_url+ Urls.Login.captchaURL, Urls.Ip_url+ Urls.Login.validateURL, null);
        changePhone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        changePhone.getPaint().setAntiAlias(true);
        AdressIp = getIpAddress();

        SPUtils.put(getActivity(), "ip", AdressIp);
        LogUtils.i("ipAddress", AdressIp);

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

                LogUtils.i("params", params.toString());
                return params;
            }

            /**
             * 验证过程中有错误会走这里
             */
            @Override
            public void gt3DialogOnError(String error) {
                LogUtils.i("验证过程中有错误会走这里", error);
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
                LogUtils.i("result", result);
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
                    OkGo.post(Urls.Ip_url+ Urls.Login.validateURL)
                            .tag(this)
                            .upJson(object)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    LogUtils.i("自定义二次验证", s);
                                    Gson gson=new Gson();
                                    MessageCode messageCode = gson.fromJson(s, MessageCode.class);
                                    LogUtils.i("自定义二次验证", messageCode);

                                    if(messageCode.getError_code()==0){
                                        Atest = true;
                                        btGetCodeLogin.setEnabled(true);
                                        gt3GeetestUtils.gt3TestFinish();
                                        gt3GeetestUtils.gt3TestFinish();
                                        gtcode=messageCode.getData().getGtcode();
                                    }else {
                                        gt3DialogOnError("验证失败，请重新验证");

                                    }

                                   /* try {
                                        JSONObject jsonObject = new JSONObject(s);
                                        String data = jsonObject.getString("data");
                                        int error_code = jsonObject.getInt("error_code");
                                        if (error_code==0) {
                                            btGetCodeLogin.setEnabled(true);
                                            gt3GeetestUtils.gt3TestFinish();
                                            Gson gson=new Gson();
                                            GtDateBean bean = gson.fromJson(data, GtDateBean.class);
                                            gt3GeetestUtils.gt3TestFinish();
                                            gtcode=bean.getGtcode();
                                        } else {
                                            gt3DialogOnError("验证失败，请重新验证");
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }*/

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
                LogUtils.i("第一次次请求后数据", jsonObject.toString());

            }


            /**
             * 往二次验证里面put数据，是map类型,注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
             */
            @Override
            public Map<String, String> gt3SecondResult() {
               /* HashMap<String, String> params = null;
                try {
                    params = new HashMap<>();
                    JSONObject jsonObject=new JSONObject(Atest);
                    params.put("ip", AdressIp);
                    params.put("type", "mobile");
                    params.put("unique", times);
                    params.put("g_challenge",jsonObject.getString("geetest_challenge"));
                    params.put("g_seccode",jsonObject.getString("geetest_seccode"));
                    params.put("g_validate",jsonObject.getString("geetest_validate"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                return null;
            }


            /**
             * 验证全部走完的回调，result为验证后的数据
             */
            //请求成功数据
            @Override
            public void gt3DialogSuccessResult(String result) {
                LogUtils.i("验证全部走完的回调", result);

               /* btGetCodeLogin.setEnabled(true);
                ;*/

                //Gt3GeetestTestMsg.setCandotouch(false);//这里设置验证成功后是否可以关闭

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
     * Get the verification code
     */
    private void getMessage() {
        if (etPhone.getText().toString().length()==11) {

            HashMap<String, String> params = new HashMap<>();
            params.put("userphone", etPhone.getText().toString());
            params.put("terminal","1");

            JSONObject jsonObject = new JSONObject(params);
            OkGo.<MessageCode>post(Urls.Ip_url+ Urls.times.MESSAGE_SEND)
                    .tag(this)
                    .upJson(jsonObject)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {

                            Gson gson = new Gson();
                            MessageCode body = gson.fromJson(s, MessageCode.class);
                                if (0==body.getError_code()) {
                                    Atest=false;

                                    ToastUtils.showToast(getActivity(), "发送成功");
                                    String phone = etPhone.getText().toString();
                                    if (phone.length() == 11) {
                                        StringBuffer str = new StringBuffer(phone);
                                        str.insert(3, " ");
                                        str.insert(8, " ");
                                        tvPhone.setText("+86 " + str);
                                        captchaTimeCount.start();
                                    }
                                    btGetCodeLogin.setEnabled(true);
                                    layoutPhoe.setVisibility(View.GONE);
                                    layoutCode.setVisibility(View.VISIBLE);

                                } else {
                                    ToastUtils.showToast(getActivity(), body.getError_message());
                                }




                        }
                    });
        } else {
            ToastUtils.showToast(getActivity(), "手机号格式错误");
        }

    }

    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {

        }
        return "";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.bt_message_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_message_login:
                    HashMap<String, String> params = new HashMap<>();
                    params.put("userphone", etPhone.getText().toString());
                    params.put("code", etCode.getText().toString());
                    params.put("gtcode", gtcode);
                    params.put("status",status);
                    params.put("unique",times);
                    JSONObject object=new JSONObject(params);
                    OkGo.<String>post(Urls.Ip_url+ Urls.Login.QUICK_LOGIN)
                            .tag(this)
                            .upJson(object)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson gson=new Gson();
                                    CodeMessage codeMessage = gson.fromJson(s, CodeMessage.class);
                                    LogUtils.i("CodeMessage",codeMessage);

                                    if(codeMessage.getError_code()==0){
                                        if("1".equals(codeMessage.getData().getStatus())){
                                            SPUtils.put(getActivity(), "token", codeMessage.getData().getToken());
                                            String from = getActivity().getIntent().getStringExtra("from");
                                            Serializable welfare = getActivity().getIntent().getSerializableExtra("welfare");
                                            SPUtils.put(getActivity(), "login", true);
                                            if (from != null) {
                                                if(from.equals("user")){
                                                    EventBus.getDefault().post(new InformationEvent("user3"));
                                                    getActivity().finish();
                                                }else if(from.equals("123")){
                                                    EventBus.getDefault().post(new InformationEvent("user3"));
                                                    getActivity().finish();
                                                }else if(from.equals("user2")){
                                                    EventBus.getDefault().post(new InformationEvent("userinfor"));
                                                    EventBus.getDefault().post(new MessageEvent("","1"));
                                                    getActivity().finish();
                                                }else if(from.equals("collection")){
                                                    Intent intent = new Intent();
                                                    intent.putExtra("ok", "ok");
                                                    getActivity().setResult(2000, intent);
                                                    getActivity().finish();
                                                }
                                            /*else if(from.equals("user1")){
                                                                    setResult(4000, new Intent().putExtra("user", bean));
                                                                    finish();
                                                                }else if(from.equals("user2")){
                                                                    setResult(4000, new Intent().putExtra("user", bean));
                                                                    finish();
                                                                }*/
                                            } else if(welfare!=null){
                                                startActivity(new Intent(getActivity(), HtmlActivity.class).putExtra("welfare",welfare));
                                                getActivity().finish();
                                            } else{
                                                EventBus.getDefault().post(new MessageEvent("","1"));
                                                getActivity().finish();
                                            }
                                        }else {
                                            LogUtils.i("status",codeMessage.getData().getStatus());
                                            startActivity(new Intent(getActivity(), SettingPassWordActivity.class).putExtra("userPhone",etPhone.getText().toString()));
                                        }
                                    }else {
                                        ToastUtils.showToast(getActivity(),codeMessage.getError_message());
                                    }
                                }
                            });
                break;
        }
    }
   /* @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            Atest=false;
        LogUtils.i("1","不可见");
         } else {
        // 相当于Fragment的onResume
        System.out.println("界面可见");
         }
    }*/
}
