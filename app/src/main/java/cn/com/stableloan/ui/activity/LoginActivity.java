package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 登录注册页
 */

public class LoginActivity extends BaseActivity {
    /* @Bind(R.id.nts)
     NavigationTabStrip nts;*/
    @Bind(R.id.nts)
    TabLayout nts;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_lock)
    EditText etLock;
    @Bind(R.id.bt_getCode)
    Button btGetCode;
    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.register_button)
    Button registerButton;
    @Bind(R.id.tv_forget)
    TextView tvForget;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        etLock.addTextChangedListener(watcher);
        etPhone.addTextChangedListener(watcher1);

        nts.addTab(nts.newTab().setText("密码登陆"));
        nts.addTab(nts.newTab().setText("短信登陆"));
        nts.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getSwitch(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
       /* nts.setTitles("短信登陆","密码登陆");
        nts.setTabIndex(0, true);*/


    }

    private void getSwitch(int position) {
            switch (position){
                case 0:
                    etLock.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                    etPhone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                    etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                    etLock.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                    btGetCode.setVisibility(View.GONE);
                    tvForget.setVisibility(View.VISIBLE);
                    etPhone.getText().clear();
                    etLock.getText().clear();
                    etPhone.setHint("手机号");
                    etLock.setHint("密码");
                    break;
                case 1:
                    etPhone.setHint("请输入手机号码");
                    etLock.setHint("验证码");
                    etPhone.getText().clear();
                    etLock.getText().clear();
                    etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});;
                    etLock.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});;
                    etLock.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                    etPhone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                    btGetCode.setVisibility(View.VISIBLE);
                    tvForget.setVisibility(View.GONE);
                    break;

            }

    }

    /**
     *密码
     */
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
                    if(!etPhone.getText().toString().isEmpty()){
                        loginButton.setEnabled(true);
                        loginButton.setBackgroundResource(R.drawable.login_button_up);
                        loginButton.setTextColor(getResources().getColor(R.color.white));

                    }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!etPhone.getText().toString().isEmpty()&&!etLock.getText().toString().isEmpty()){
                loginButton.setEnabled(true);
            }else {
                loginButton.setEnabled(false);

            }

        }
    };
    /**
     * 手机号
     *
     */
    private TextWatcher watcher1 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            if(!etLock.getText().toString().isEmpty()){
                loginButton.setEnabled(true);
                loginButton.setBackgroundResource(R.drawable.login_button_up);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!etPhone.getText().toString().isEmpty()&&!etLock.getText().toString().isEmpty()){
                loginButton.setEnabled(true);
                loginButton.setBackgroundResource(R.drawable.login_button_up);
                loginButton.setTextColor(getResources().getColor(R.color.white));

            }else {
                loginButton.setEnabled(false);

            }

        }
    };




    @OnClick({R.id.bt_getCode, R.id.login_button, R.id.register_button,R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_getCode:
                if(etPhone.getText().toString().isEmpty()){
                    ToastUtils.showToast(this,"请填写手机号码");
                }else if(etPhone.getText().toString().length()!=11){
                    ToastUtils.showToast(this,"手机号错误");
                }else {
                    getCodeMessage();


                }

                break;
            case R.id.login_button:
                 setLogin();

                break;
            case R.id.tv_forget:
                break;
            case R.id.register_button:
                    RegisterActivity.launch(this);
                 break;
        }
    }

    /**
     * 登陆
     *
     *
     */
    private void setLogin() {




    }

    /**
     * 获取验证码
     *
     */
    private void getCodeMessage() {
        OkGo.post(Urls.Login.SEND_MESSAGE)
                .tag(this)
                .params("userPhone",etPhone.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });
    }

}
