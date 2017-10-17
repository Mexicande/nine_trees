package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.utils.EncryptUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.RegexUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.editext.PowerfulEditText;
import okhttp3.Call;
import okhttp3.Response;

public class UpdatePWActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.prompting)
    TextView prompting;
    @Bind(R.id.et_passWord)
    PowerfulEditText etPassWord;
    @Bind(R.id.iv_cancel)
    TextView ivCancel;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, UpdatePWActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pw);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        titleName.setText("修改密码");
        ivCancel.setText("取消");
        tvSave.setText("完成");
    }

    @OnClick({R.id.iv_cancel, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel:
                finish();
                break;
            case R.id.tv_save:
                String pw = etPassWord.getText().toString();
                if(!pw.isEmpty()){
                    updatewWord(pw);
                }else {
                    ToastUtils.showToast(this,"密码不能为空");
                }
                break;
        }
    }


    private KProgressHUD hud;

    private void updatewWord(String password) {

        String pw = getIntent().getStringExtra("password");

        boolean PassWord = RegexUtils.isPW(password);

        if (PassWord) {
            if (pw!=null&&password.equals(pw)) {

                String md5ToString = EncryptUtils.encryptMD5ToString(password);

                changeApi(md5ToString);

            } else {
                prompting.setText("两次密码不一致");
            }
        } else {
            prompting.setText("密码格式不正确");
        }
    }


    private void  changeApi(String pw){
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("修改中.....")
                .setCancellable(true)
                .show();
        String token = (String) SPUtils.get(this, "token", "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("password", pw);
        params.put("token", token);
        JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Urls.NEW_Ip_url + Urls.update.UPDATE_Word)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        hud.dismiss();
                        try {
                            JSONObject object = new JSONObject(s);
                            int error_code = object.getInt("error_code");
                            if (error_code==0) {
                                ToastUtils.showToast(UpdatePWActivity.this, "修改成功");
                                AppApplication.destoryActivity("password");
                                AppApplication.destoryActivity("SafeSetting");
                                startActivity(new Intent(UpdatePWActivity.this, LoginActivity.class).putExtra("from", "user"));
                                finish();
                            } else {
                                hud.dismiss();
                                String string = object.getString("error_message");
                                ToastUtils.showToast(UpdatePWActivity.this, string);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        hud.dismiss();
                        ToastUtils.showToast(UpdatePWActivity.this, "网络异常，请检测网络");

                    }
                });
    }

}
