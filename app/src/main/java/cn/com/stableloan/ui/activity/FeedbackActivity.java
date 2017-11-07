package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.supertextview.SuperButton;
import cn.com.stableloan.view.update.AppUpdateUtils;
import okhttp3.Call;
import okhttp3.Response;

public class FeedbackActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.bt_login)
    SuperButton btLogin;

    private boolean falg = false;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, FeedbackActivity.class));
    }

    private static final int REQUEST_CODE=100;
    private static final int RESULT_CODE=110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        titleName.setText("提问&反馈&联系我们");
        ivBack.setVisibility(View.VISIBLE);
        setListener();
    }

    private void setListener() {
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etMessage.getText().toString().isEmpty()) {
                    btLogin.setEnabled(true);
                    btLogin.setUseShape();
                }else {
                    btLogin.setEnabled(false);
                    btLogin.setUseShape();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick({R.id.iv_back, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_login:
                sendMessageFeed();
                break;
        }
    }

    private void sendMessageFeed() {
        String message = etMessage.getText().toString();
        String userphone = (String) SPUtils.get(this, Urls.lock.USER_PHONE, "1");

        if (!TextUtils.isEmpty(message)) {
            String token = (String) SPUtils.get(this, Urls.TOKEN, "1");
            String versionName = AppUpdateUtils.getVersionName(this);
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindowManager().getDefaultDisplay().getRealSize(point);
            }
            DisplayMetrics displayMetrics = AppUpdateUtils.getDisplayMetrics(this);

            double x = Math.pow(point.x/ displayMetrics.xdpi, 2);
            double y = Math.pow(point.y / displayMetrics.ydpi, 2);
            double screenInches = Math.sqrt(x + y);
            DecimalFormat df = new DecimalFormat("0.0");
            String format = df.format(screenInches);
            HashMap<String, String> params = new HashMap<>();
                params.put("token", token);
                params.put("content", message);
                params.put("phone", userphone);
                params.put("sversion", versionName);
                params.put("browser", "android");
                params.put("screen", format);
                JSONObject jsonObject = new JSONObject(params);
                OkGo.<String>post(Urls.Ip_url + Urls.user.FEEDBACK)
                        .tag(this)
                        .upJson(jsonObject)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject object = new JSONObject(s);
                                    int error_code = object.getInt("error_code");
                                    if (error_code==0) {
                                        ToastUtils.showToast(FeedbackActivity.this, "提交成功");
                                        finish();
                                    }else if(error_code==2){
                                        Intent intent=new Intent(FeedbackActivity.this,LoginActivity.class);
                                        intent.putExtra("message",object.getString("error_message"));
                                        intent.putExtra("from","error_UserFragment");
                                        startActivityForResult(intent,REQUEST_CODE);
                                    } else{
                                        ToastUtils.showToast(FeedbackActivity.this, object.getString("error_message"));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

            } else {
                ToastUtils.showToast(this, "信息不能为空");
            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            switch (resultCode){
                case RESULT_CODE:
                    sendMessageFeed();
                    break;
            }
        }
    }
}
