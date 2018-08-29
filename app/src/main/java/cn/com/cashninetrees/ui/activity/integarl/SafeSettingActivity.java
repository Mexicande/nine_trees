package cn.com.cashninetrees.ui.activity.integarl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.api.ApiService;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.base.BaseActivity;
import cn.com.cashninetrees.bean.Login;
import cn.com.cashninetrees.interfaceutils.OnRequestDataListener;
import cn.com.cashninetrees.model.SaveBean;
import cn.com.cashninetrees.ui.activity.HtmlActivity;
import cn.com.cashninetrees.ui.activity.LoginActivity;
import cn.com.cashninetrees.ui.activity.SafeActivity;
import cn.com.cashninetrees.ui.activity.settingdate.DeviceActivity;
import cn.com.cashninetrees.utils.ActivityUtils;
import cn.com.cashninetrees.utils.SPUtil;
import cn.com.cashninetrees.utils.ToastUtils;
import cn.com.cashninetrees.utils.WaitTimeUtils;
import cn.com.cashninetrees.view.dialog.SelfDialog;
import cn.com.cashninetrees.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 安全设置
 * @author apple
 */

public class SafeSettingActivity extends BaseActivity   {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.st_VersionCode)
    SuperTextView stVersionCode;
    @Bind(R.id.sv_Terminal)
    SuperTextView svTerminal;
    @Bind(R.id.sv_SafeNurse)
    SuperTextView svSafeNurse;
    @Bind(R.id.sv_QuitLogin)
    SuperTextView svQuitLogin;
    private SaveBean saveBean;
    private String[] managedList;
    private Context mContext;
    private static final int REQUEST_CODE = 110;
    private static final int TOKEN_FAIL = 120;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SafeSettingActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting_activty);
        ButterKnife.bind(this);
        mContext = this;
        initToolbar();
        initDate();
        getDate();
        setListener();

    }

    private void initToolbar() {

        titleName.setText("安全设置");


    }

    private void setListener() {




        svQuitLogin.setOnSuperTextViewClickListener(superTextView -> exit());
        svTerminal.setOnSuperTextViewClickListener(superTextView -> DeviceActivity.launch(SafeSettingActivity.this));
        //贴心小护士
        svSafeNurse.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                Intent intent = new Intent(SafeSettingActivity.this, HtmlActivity.class);
                intent.putExtra("link", Urls.HTML_URL+"#/minTips");
                intent.putExtra("title", "安全小贴士");
                startActivity(intent);
            }
        });


    }



    private void initDate() {
        String versionName = getVersionCode(this);
        stVersionCode.setRightString("V" + versionName);
        managedList = getResources().getStringArray(R.array.times);
    }

    public String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionName + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private void getDate() {

        String token = SPUtil.getString(this, Urls.lock.TOKEN, "1");

        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        JSONObject jsonObject = new JSONObject(parms);

    }

    private SelfDialog selfDialog;

    private void exit() {


        selfDialog = new SelfDialog(this);
        selfDialog.setYesOnclickListener("确定", () -> {
            selfDialog.dismiss();
            SPUtil.clear(this);
            startActivity(new Intent(SafeSettingActivity.this, LoginActivity.class).putExtra("user", "user"));
            finish();
        });
        selfDialog.setNoOnclickListener("取消", () -> selfDialog.dismiss());
        selfDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case Urls.SettingResultCode.SAFE_DATE:
                    getDate();
                    break;
                case TOKEN_FAIL:
                    int token = data.getIntExtra(Urls.lock.TOKEN, 0);
                    if (token == 1) {
                        getDate();
                    }
                    break;
                 default:
                     break;


            }
        }
        if (requestCode == Urls.REQUEST_CODE.PULLBLIC_CODE) {
            getDate();
        }
    }

    @OnClick({
             R.id.iv_back
            })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /**
     * 刷新数据
     * @param event
     */
    @Subscribe
    public void onMessageEvent(Login event) {
        getDate();

    }

}

