package cn.com.stableloan.ui.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.MessageEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.activity.CreateGestureActivity;
import cn.com.stableloan.ui.activity.GestureLoginActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.MainActivity;
import cn.com.stableloan.ui.activity.Setting1Activity;
import cn.com.stableloan.ui.activity.UpdataProfessionActivity;
import cn.com.stableloan.ui.activity.UpdateNickActivity;
import cn.com.stableloan.ui.activity.UpdatePassWordActivity;
import cn.com.stableloan.ui.activity.UserInformationActivity;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.utils.constant.Constant;
import cn.com.stableloan.view.SelfDialog;
import de.greenrobot.event.ThreadMode;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
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

    private ACache aCache;
    private Bitmap splashBitmap;
    private int screenWidth, screenHeight;
    private Handler handler = new Handler(){};

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


    private void getUserInfo() {
        TinyDB tinyDB = new TinyDB(getActivity());
        UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
        if (user != null) {
            tvNick.setText(user.getNickname());
            tvUserPhone.setText(user.getUserphone());
        } else {
            String token = (String) SPUtils.get(getActivity(), "token", "1");
            if (!token.isEmpty()) {
                HashMap<String, String> params = new HashMap<>();
                params.put("token", token);
                JSONObject jsonObject = new JSONObject(params);
                OkGo.post(Urls.puk_URL + Urls.user.USERT_INFO)
                        .tag(this)
                        .upJson(jsonObject.toString())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                LogUtils.i("用户信息", s);
                                try {
                                    JSONObject object = new JSONObject(s);
                                    String success = object.getString("isSuccess");
                                    if (success.equals("1")) {
                                        Gson gson = new Gson();
                                        UserBean bean = gson.fromJson(s, UserBean.class);
                                        tvNick.setText(bean.getNickname());
                                        tvUserPhone.setText(bean.getUserphone());
                                    } else {
                                        String string = object.getString("msg");
                                        ToastUtils.showToast(getActivity(), string);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void exit() {
        selfDialog = new SelfDialog(getActivity());
        selfDialog.setTitle("提示");
        selfDialog.setMessage("确定退出登陆?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfDialog.dismiss();
                SPUtils.clear(getActivity());
                TinyDB tinyDB = new TinyDB(getActivity());
                tinyDB.clear();
                startActivityForResult(new Intent(getActivity(), LoginActivity.class).putExtra("from", "user1"), FLAG_LOGIN);
            }
        });
        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }


    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(false)
                .statusBarAlpha(0.3f)
                .navigationBarColor(R.color.colorStatus)
                .init();
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event){
        tvNick.setText(event.userNick);
        ToastUtils.showToast(getActivity(),"收到消息了--"+event.userNick);
    }


    @OnClick({R.id.layout_my, R.id.layout_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_my:
                String gesturePassword = aCache.getAsString(Constant.GESTURE_PASSWORD);
                if(gesturePassword == null || "".equals(gesturePassword)) {
                    Intent intent = new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from","userinformation");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), GestureLoginActivity.class);
                    startActivity(intent);
                }
                //UserInformationActivity.launch(getActivity());
                break;
            case R.id.layout_setting:
                Setting1Activity.launch(getActivity());
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
