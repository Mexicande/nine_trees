package cn.com.stableloan.ui.activity.integarl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.ui.activity.FeedbackActivity;

public class RuleDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_desc);
        ButterKnife.bind(this);
        ImmersionBar.with(this).init();

    }

    @OnClick({R.id.layout_close, R.id.feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_close:
                finish();
                break;
            case R.id.feedback:
                FeedbackActivity.launch(this);
                break;
        }
    }
}