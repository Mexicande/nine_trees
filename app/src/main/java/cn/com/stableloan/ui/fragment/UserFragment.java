package cn.com.stableloan.ui.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.ApiService;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.interfaceutils.OnRequestDataListener;
import cn.com.stableloan.model.integarl.Personal;
import cn.com.stableloan.ui.activity.CashActivity;
import cn.com.stableloan.ui.activity.CollectionActivity;
import cn.com.stableloan.ui.activity.FeedbackActivity;
import cn.com.stableloan.ui.activity.IntegralActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.UserInformationActivity;
import cn.com.stableloan.ui.activity.integarl.InviteFriendsActivity;
import cn.com.stableloan.ui.activity.integarl.SafeSettingActivity;
import cn.com.stableloan.utils.ActivityUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.dialog.Wechat_dialog;
import cn.com.stableloan.view.supertextview.SuperTextView;

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
    private String token;
    private Wechat_dialog wechat_dialog;
    public UserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            getUserInfo();
        }
    }

    private void getUserInfo() {
        token = (String) SPUtils.get(getActivity(), Urls.lock.TOKEN, null);
        if(getToken()){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Urls.lock.TOKEN, token);
                ApiService.GET_SERVICE(Urls.user.USERT_INFO, jsonObject, new OnRequestDataListener() {
                    @Override
                    public void requestSuccess(int code, JSONObject data) {
                        Gson gson=new Gson();
                        Personal personal = gson.fromJson(data.toString(), Personal.class);
                        if(personal.getError_code()==0){
                            tvNick.setText(personal.getData().getNickname());
                            tvIntegral.setText(personal.getData().getCredits());
                            btMoney.setText(personal.getData().getTotal());
                        } else {
                            ToastUtils.showToast(getActivity(),personal.getError_message());
                        }
                    }

                    @Override
                    public void requestFailure(int code, String msg) {

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

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

    private boolean getToken(){
        if(TextUtils.isEmpty(token)){
            Intent intent=new Intent(getActivity(),LoginActivity.class);
            intent.putExtra("user","UserFragment");
            startActivity(intent);
        }else {
            return true;
        }
        return false;
    }

    @OnClick({R.id.layout_my, R.id.layout_setting, R.id.feedback, R.id.layout_collection,
            R.id.layout_Integral, R.id.laout_Money,R.id.invite,R.id.tv_vip,R.id.layout_attention})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_my:
                if(getToken()){
                    ActivityUtils.startActivity(UserInformationActivity.class);
                }
                break;
            case R.id.tv_vip:

                break;
            case R.id.layout_setting:
                if(getToken()){
                    ActivityUtils.startActivity(SafeSettingActivity.class);
                }
                break;
            case R.id.feedback:
                if(getToken()){
                    ActivityUtils.startActivity(FeedbackActivity.class);
                }
                break;
            case R.id.layout_collection:
                if(getToken()){
                    ActivityUtils.startActivity(CollectionActivity.class);
                }
                break;
            case R.id.layout_Integral:
                if(getToken()){
                    startActivityForResult(new Intent(getActivity(),IntegralActivity.class),200);
                }
                break;
            case R.id.laout_Money:
                if(getToken()){
                    startActivityForResult(new Intent(getActivity(),CashActivity.class),200);
                }

                break;
            case R.id.invite:
                if(getToken()){
                    startActivityForResult(new Intent(getActivity(),InviteFriendsActivity.class).putExtra("nick",tvNick.getText().toString()),200);
                }
                break;
            case R.id.layout_attention:
                wechat_dialog = new Wechat_dialog(getActivity());
                wechat_dialog.setYesOnclickListener("去微信", new Wechat_dialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        wechat_dialog.dismiss();
                       if(isWeiringAvailable(getActivity())){
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

    public static boolean isWeiringAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        // 获取所有已安装程序的包信息
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            getUserInfo();
        }
    }

}
