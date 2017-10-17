package cn.com.stableloan.ui.activity.integarl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.SaveBean;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.activity.HtmlActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.SafeActivity;
import cn.com.stableloan.ui.activity.UpdatePassWordActivity;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.ui.activity.settingdate.DeviceActivity;
import cn.com.stableloan.ui.activity.settingdate.SwitchPassWordActivity;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.WaitTimeUtils;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.view.dialog.SelfDialog;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

public class SafeSettingActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.st_VersionCode)
    SuperTextView stVersionCode;
    @Bind(R.id.sv_ChangePW)
    SuperTextView svChangePW;
    @Bind(R.id.sv_ChangeGesture)
    SuperTextView svChangeGesture;
    @Bind(R.id.sv_UnLockLogin)
    SuperTextView svUnLockLogin;
    @Bind(R.id.sv_UnLockApply)
    SuperTextView svUnLockApply;
    @Bind(R.id.sv_UnLockCat)
    SuperTextView svUnLockCat;
    @Bind(R.id.sv_Terminal)
    SuperTextView svTerminal;
    @Bind(R.id.sv_DateTime)
    SuperTextView svDateTime;
    @Bind(R.id.sv_SafeNurse)
    SuperTextView svSafeNurse;
    @Bind(R.id.sv_QuitLogin)
    SuperTextView svQuitLogin;
    private SaveBean saveBean;
    private String[] managedList;
    private Context mContext;
    private ACache aCache;

    private static final int REQUEST_CODE=110;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SafeSettingActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting_activty);
        ButterKnife.bind(this);
        mContext=this;
        aCache = ACache.get(this);

        initToolbar();
        initDate();
        getDate();
        setListener();

    }

    private void initToolbar() {

        titleName.setText("安全设置");
    }

    private void setListener() {


        svDateTime.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                if (WaitTimeUtils.isFastDoubleClick()) {
                    return;
                }else {
                    Intent intent=new Intent(mContext,SafeActivity.class);
                    intent.putExtra("save",saveBean);
                    startActivityForResult(intent,REQUEST_CODE);
                }
            }
        });

        svQuitLogin.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                exit();
            }
        });
        svTerminal.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                DeviceActivity.launch(SafeSettingActivity.this);
            }
        });
        //修改密码
        svChangePW.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                AppApplication.addDestoryActivity(SafeSettingActivity.this,"SafeSetting");

                startActivity(new Intent(SafeSettingActivity.this, Verify_PasswordActivity.class).putExtra("from","updatePassword"));

            }
        });
        /**
         * 手势密码
         */
        svChangeGesture.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                SwitchPassWordActivity.launch(mContext);
            }
        });
        svSafeNurse.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                startActivity(new Intent(mContext, HtmlActivity.class).putExtra("safe","#/minTips"));
            }
        });
    }

    private void initDate() {
        String versionName = getVersionCode(this);
        stVersionCode.setRightString("v"+versionName);
        managedList = getResources().getStringArray(R.array.times);

    }

    public String getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionName+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private void getDate() {
            String token = (String) SPUtils.get(this, "token", "1");
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
                                    String managed = saveBean.getManaged();
                                    if (managed != null && managed.length() == 1) {
                                        int i = Integer.parseInt(managed);

                                        svDateTime.setRightString(managedList[i]);
                                    }
                                    if (saveBean.getPeriod().length() < 2) {
                                        svDateTime.setLeftBottomString("自动清档时间:无数据");
                                        svDateTime.setRightString("无数据");
                                    } else {
                                        svDateTime.setLeftBottomString("自动清档时间:"+saveBean.getPeriod());
                                    }
                                } else {
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    });


        }
    private  SelfDialog selfDialog;
    private void exit() {
        final TinyDB tinyDB = new TinyDB(this);
        UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
        String userphone = user.getUserphone();

        selfDialog = new SelfDialog(this);
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfDialog.dismiss();
                SPUtils.clear(SafeSettingActivity.this);
                TinyDB tinyDB = new TinyDB(SafeSettingActivity.this);
                tinyDB.clear();
                startActivity(new Intent(SafeSettingActivity.this, LoginActivity.class).putExtra("from", "user2"));
                finish();
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


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            switch (resultCode){
                case  Urls.SettingResultCode.SAFE_DATE:
                    String month = data.getStringExtra("month");
                    String period = data.getStringExtra("time");
                    saveBean.setPeriod(period);
                    String var = "";
                    for (int i = 0; i < managedList.length; i++) {
                        if (managedList[i].equals(month)) {
                            var = String.valueOf(i);
                        }
                    }
                    saveBean.setManaged(var);
                    svDateTime.setRightString(month);
                    svDateTime.setLeftBottomString("自动清档时间:"+period);
                    break;


            }
        }
    }
}
