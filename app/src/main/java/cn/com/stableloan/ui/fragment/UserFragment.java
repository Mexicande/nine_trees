package cn.com.stableloan.ui.fragment;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.UpdataProfessionActivity;
import cn.com.stableloan.ui.activity.UpdateNickActivity;
import cn.com.stableloan.ui.activity.UpdatePassWordActivity;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.SelfDialog;
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
    @Bind(R.id.layout_word)
    RelativeLayout layoutWord;
    @Bind(R.id.layout_nick)
    RelativeLayout layoutNick;
    @Bind(R.id.layout_profession)
    RelativeLayout layoutProfession;
    @Bind(R.id.bt_exit)
    Button btExit;
    @Bind(R.id.version)
    TextView version;

    private SelfDialog selfDialog;

    private static final int FLAG_Profession = 1;
    private static final int SEND_Profession_ = 1000;


    private static final int FLAG_NICK = 2;
    private static final int SEND_NICK = 2000;
    private static final int FLAG_LOGIN = 3;
    private static final int SEND_LOGIN = 3000;

    private static final int Moon = 1;


    public UserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        getVerSion();
        Boolean login = (Boolean) SPUtils.get(getActivity(), "login", false);
        if (!login) {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class).putExtra("from", "user"), FLAG_LOGIN);
        } else {
            getUserInfo();
        }
        return view;
    }

    private void getVerSion() {
        String code = AppUtils.getAppVersionName();
        LogUtils.i("version",code);

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


    @OnClick({R.id.layout_word, R.id.layout_nick, R.id.layout_profession, R.id.bt_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_word://密码
                UpdatePassWordActivity.launch(getActivity());
                break;
            case R.id.layout_nick://昵称
                startActivityForResult(new Intent(getActivity(), UpdateNickActivity.class), FLAG_NICK);
                break;
            case R.id.layout_profession://职业
                startActivityForResult(new Intent(getActivity(), UpdataProfessionActivity.class), FLAG_Profession);
                break;
            case R.id.bt_exit: //退出登录
                exit();
                break;
        }
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
                startActivityForResult(new Intent(getActivity(), LoginActivity.class).putExtra("from", "user"), FLAG_LOGIN);
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
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(false)
                .navigationBarColor(R.color.colorStatus)
                .init();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FLAG_Profession:
                if (SEND_Profession_ == resultCode) {
                    String Identity = data.getStringExtra("HeadPhoto");
                    TinyDB tinyDB = new TinyDB(getActivity());
                    UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
                    user.setIdentity(Identity);
                    tinyDB.putObject("user", user);
                    LogUtils.i("修改后的", user);
                }
                break;
            //昵称设置
            case FLAG_NICK:
                if (SEND_NICK == resultCode) {
                    String nick = data.getExtras().getString("nick");
                    if (nick != null) {
                        TinyDB tinyDB = new TinyDB(getActivity());
                        UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
                        user.setNickname(nick);
                        tinyDB.putObject("user", user);
                        tvNick.setText(nick);
                    }
                }
            case FLAG_LOGIN:
                if (SEND_LOGIN == resultCode) {
                    UserBean user = (UserBean) data.getSerializableExtra("user");
                    tvNick.setText(user.getNickname());
                    tvUserPhone.setText(user.getUserphone());
                }

        }

    }

    /**
     * 头像职业选择
     *
     * @param imageView
     */
    private void setUserHead(int imageView) {
        Glide.with(getActivity()).load(imageView)
                .centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new CropCircleTransformation(getActivity())).into(UserLogo);

    }



}
