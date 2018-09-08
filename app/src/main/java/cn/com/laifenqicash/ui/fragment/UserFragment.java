package cn.com.laifenqicash.ui.fragment;


import android.content.ClipboardManager;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.laifenqicash.R;
import cn.com.laifenqicash.api.ApiService;
import cn.com.laifenqicash.api.Urls;
import cn.com.laifenqicash.bean.Login;
import cn.com.laifenqicash.common.Constants;
import cn.com.laifenqicash.interfaceutils.OnRequestDataListener;
import cn.com.laifenqicash.model.integarl.Personal;
import cn.com.laifenqicash.ui.activity.CollectionActivity;
import cn.com.laifenqicash.ui.activity.FeedbackActivity;
import cn.com.laifenqicash.ui.activity.LoginActivity;
import cn.com.laifenqicash.ui.activity.integarl.SafeSettingActivity;
import cn.com.laifenqicash.ui.activity.vip.VipActivity;
import cn.com.laifenqicash.utils.ActivityUtils;
import cn.com.laifenqicash.utils.OnClickStatistics;
import cn.com.laifenqicash.utils.SPUtil;
import cn.com.laifenqicash.utils.ToastUtils;
import cn.com.laifenqicash.view.dialog.Wechat_dialog;
import cn.com.laifenqicash.view.supertextview.SuperTextView;

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

    private String token;
    private Wechat_dialog wechat_dialog;

    protected boolean isInit = false;
    protected boolean isLoad = false;

    public UserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
            }
        }
    }

    private void lazyLoad() {
        token = SPUtil.getString(getActivity(), Urls.lock.TOKEN);
        if (TextUtils.isEmpty(token)) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.putExtra("user", "user");
            startActivity(intent);
        }
    }


    private void getUserInfo() {
        token = SPUtil.getString(getActivity(), Urls.lock.TOKEN);
        if (getToken()) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Urls.lock.TOKEN, token);
                ApiService.GET_SERVICE(Urls.user.USERT_INFO, jsonObject, new OnRequestDataListener() {
                    @Override
                    public void requestSuccess(int code, JSONObject data) {
                        Gson gson = new Gson();
                        Personal personal = gson.fromJson(data.toString(), Personal.class);
                        if (personal.getError_code() == 0) {

                        } else {
                            ToastUtils.showToast(getActivity(), personal.getError_message());
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
        isInit = false;
        isLoad = false;
    }


    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(false)
                .statusBarAlpha(0.3f)
                .init();
    }

    private boolean getToken() {
        token = SPUtil.getString(getActivity(), Urls.lock.TOKEN);
        if (TextUtils.isEmpty(token)) {
            ActivityUtils.startActivity(LoginActivity.class);
        } else {
            return true;
        }
        return false;
    }

    @OnClick({ R.id.layout_setting, R.id.feedback, R.id.layout_collection,
            R.id.tv_vip, R.id.layout_attention, R.id.User_logo,R.id.vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.User_logo:
                getToken();
                break;
            case R.id.vip:
                OnClickStatistics.buriedStatistics(token, Constants.VIP_CLICK);
                ActivityUtils.startActivity(VipActivity.class);
                break;
            case R.id.tv_vip:
                OnClickStatistics.buriedStatistics(token, Constants.VIP_CLICK);
                ActivityUtils.startActivity(VipActivity.class);
                break;
            case R.id.layout_setting:
                ActivityUtils.startActivity(SafeSettingActivity.class);
                break;
            case R.id.feedback:
                if (getToken()) {
                    ActivityUtils.startActivity(FeedbackActivity.class);
                }
                break;
            case R.id.layout_collection:
                if (getToken()) {
                    ActivityUtils.startActivity(CollectionActivity.class);
                }
                break;

            case R.id.layout_attention:
                wechat_dialog = new Wechat_dialog(getActivity());
                wechat_dialog.setYesOnclickListener("去微信", new Wechat_dialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        wechat_dialog.dismiss();
                        if (isWeiringAvailable(getActivity())) {

                            ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            if (cmb != null) {
                                String wechat = SPUtil.getString(getActivity(), "wechat");
                                if(!TextUtils.isEmpty(wechat)){
                                    cmb.setText(wechat);
                                }else {
                                    cmb.setText("手机借款");
                                }
                            }

                            Intent intent = new Intent();
                            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                            intent.setAction(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_LAUNCHER);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setComponent(cmp);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        } else {
                            ToastUtils.showToast(getActivity(), "请先安装微信");
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
        if (requestCode == 200) {
            getUserInfo();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 刷新数据
     *
     * @param event
     */
    @Subscribe
    public void onMessageEvent(Login event) {
        token = SPUtil.getString(getActivity(), Urls.lock.TOKEN);
        getUserInfo();
    }
}
