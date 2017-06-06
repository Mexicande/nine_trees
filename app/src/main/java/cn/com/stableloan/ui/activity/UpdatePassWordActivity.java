package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.utils.ToastUtils;

public class UpdatePassWordActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.up_password)
    EditText upPassword;
    @Bind(R.id.et_password)
    EditText etPassword;
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
        titleName.setText("密码修改");
        ivBack.setVisibility(View.VISIBLE);
        tvSave.setVisibility(View.VISIBLE);
    }




    @OnClick(R.id.iv_back)
    public void onViewClicked() {

        finish();
    }

}
