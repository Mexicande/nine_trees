package cn.com.stableloan.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;

public class SafeActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_safe);
        ButterKnife.bind(this);
        titleName.setText("账号安全");
    }

    @OnClick({R.id.layout_go, R.id.safe, R.id.change_password, R.id.pattern})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_go:
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
