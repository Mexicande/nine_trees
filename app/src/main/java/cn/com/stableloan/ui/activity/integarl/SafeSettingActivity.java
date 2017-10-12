package cn.com.stableloan.ui.activity.integarl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.SaveBean;
import cn.com.stableloan.ui.activity.MainActivity;
import cn.com.stableloan.ui.activity.SafeActivity;
import cn.com.stableloan.ui.activity.SafeActivity11111;
import cn.com.stableloan.ui.activity.UpdatePassWordActivity;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

public class SafeSettingActivity extends AppCompatActivity {

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
        initDate();
        getDate();
        setListener();

    }

    private void setListener() {

        svChangePW.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                UpdatePassWordActivity.launch(mContext);
            }
        });
        svDateTime.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                SafeActivity.launch(mContext);
                Intent intent=new Intent(mContext,SafeActivity.class);
                intent.putExtra("save",saveBean);
                startActivityForResult(intent,REQUEST_CODE);
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

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }
}
