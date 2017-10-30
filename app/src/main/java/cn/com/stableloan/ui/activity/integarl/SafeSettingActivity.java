package cn.com.stableloan.ui.activity.integarl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mancj.slideup.SlideUp;

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
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.ui.activity.settingdate.DeviceActivity;
import cn.com.stableloan.ui.activity.settingdate.SwitchPassWordActivity;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
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
    @Bind(R.id.cat_SlideImage)
    RelativeLayout catSlideImage;
    @Bind(R.id.RB_NO)
    RadioButton RBNO;
    @Bind(R.id.RB_PW)
    RadioButton RBPW;
    @Bind(R.id.RB_GE)
    RadioButton RBGE;
    @Bind(R.id.slide_title)
    TextView slideTitle;
    @Bind(R.id.apply_title)
    TextView applyTitle;
    @Bind(R.id.apply_NO)
    RadioButton applyNO;
    @Bind(R.id.apply_PW)
    RadioButton applyPW;
    @Bind(R.id.apply_GE)
    RadioButton applyGE;
    @Bind(R.id.apply_ViewImage)
    LinearLayout applyViewImage;
    @Bind(R.id.login_NO)
    RadioButton loginNO;
    @Bind(R.id.login_PW)
    RadioButton loginPW;
    @Bind(R.id.login_GE)
    RadioButton loginGE;
    @Bind(R.id.login_ViewImage)
    LinearLayout loginViewImage;
    @Bind(R.id.login_SlideImage)
    RelativeLayout loginSlideImage;
    @Bind(R.id.apply_SlideImage)
    RelativeLayout applySlideImage;
    private SaveBean saveBean;
    private String[] managedList;
    private Context mContext;
    private ACache aCache;
    private UserBean user;
    private SlideUp cat_SlideUp;
    private SlideUp apply_SlideUp;
    private SlideUp login_SlideUp;
    private TinyDB tinyDB;
    private String userPhone;

    private static final int REQUEST_CODE = 110;
    private static final int TOKEN_FAIL=120;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SafeSettingActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting_activty);
        ButterKnife.bind(this);
        mContext = this;
        aCache = ACache.get(this);
        initToolbar();
        initDate();
        getDate();
        setListener();

    }

    private void initToolbar() {

        titleName.setText("安全设置");

        cat_SlideUp = new SlideUp.Builder(catSlideImage)
                .withListeners(new SlideUp.Listener.Slide() {
                    @Override
                    public void onSlide(float percent) {
                    }
                })
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
        apply_SlideUp = new SlideUp.Builder(applySlideImage)
                .withListeners(new SlideUp.Listener.Slide() {
                    @Override
                    public void onSlide(float percent) {
                    }
                })
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
        login_SlideUp = new SlideUp.Builder(loginSlideImage)
                .withListeners(new SlideUp.Listener.Slide() {
                    @Override
                    public void onSlide(float percent) {
                    }
                })
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();

/*
        RBNO.setChecked(true);
*/

    }

    private void setListener() {

        svDateTime.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                if (WaitTimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    Intent intent = new Intent(mContext, SafeActivity.class);
                    intent.putExtra("save", saveBean);
                    startActivityForResult(intent, REQUEST_CODE);
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
                AppApplication.addDestoryActivity(SafeSettingActivity.this, "SafeSetting");

                startActivity(new Intent(SafeSettingActivity.this, Verify_PasswordActivity.class).putExtra("from", "updatePassword"));

            }
        });
        /**
         * 手势密码
         */
        svChangeGesture.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                //SwitchPassWordActivity.launch(mContext);
                startActivityForResult(new Intent(mContext,SwitchPassWordActivity.class),REQUEST_CODE);

            }
        });
        //贴心小护士
        svSafeNurse.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                startActivity(new Intent(mContext, HtmlActivity.class).putExtra("safe", "#/minTips"));
            }
        });

        //资料查看
        svUnLockCat.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                cat_SlideUp.show();

            }
        });
        //登陆方式
        svUnLockLogin.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                    login_SlideUp.show();
            }
        });
        //申请方式
        svUnLockApply.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                    apply_SlideUp.show();
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

        String userphone = (String) SPUtils.get(this, Urls.lock.USER_PHONE, "1");

        tinyDB=new TinyDB(this);

        user = (UserBean) tinyDB.getObject(userphone, UserBean.class);

        userPhone = user.getUserphone();

        String gesturePassword = aCache.getAsString(userphone);

        if(gesturePassword!=null){
            RBGE.setEnabled(true);
            loginGE.setEnabled(true);
            applyGE.setEnabled(true);
        }else {
            RBGE.setEnabled(false);
            applyGE.setEnabled(false);
            loginGE.setEnabled(false);
        }
        int cat = (int) SPUtils.get(this,userPhone+Urls.lock.CAT, 0);

        switch (cat) {
            case Urls.lock.NO_VERIFICATION:
                svUnLockCat.setRightString("无验证");
                RBNO.setChecked(true);
                break;
            case Urls.lock.PW_VERIFICATION:
                svUnLockCat.setRightString("密码验证");
                RBPW.setChecked(true);
                break;
            case Urls.lock.GESTURE_VERIFICATION:
                svUnLockCat.setRightString("手势验证");
                if (gesturePassword == null) {
                    RBGE.setEnabled(false);
                } else {
                    RBGE.setChecked(true);
                }
                break;
        }
        int apply = (int) SPUtils.get(this, userPhone+Urls.lock.APPLY, 0);
        switch (apply) {
            case Urls.lock.NO_VERIFICATION:
                svUnLockApply.setRightString("无验证");
                applyNO.setChecked(true);
                break;
            case Urls.lock.PW_VERIFICATION:
                svUnLockApply.setRightString("密码验证");
                applyPW.setChecked(true);
                break;
            case Urls.lock.GESTURE_VERIFICATION:
                svUnLockApply.setRightString("手势验证");
                if (gesturePassword == null) {
                    applyGE.setEnabled(false);
                } else {
                    applyGE.setChecked(true);
                }
                break;
        }
        int login = (int) SPUtils.get(this, userPhone+Urls.lock.LOGIN, 6);
        switch (login) {
            case Urls.lock.NO_VERIFICATION:
                svUnLockLogin.setRightString("无验证");
                loginNO.setChecked(true);
                break;
            case Urls.lock.PW_VERIFICATION:
                svUnLockLogin.setRightString("密码验证");

                loginPW.setChecked(true);
                break;
            case Urls.lock.GESTURE_VERIFICATION:
                svUnLockLogin.setRightString("手势验证");

                if (gesturePassword == null) {
                    loginGE.setEnabled(false);
                } else {
                    loginGE.setChecked(true);
                }
                break;
            default:
                loginPW.setChecked(true);
                break;
        }



        if (gesturePassword != null && !"".equals(gesturePassword)) {
            svChangeGesture.setRightString("已设置");
        } else {
            svChangeGesture.setRightString("未设置");
        }


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

                                String token = saveBean.getToken();

                                if(token!=null&&"0".equals(token)){
                                    Intent intent=new Intent(mContext,LoginActivity.class);
                                    intent.putExtra("message","登陆失效,请重新登陆");
                                    intent.putExtra("from","SafeDate");
                                    startActivityForResult(intent,REQUEST_CODE);
                                }else {
                                    String managed = saveBean.getManaged();
                                    if (managed != null && managed.length() == 1) {
                                        int i = Integer.parseInt(managed);
                                        svDateTime.setRightString(managedList[i]);
                                    }
                                    if (saveBean.getPeriod().length() < 2) {
                                        svDateTime.setLeftBottomString("自动清档时间:无数据");
                                    } else {
                                        svDateTime.setLeftBottomString("自动清档时间:" + saveBean.getPeriod());
                                    }
                                }
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });


    }

    private SelfDialog selfDialog;

    private void exit() {


        selfDialog = new SelfDialog(this);
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfDialog.dismiss();
                SPUtils.remove(mContext,Urls.lock.TOKEN);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case Urls.SettingResultCode.SAFE_DATE:
                    getDate();
                    break;
                case 100:
                    int lock = data.getIntExtra("lock", 0);
                    if(lock==1){
                        svChangeGesture.setRightString("未设置");
                        if(RBGE.isChecked()){
                            RBGE.setChecked(false);
                            RBNO.setChecked(true);
                            svUnLockCat.setRightString("无验证");

                        }
                        if(loginGE.isChecked()){
                            loginGE.setChecked(false);
                            loginPW.setChecked(true);
                            svUnLockLogin.setRightString("密码验证");
                        }
                        if(applyGE.isChecked()){
                            applyGE.setChecked(false);
                            applyNO.setChecked(true);
                            svUnLockApply.setRightString("无验证");
                        }
                        RBGE.setEnabled(false);
                        loginGE.setEnabled(false);
                        applyGE.setEnabled(false);
                    }else if(lock==2){
                        svChangeGesture.setRightString("已设置");
                        RBGE.setEnabled(true);
                        loginGE.setEnabled(true);
                        applyGE.setEnabled(true);
                    }
                    break;
                case TOKEN_FAIL:
                        int token = data.getIntExtra(Urls.TOKEN, 0);
                        if(token==1){
                            getDate();
                        }

                    break;


            }
        }
    }

    @OnClick({R.id.RB_NO, R.id.RB_PW, R.id.RB_GE, R.id.image_Visiable
            , R.id.iv_back, R.id.Apply_Visiable, R.id.login_NO, R.id.login_PW,
            R.id.login_GE, R.id.login_image_Visiable, R.id.apply_NO, R.id.apply_PW, R.id.apply_GE, R.id.apply_ViewImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //查看个人资料
            case R.id.RB_NO:
                SPUtils.put(this, userPhone+Urls.lock.CAT, Urls.lock.NO_VERIFICATION);
                svUnLockCat.setRightString("无验证");
                cat_SlideUp.hide();
                break;
            case R.id.RB_PW:
                    SPUtils.put(this, userPhone+Urls.lock.CAT, Urls.lock.PW_VERIFICATION);
                svUnLockCat.setRightString("密码验证");
                cat_SlideUp.hide();
                break;
            case R.id.RB_GE:
                String gesturePassword = aCache.getAsString(userPhone);
                if (gesturePassword == null) {
                    ToastUtils.showToast(this, "请先设置手势密码");
                } else {
                        SPUtils.put(this, userPhone+Urls.lock.CAT, Urls.lock.GESTURE_VERIFICATION);
                    svUnLockCat.setRightString("手势验证");
                    cat_SlideUp.hide();
                }
                break;
            case R.id.image_Visiable:
                cat_SlideUp.hide();
                break;
            case R.id.apply_NO:
                SPUtils.put(this, userPhone+Urls.lock.APPLY, Urls.lock.NO_VERIFICATION);
                svUnLockApply.setRightString("无验证");
                apply_SlideUp.hide();

                break;
            case R.id.apply_PW:
                SPUtils.put(this, userPhone+Urls.lock.APPLY, Urls.lock.PW_VERIFICATION);
                svUnLockApply.setRightString("密码验证");
                apply_SlideUp.hide();
                break;
            case R.id.apply_GE:
                String gesturePassword2 = aCache.getAsString(userPhone);
                if (gesturePassword2 == null) {
                    ToastUtils.showToast(this, "请先设置手势密码");
                } else {
                        SPUtils.put(this, userPhone+Urls.lock.APPLY, Urls.lock.GESTURE_VERIFICATION);
                    svUnLockApply.setRightString("手势验证");
                    apply_SlideUp.hide();
                }

                break;
            case R.id.Apply_Visiable:
                apply_SlideUp.hide();
                break;
            case R.id.login_NO:
                    SPUtils.put(this, userPhone+Urls.lock.LOGIN, Urls.lock.NO_VERIFICATION);
                svUnLockLogin.setRightString("无验证");
                login_SlideUp.hide();

                break;
            case R.id.login_PW:
                SPUtils.put(this, userPhone+Urls.lock.LOGIN, Urls.lock.PW_VERIFICATION);
                svUnLockLogin.setRightString("密码验证");
                login_SlideUp.hide();
                break;
            case R.id.login_GE:
                String gesturePassword3 = aCache.getAsString(userPhone);
                if (gesturePassword3 == null) {
                    ToastUtils.showToast(this, "请先设置手势密码");
                } else {
                        SPUtils.put(this, userPhone+Urls.lock.LOGIN, Urls.lock.GESTURE_VERIFICATION);
                    svUnLockLogin.setRightString("手势验证");
                    login_SlideUp.hide();
                }

                break;
            case R.id.login_image_Visiable:
                login_SlideUp.hide();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (cat_SlideUp.isVisible()) {
            cat_SlideUp.hide();
        } else if(apply_SlideUp.isVisible()){
            apply_SlideUp.hide();
        }else if(login_SlideUp.isVisible()){
            login_SlideUp.hide();
        }else {
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }

}
