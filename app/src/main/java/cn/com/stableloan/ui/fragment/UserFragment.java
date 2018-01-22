package cn.com.stableloan.ui.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
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
import cn.com.stableloan.model.UserInfromBean;
import cn.com.stableloan.model.integarl.Personal;
import cn.com.stableloan.ui.activity.CashActivity;
import cn.com.stableloan.ui.activity.CollectionActivity;
import cn.com.stableloan.ui.activity.FeedbackActivity;
import cn.com.stableloan.ui.activity.GestureLoginActivity;
import cn.com.stableloan.ui.activity.IntegralActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.UpdataProfessionActivity;
import cn.com.stableloan.ui.activity.UserInformationActivity;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.ui.activity.integarl.InviteFriendsActivity;
import cn.com.stableloan.ui.activity.integarl.SafeSettingActivity;
import cn.com.stableloan.ui.activity.safe.FingerActivity;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.utils.constant.Constant;
import cn.com.stableloan.utils.fingerprint.FingerprintIdentify;
import cn.com.stableloan.view.dialog.Wechat_dialog;
import cn.com.stableloan.view.supertextview.SuperTextView;
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
    @Bind(R.id.feedback)
    SuperTextView feedback;
    @Bind(R.id.tv_Integral)
    TextView tvIntegral;
    @Bind(R.id.bt_Money)
    TextView btMoney;

    private Wechat_dialog wechat_dialog;

    private  boolean ONRESUEM=false;
    public UserFragment() {

    }
    private FingerprintIdentify mFingerprintIdentify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            //相当于Fragment的onPause
            getUserInfo();

        } else {
            getUserInfo();
            // 相当于Fragment的onResume
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void getUserInfo() {

/*
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("我的", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//记录事件
        ZhugeSDK.getInstance().track(getActivity(), "我的", eventObject);*/
        String token = (String) SPUtils.get(getActivity(), Urls.TOKEN, "1");
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
                                    String userphone = (String) SPUtils.get(getActivity(), Urls.lock.USER_PHONE, "1");
                                    tinyDB.putObject(userphone, personal.getData());
                                    tvNick.setText(personal.getData().getNickname());
                                    tvIntegral.setText(personal.getData().getCredits());
                                    btMoney.setText(personal.getData().getTotal());
                                }else if(personal.getError_code()==2){
                                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                                    intent.putExtra("message",personal.getError_message());
                                    intent.putExtra("from","error_UserFragment");
                                    startActivityForResult(intent,200);
                                }else if(personal.getError_code()==1136){
                                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                                    intent.putExtra("message","1136");
                                    intent.putExtra("from","1136");
                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
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

    @Subscribe
    public  void updateEvent(UpdateEvent msg){
        if(msg!=null){
            if("user".equals(msg.msg)){
                getUserInfo();
            }
        }

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

    }

    @Subscribe
    public void onUserEvent(UserEvent event) {
        UserInfromBean userNick = event.userNick;
        if (userNick != null) {
            tvNick.setText(userNick.getData().getNickname());
            tvIntegral.setText(userNick.getData().getCredits());
            btMoney.setText(userNick.getData().getTotal());
        }
    }

    @OnClick({R.id.layout_my, R.id.layout_setting, R.id.feedback, R.id.layout_collection,
            R.id.layout_Integral, R.id.laout_Money,R.id.invite,R.id.iv_Edit,R.id.layout_attention})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_my:
                TextUser();
                break;
            case R.id.iv_Edit:
                startActivityForResult(new Intent(getActivity(), UpdataProfessionActivity.class),100);
                break;
            case R.id.layout_setting:
                SafeSettingActivity.launch(getActivity());
                break;
            case R.id.feedback:
                FeedbackActivity.launch(getActivity());
                break;
            case R.id.layout_collection:
                CollectionActivity.launch(getActivity());
                break;
            case R.id.layout_Integral:
                startActivityForResult(new Intent(getActivity(),IntegralActivity.class),200);

                break;
            case R.id.laout_Money:
                startActivityForResult(new Intent(getActivity(),CashActivity.class),200);

                break;
            case R.id.invite:
                startActivityForResult(new Intent(getActivity(),InviteFriendsActivity.class).putExtra("nick",tvNick.getText().toString()),200);
                break;
            case R.id.layout_attention:
                wechat_dialog = new Wechat_dialog(getActivity());
                wechat_dialog.setYesOnclickListener("去微信", new Wechat_dialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        wechat_dialog.dismiss();
                       if(isWeixinAvilible(getActivity())){
                           Intent intent = new Intent();
                           ComponentName cmp=new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                           intent.setAction(Intent.ACTION_MAIN);
                           intent.addCategory(Intent.CATEGORY_LAUNCHER);
                           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           intent.setComponent(cmp);
                           startActivity(intent);
                           getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                       }else {
                           ToastUtils.showToast(getActivity(),"请先安装微信");
                       }


                    }
                });
                wechat_dialog.setNoOnclickListener("取消", new Wechat_dialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {

                        wechat_dialog.dismiss();


                    }
                });
                wechat_dialog.show();
                break;
            default:
                break;

        }
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (("com.tencent.mm").equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void TextUser() {
             String userphone = (String) SPUtils.get(getActivity(), Urls.lock.USER_PHONE, "1");
                    int cat = (int) SPUtils.get(getActivity(), userphone+Urls.lock.CAT, 0);
                       switch (cat){
                           case Urls.lock.NO_VERIFICATION:
                               startActivityForResult(new Intent(getActivity(),UserInformationActivity.class),200);
                              // UserInformationActivity.launch(getActivity());
                               break;
                           case Urls.lock.GESTURE_VERIFICATION:
                               startActivityForResult(new Intent(getActivity(),GestureLoginActivity.class).putExtra("from", "userinformation"),200);
                              /* Intent intent = new Intent(getActivity(), GestureLoginActivity.class).putExtra("from", "userinformation");
                               startActivity(intent);*/
                               break;
                           case Urls.lock.PW_VERIFICATION:
                               startActivityForResult(new Intent(getActivity(),Verify_PasswordActivity.class).putExtra("from", "userinformation"),200);
                              /* Intent intent2 = new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "userinformation");
                               startActivity(intent2);*/
                               break;
                           case Urls.lock.GESTURE_FINGER:
                               mFingerprintIdentify = new FingerprintIdentify(getActivity());

                               //硬件设备是否已录入指纹
                               boolean registeredFingerprint = mFingerprintIdentify.isRegisteredFingerprint();
                               //指纹功能是否可用
                               boolean fingerprintEnable = mFingerprintIdentify.isFingerprintEnable();
                               if (registeredFingerprint && fingerprintEnable) {
                                   Intent intent=new Intent(getActivity(),FingerActivity.class);
                                   intent.putExtra("from","userinformation");
                                   startActivity(intent);
                               } else {
                                   startActivity(new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "userinformation"));
                               }
                               break;
                           default:
                               break;
                       }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            switch (resultCode){
                case 100:
                    if(data!=null){
                        String nick = data.getStringExtra("nick");
                        if(nick!=null){
                            tvNick.setText(nick);
                        }
                    }
                    break;
            }
        }
        if(requestCode==200){
            getUserInfo();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
