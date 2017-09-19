package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
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
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.model.integarl.StatusBean;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

public class UserInformationActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.User_information)
    SuperTextView UserInformation;
    @Bind(R.id.User_Authorization)
    SuperTextView UserAuthorization;
    @Bind(R.id.User_Pic)
    SuperTextView UserPic;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, UserInformationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getStatus();
        initToolbar();
    }

    private void initToolbar() {
        ivBack.setVisibility(View.VISIBLE);
        titleName.setText("我的资料");
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("mymaterials", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//记录事件
        ZhugeSDK.getInstance().track(this, "我的资料", eventObject);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getStatus();
    }

    private void getStatus() {
        Map<String, String> parms = new HashMap<>();
        String token = (String) SPUtils.get(this, "token", "1");
        parms.put("token", token);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.Ip_url + Urls.user.USER_STATUS)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtils.i("Status--", s);
                        if (s != null) {
                            Gson gson = new Gson();
                            StatusBean statusBean = gson.fromJson(s, StatusBean.class);
                            if (statusBean.getError_code() == 0) {
                                StatusBean.DataBean data = statusBean.getData();
                                if ("1".equals(data.getStep1())) {
                                    Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_succeed);
                                    UserInformation.setTextBackground(drawable);
                                    UserInformation.setRightString("已完成");
                                }else {
                                    Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_fail);
                                    UserInformation.setTextBackground(drawable);
                                    UserInformation.setRightString("未完成");
                                }
                                if ("1".equals(data.getStep2())) {
                                    Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_succeed);
                                    UserAuthorization.setTextBackground(drawable);
                                    UserAuthorization.setRightString("已完成");
                                }else {
                                    Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_fail);
                                    UserAuthorization.setTextBackground(drawable);
                                    UserAuthorization.setRightString("未完成");
                                }
                                if ("1".equals(data.getStep3())) {
                                    Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_succeed);
                                    UserPic.setTextBackground(drawable);
                                    UserPic.setRightString("已完成");
                                }else {
                                    Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_fail);
                                    UserPic.setTextBackground(drawable);
                                    UserPic.setRightString("未完成");
                                }

                            } else {
                                ToastUtils.showToast(UserInformationActivity.this, statusBean.getError_message());
                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });

    }


    @OnClick({R.id.iv_back, R.id.User_information, R.id.User_Authorization, R.id.User_Pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.User_information:
                TinyDB tinyDB = new TinyDB(this);
                UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
                int identity = user.getIdentity();
                if (identity == 0) {
                    startActivity(new Intent(this, UpdataProfessionActivity.class).putExtra("from", "identity"));
                    //UpdataProfessionActivity.launch(this);
                } else {
                    IdentityinformationActivity.launch(this);
                }
                break;
            case R.id.User_Authorization:
                CertificationActivity.launch(this);
                break;
            case R.id.User_Pic:
                ImageActivity.launch(this);
                break;
        }
    }

    @Subscribe
    public void onMessageEvent(InformationEvent event) {
        String message = event.message;
        if ("informationStatus".equals(message)) {
            getStatus();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
