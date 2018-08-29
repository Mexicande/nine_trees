package cn.com.cashninetrees.ui.activity.integarl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.ui.activity.FeedbackActivity;

/**
 * @author apple
 */
public class RuleDescActivity extends AppCompatActivity {
    public static void launch(Context context) {
        context.startActivity(new Intent(context, RuleDescActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_desc);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();    }

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
