package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.utils.EncryptUtils;
import cn.com.stableloan.utils.RegexUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.editext.PowerfulEditText;
import cxy.com.validate.Validate;

public class UpdatePassWordActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_cancel)
    TextView ivCancel;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.prompting)
    TextView prompting;
    @Bind(R.id.et_passWord)
    PowerfulEditText etPassWord;
    @Bind(R.id.main)
    LinearLayout main;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, UpdatePassWordActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass_word);
        ButterKnife.bind(this);

        initToolbar();
        setListener();
    }

    private void setListener() {
     /*   main.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                Rect rect=new Rect();
                    //1.获取main在窗体的可视区域
                main.getWindowVisibleDisplayFrame(rect);
                //2.获取main在窗体的不可见视区域高度，在键盘没有弹起时
                //main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int hight = main.getRootView().getHeight() - rect.bottom;

                //3.不可见区域大于100；说明键盘弹出来了，
                if(hight>100){
                    int[] ints = new int[2];
                    //4.获取etPassWord的窗体坐标，算出main需要滚动的高度
                    etPassWord.getLocationInWindow(ints);
                    int i = (ints[1] + etPassWord.getHeight()) - rect.bottom;
                    // 5.让界面整体上移键盘的高度
                    main.scrollBy(0,i);
                }else {
                    //6.不可见区域小于100，说明键盘隐藏了，把界面下移，移动到原有高度
                    main.scrollBy(0,0);

                }
            }
        });*/

    }

    private void initToolbar() {
        titleName.setText("修改密码");
        ivCancel.setText("取消");
        tvSave.setText("下一步");


    }

    @OnClick({R.id.iv_cancel, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel:
                finish();
                break;
            case R.id.tv_save:
                String pw = etPassWord.getText().toString();
                if (!pw.isEmpty()) {
                    validationPW(pw);
                } else {
                    ToastUtils.showToast(this, "请输入新密码");
                }
                break;
        }
    }

    private void validationPW(String pw) {

        String md5ToString = EncryptUtils.encryptMD5ToString(pw);

        boolean passWord = RegexUtils.isPW(pw);
        String password = getIntent().getStringExtra("password");

        if (passWord) {
            if (password != null && !md5ToString.equals(password)) {
                AppApplication.addDestoryActivity(this, "password");
                startActivity(new Intent(this, UpdatePWActivity.class).putExtra("password", pw));
            } else {
                prompting.setText("请输入其他密码，不能再次使用旧密码");
            }
        } else {
            prompting.setText("密码格式不正确,请重新输入");
        }

    }
}
