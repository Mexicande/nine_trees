package cn.com.stableloan.ui.activity;

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

public class UpdatePassWordActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass_word);
        ButterKnife.bind(this);
        titleName.setText("密码修改");
        ivBack.setVisibility(View.VISIBLE);
        tvSave.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.tv_save)
    public void onViewClicked() {


    }
}
