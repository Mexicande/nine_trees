package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
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
import cn.com.stableloan.utils.EncryptUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cxy.com.validate.IValidateResult;
import cxy.com.validate.Validate;
import cxy.com.validate.ValidateAnimation;
import cxy.com.validate.annotation.Index;
import cxy.com.validate.annotation.NotNull;
import cxy.com.validate.annotation.Password1;
import cxy.com.validate.annotation.Password2;
import cxy.com.validate.annotation.RE;
import okhttp3.Call;
import okhttp3.Response;

public class UpdatePassWordActivity extends BaseActivity  implements IValidateResult {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_save)
    TextView tvSave;





    @Index(1)
    @NotNull(msg = "验证码不能为空！")
    @Bind(R.id.up_password)
    EditText upPassword;

    @Index(2)
    @NotNull(msg = "两次密码验证->密码一不为能空！")
    @RE(re = RE.number_letter_underline, msg = "密码格式不正确")
    @Password1()
    @Bind(R.id.et_password)
    EditText etPassword;

    @Index(3)
    @NotNull(msg = "两次密码验证->密码二不为能空！")
    @Password2(msg = "两次密码不一致！！！")
    @RE(re = RE.number_letter_underline, msg = "密码格式不正确")
    @Bind(R.id.et_Confirm_Password)
    EditText etConfirmPassword;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, UpdatePassWordActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass_word);
        ButterKnife.bind(this);
        Validate.reg(this);
        initToolbar();
    }

    private void initToolbar() {
        titleName.setText("密码修改");
        ivBack.setVisibility(View.VISIBLE);
        tvSave.setVisibility(View.VISIBLE);
        tvSave.setText("保存");

    }


    @OnClick({R.id.iv_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                Validate.check(UpdatePassWordActivity.this, UpdatePassWordActivity.this);

                break;
        }
    }

    private void updatewWord() {
        String token = (String) SPUtils.get(this, "token", "1");

        upPassword.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        etPassword.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        etConfirmPassword.setTransformationMethod(PasswordTransformationMethod
                .getInstance());

        final String oldWord = upPassword.getText().toString();

        final String newWord = etPassword.getText().toString();


        if(!oldWord.equals(newWord)){

            if(token!=null){
                String old = EncryptUtils.encryptMD5ToString(oldWord);
                String news = EncryptUtils.encryptMD5ToString(newWord);

                HashMap<String, String> params = new HashMap<>();
                params.put("oldpassword",old);
                params.put("password",news);
                params.put("token",token);
                params.put("status","0");
                JSONObject jsonObject = new JSONObject(params);
                OkGo.post(Urls.puk_URL+Urls.update.UPDATE_Word)
                        .tag(this)
                        .upJson(jsonObject.toString())
                        .execute( new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                LogUtils.i("密码修改",s);
                                try {
                                    JSONObject object=new JSONObject(s);
                                    boolean isSuccess = object.getBoolean("isSuccess");
                                    if(isSuccess){
                                        ToastUtils.showToast(UpdatePassWordActivity.this,"修改成功");
                                        finish();
                                    }else {
                                        String string = object.getString("msg");
                                        ToastUtils.showToast(UpdatePassWordActivity.this,string);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }else {
                ToastUtils.showToast(UpdatePassWordActivity.this,"系统异常,请稍后再试");
            }
        }else {
            ToastUtils.showToast(UpdatePassWordActivity.this,"新密码与原密码不能相同");

        }


    }

    @Override
    public void onValidateSuccess() {
            updatewWord();
    }



    @Override
    public void onValidateError(String msg, EditText editText) {
        if (editText != null)
            editText.setFocusable(true);
        ToastUtils.showToast(this,msg);
    }

    @Override
    public Animation onValidateErrorAnno() {
        return ValidateAnimation.horizontalTranslate();
    }
}
