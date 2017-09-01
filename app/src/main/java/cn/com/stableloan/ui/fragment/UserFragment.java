package cn.com.stableloan.ui.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.bean.UpdateEvent;
import cn.com.stableloan.bean.UserEvent;
import cn.com.stableloan.model.MessageEvent;
import cn.com.stableloan.model.SaveBean;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.model.UserInfromBean;
import cn.com.stableloan.model.integarl.Personal;
import cn.com.stableloan.ui.activity.CashActivity;
import cn.com.stableloan.ui.activity.CollectionActivity;
import cn.com.stableloan.ui.activity.FeedbackActivity;
import cn.com.stableloan.ui.activity.GestureLoginActivity;
import cn.com.stableloan.ui.activity.IntegralActivity;
import cn.com.stableloan.ui.activity.Setting1Activity;
import cn.com.stableloan.ui.activity.UserInformationActivity;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.ui.activity.integarl.InviteFriendsActivity;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.utils.constant.Constant;
import cn.com.stableloan.view.SelfDialog;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends ImmersionFragment {


    @Bind(R.id.User_logo)
    ImageView UserLogo;
    @Bind(R.id.tv_nick)
    TextView tvNick;
    @Bind(R.id.tv_UserPhone)
    TextView tvUserPhone;
    @Bind(R.id.feedback)
    SuperTextView feedback;
    @Bind(R.id.tv_Integral)
    TextView tvIntegral;
    @Bind(R.id.bt_Money)
    TextView btMoney;

    private ACache aCache;
    private Bitmap splashBitmap;
    private int screenWidth, screenHeight;
    private Handler handler = new Handler() {
    };

    private SelfDialog selfDialog;

    private static final int FLAG_Profession = 1;
    private static final int SEND_Profession_ = 1000;


    private static final int FLAG_NICK = 2;
    private static final int SEND_NICK = 2000;
    private static final int FLAG_LOGIN = 3;
    private static final int SEND_LOGIN = 4000;

    private static final int Moon = 1;

    public UserFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        aCache = ACache.get(getActivity());
        getUserInfo();
        return view;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            //相当于Fragment的onPause
            getUserInfo();

        } else {
            // 相当于Fragment的onResume
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();


    }

    private void getUserInfo() {
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("我的", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//记录事件
        ZhugeSDK.getInstance().track(getActivity(), "minepage", eventObject);
        String token = (String) SPUtils.get(getActivity(), "token", "1");
        final TinyDB tinyDB = new TinyDB(getActivity());
        if (!"1".equals(token)) {
            HashMap<String, String> params = new HashMap<>();
            params.put("token", token);
            JSONObject jsonObject = new JSONObject(params);
            OkGo.post(Urls.Ip_url + Urls.user.USERT_INFO)
                    .tag(this)
                    .upJson(jsonObject.toString())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            if(s!=null){
                                Gson gson=new Gson();
                                Personal personal = gson.fromJson(s, Personal.class);
                                if(personal.getError_code()==0){
                                    tinyDB.putObject("user", personal.getData());
                                    tvNick.setText(personal.getData().getNickname());
                                    tvUserPhone.setText(personal.getData().getUserphone());
                                    tvIntegral.setText(personal.getData().getCredits());
                                    btMoney.setText(personal.getData().getTotal());
                                }else {
                                    ToastUtils.showToast(getActivity(),personal.getError_message());
                                }
                            }

                        }
                    });
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(false)
                .navigationBarColor(R.color.md_grey_900)
                .statusBarAlpha(0.3f)
                .init();
    }


    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (!event.userNick.isEmpty()) {
            tvNick.setText(event.userNick);
        }
        if (event.phone != null) {
            tvUserPhone.setText(event.phone);
        }
        if (event.phone.equals("1")) {
            TextUser();
        }

    }

    @Subscribe
    public  void updateEvent(UpdateEvent msg){
        if(msg!=null){
            if("user".equals(msg.msg)){
                getUserInfo();
            }
        }

    }
    @Subscribe
    public void onUserEvent(UserEvent event) {
        UserInfromBean userNick = event.userNick;
        if (userNick != null) {
            tvNick.setText(userNick.getData().getNickname());
            tvUserPhone.setText(userNick.getData().getUserphone());
            tvIntegral.setText(userNick.getData().getCredits());
            btMoney.setText(userNick.getData().getTotal());
        }
    }

    @OnClick({R.id.layout_my, R.id.layout_setting, R.id.feedback, R.id.layout_collection,
            R.id.layout_Integral, R.id.laout_Money,R.id.invite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_my:
                TextUser();
                break;
            case R.id.layout_setting:
                Setting1Activity.launch(getActivity());
                break;
            case R.id.feedback:
                FeedbackActivity.launch(getActivity());
                break;
            case R.id.layout_collection:
                CollectionActivity.launch(getActivity());
                break;
            case R.id.layout_Integral:
                IntegralActivity.launch(getActivity());
                break;
            case R.id.laout_Money:
                CashActivity.launch(getActivity());
                break;
            case R.id.invite:
                InviteFriendsActivity.launch(getActivity());
                break;
                
        }
    }


    private KProgressHUD hud;

    private void TextUser() {

        String token = (String) SPUtils.get(getActivity(), "token", "1");
        String signature = (String) SPUtils.get(getActivity(), "signature", "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signature);
        JSONObject jsonObject = new JSONObject(params);
        OkGo.<String>post(Urls.NEW_URL + Urls.Login.Immunity)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            String isSuccess = object.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                String status = object.getString("status");
                                if ("1".equals(status)) {
                                    UserInformationActivity.launch(getActivity());
                                } else {
                                    String gesturePassword = aCache.getAsString(Constant.GESTURE_PASSWORD);
                                    if (gesturePassword == null || "".equals(gesturePassword)) {
                                        Intent intent = new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "userinformation");
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(getActivity(), GestureLoginActivity.class);
                                        startActivity(intent);
                                    }
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(getActivity(), "网络异常，请稍后再试");
                            }
                        });
                    }
                });


    }
    private SaveBean saveBean;

    private void getDate() {

        String token = (String) SPUtils.get(getActivity(), "token", "1");

        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_URL + Urls.STATUS.Getsetting)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            String isSuccess = object.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                Gson gson = new Gson();
                                saveBean = gson.fromJson(s, SaveBean.class);
                                String way1 = saveBean.getWay();
                                String gesturePassword = aCache.getAsString(Constant.GESTURE_PASSWORD);
                                if (way1 != null) {
                                    if ("0".equals(way1)) {
                                        Intent intent = new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "userinformation");
                                        startActivity(intent);

                                    } else {
                                        if (gesturePassword == null || "".equals(gesturePassword)) {
                                            Intent intent = new Intent(getActivity(), GestureLoginActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "userinformation");
                                            startActivity(intent);
                                        }

                                    }
                                }

                            } else {
                                String msg = object.getString("msg");
                                ToastUtils.showToast(getActivity(), msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });


    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
