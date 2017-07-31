package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.RoundButton;
import okhttp3.Call;
import okhttp3.Response;

public class FeedbackActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.et_message_phone)
    EditText etMessagePhone;
    @Bind(R.id.bt_login)
    RoundButton btLogin;

    private boolean falg = false;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, FeedbackActivity.class));
    }
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
        String phone = etMessagePhone.getText().toString();
        String message = etMessage.getText().toString();

        if (!phone.isEmpty() && !message.isEmpty()) {
            String token = (String) SPUtils.get(this, "token", "1");
            if (token != null) {
                HashMap<String, String> params = new HashMap<>();
                params.put("token", token);
                params.put("content", message);
                if(phone.isEmpty()){
                    params.put("phone", "");
                }else {
                    params.put("phone", phone);

                }
                JSONObject jsonObject = new JSONObject(params);

                OkGo.<String>post(Urls.Ip_url+ Urls.user.FEEDBACK)
                        .tag(this)
                        .upJson(jsonObject)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject object = new JSONObject(s);
                                    if ("0".equals(object.getString("error_code"))) {
                                        ToastUtils.showToast(FeedbackActivity.this, "提交成功");
                                        finish();
                                    } else {
                                        ToastUtils.showToast(FeedbackActivity.this, object.getString("error_message"));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

            } else {
                ToastUtils.showToast(this,"信息不能为空");
            }
        }
    }

}
