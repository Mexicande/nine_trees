package cn.com.stableloan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.PicStatusEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.view.lock.LockPatternUtil;
import cn.com.stableloan.view.lock.LockPatternView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Sym on 2015/12/24.
 */
public class GestureLoginActivity extends BaseActivity {

    private static final String TAG = "LoginGestureActivity";

    @Bind(R.id.lockPatternView)
    LockPatternView lockPatternView;
    @Bind(R.id.messageTv)
    TextView messageTv;
    @Bind(R.id.forgetGestureBtn)
    Button forgetGestureBtn;
    @Bind(R.id.title_name)
    TextView titleName;

    private ACache aCache;
    private static final long DELAYTIME = 600l;
    private byte[] gesturePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_login);
        ButterKnife.bind(this);
        titleName.setText("身份验证");
        this.init();
    }

    private void init() {
        aCache = ACache.get(GestureLoginActivity.this);
        //得到当前用户的手势密码

        final TinyDB tinyDB = new TinyDB(this);
        UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
        String userphone = user.getUserphone();
        gesturePassword = aCache.getAsBinary(userphone);
        lockPatternView.setOnPatternListener(patternListener);
        updateStatus(Status.DEFAULT);
    }

    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            if (pattern != null) {
                if (LockPatternUtil.checkPattern(pattern, gesturePassword)) {
                    updateStatus(Status.CORRECT);
                } else {
                    updateStatus(Status.ERROR);
                }
            }
        }
    };

    /**
     * 更新状态
     *
     * @param status
     */
    private void updateStatus(Status status) {
        messageTv.setText(status.strId);
        messageTv.setTextColor(getResources().getColor(status.colorId));
        switch (status) {
            case DEFAULT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case ERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                break;
            case CORRECT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                loginGestureSuccess();
                break;
        }
    }

    /**
     * 手势登录成功（去首页）
     */
    private void loginGestureSuccess() {
        String from = getIntent().getStringExtra("from");
        if(from!=null){
            if(from.equals("SettingSafe")){
                String token = (String) SPUtils.get(this, "token", "1");
                Map<String,String> parms=new HashMap<>();
                parms.put("token",token);
                JSONObject jsonObject=new JSONObject(parms);
                OkGo.<String>post(Urls.NEW_URL+ Urls.Login.GET_SIGNATURE)
                        .tag(this)
                        .upJson(jsonObject)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject jsonObject1=new JSONObject(s);
                                    String isSuccess = jsonObject1.getString("isSuccess");
                                    if("1".equals(isSuccess)){
                                        String signature = jsonObject1.getString("signature");
                                        SPUtils.put(GestureLoginActivity.this,"signature",signature);
                                        SafeSettingActivity.launch(GestureLoginActivity.this);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }else if(from.equals("PicStatus")){

                String token = (String) SPUtils.get(this, "token", "1");
                Map<String,String> parms=new HashMap<>();
                parms.put("token",token);
                JSONObject jsonObject=new JSONObject(parms);
                OkGo.<String>post(Urls.NEW_URL+ Urls.Login.GET_SIGNATURE)
                        .tag(this)
                        .upJson(jsonObject)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject jsonObject1=new JSONObject(s);
                                    String isSuccess = jsonObject1.getString("isSuccess");
                                    if("1".equals(isSuccess)){
                                        String signature = jsonObject1.getString("signature");
                                        SPUtils.put(GestureLoginActivity.this,"signature",signature);
                                        EventBus.getDefault().post(new PicStatusEvent("update"));
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

            }
        } else if(from.equals("CardUpload")){
            String token = (String) SPUtils.get(this, "token", "1");
            Map<String,String> parms=new HashMap<>();
            parms.put("token",token);
            JSONObject jsonObject=new JSONObject(parms);
            OkGo.<String>post(Urls.NEW_URL+ Urls.Login.GET_SIGNATURE)
                    .tag(this)
                    .upJson(jsonObject)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject1=new JSONObject(s);
                                String isSuccess = jsonObject1.getString("isSuccess");
                                if("1".equals(isSuccess)){
                                    String signature = jsonObject1.getString("signature");
                                    SPUtils.put(GestureLoginActivity.this,"signature",signature);
                                    EventBus.getDefault().post(new InformationEvent("CardUpload"));
                                    finish();
                                 }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

        } else if(from.equals("UserInformation")){
            String token = (String) SPUtils.get(this, "token", "1");
            Map<String,String> parms=new HashMap<>();
            parms.put("token",token);
            JSONObject jsonObject=new JSONObject(parms);
            OkGo.<String>post(Urls.NEW_URL+ Urls.Login.GET_SIGNATURE)
                    .tag(this)
                    .upJson(jsonObject)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject1=new JSONObject(s);
                                String isSuccess = jsonObject1.getString("isSuccess");
                                if("1".equals(isSuccess)){
                                    String signature = jsonObject1.getString("signature");
                                    EventBus.getDefault().post(new InformationEvent("ok"));
                                    SPUtils.put(GestureLoginActivity.this,"signature",signature);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });



        }else  if(from.equals("bankinformation")){
            String token = (String) SPUtils.get(this, "token", "1");
            Map<String,String> parms=new HashMap<>();
            parms.put("token",token);
            JSONObject jsonObject=new JSONObject(parms);
            OkGo.<String>post(Urls.NEW_URL+ Urls.Login.GET_SIGNATURE)
                    .tag(this)
                    .upJson(jsonObject)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject1=new JSONObject(s);
                                String isSuccess = jsonObject1.getString("isSuccess");
                                if("1".equals(isSuccess)){
                                    String signature = jsonObject1.getString("signature");
                                    SPUtils.put(GestureLoginActivity.this,"signature",signature);
                                    EventBus.getDefault().post(new InformationEvent("bankinformation"));
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

        }else  {
            String token = (String) SPUtils.get(this, "token", "1");
            Map<String,String> parms=new HashMap<>();
            parms.put("token",token);
            JSONObject jsonObject=new JSONObject(parms);
            OkGo.<String>post(Urls.NEW_URL+ Urls.Login.GET_SIGNATURE)
                    .tag(this)
                    .upJson(jsonObject)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject1=new JSONObject(s);
                                String isSuccess = jsonObject1.getString("isSuccess");
                                if("1".equals(isSuccess)){
                                    String signature = jsonObject1.getString("signature");
                                    SPUtils.put(GestureLoginActivity.this,"signature",signature);
                                    UserInformationActivity.launch(GestureLoginActivity.this);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });



        }

        //Toast.makeText(GestureLoginActivity.this, "success", Toast.LENGTH_SHORT).show();
    }

    /**
     * 忘记手势密码（去账号登录界面）
     */
    @OnClick(R.id.forgetGestureBtn)
    void forgetGesturePasswrod() {
        String from = getIntent().getStringExtra("from");

        if(from.equals("SettingSafe")){
            Intent intent = new Intent(GestureLoginActivity.this, Verify_PasswordActivity.class).putExtra("from","safe");
            startActivity(intent);
            this.finish();
        }else if(from.equals("userinformation")){
            Intent intent = new Intent(GestureLoginActivity.this, Verify_PasswordActivity.class) .putExtra("from", "userinformation");
            startActivity(intent);
            this.finish();
        }else {
            Intent intent = new Intent(GestureLoginActivity.this, Verify_PasswordActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @OnClick(R.id.layout_go)
    public void onViewClicked() {
        finish();
    }

    private enum Status {
        //默认的状态
        DEFAULT(R.string.gesture_default, R.color.grey_a5a5a5),
        //密码输入错误
        ERROR(R.string.gesture_error, R.color.red_f4333c),
        //密码输入正确
        CORRECT(R.string.gesture_correct, R.color.grey_a5a5a5);

        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }
}
