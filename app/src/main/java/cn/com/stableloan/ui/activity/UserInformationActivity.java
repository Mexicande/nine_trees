package cn.com.stableloan.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;

public class UserInformationActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.safe, R.id.change_password, R.id.pattern})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.safe:

                break;
            case R.id.change_password:

                break;
            case R.id.pattern:
                
                break;
        }
    }
}
