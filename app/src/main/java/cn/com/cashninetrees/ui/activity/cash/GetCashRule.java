package cn.com.cashninetrees.ui.activity.cash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.base.BaseActivity;

public class GetCashRule extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.titleName)
    TextView titleName;
    @Bind(R.id.layout_ExtractCash)
    LinearLayout layoutExtractCash;
    @Bind(R.id.layout_getCash)
    LinearLayout layoutGetCash;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, GetCashRule.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_cash_rule);
        ButterKnife.bind(this);

        String from = getIntent().getStringExtra("from");
        if (from != null && "getCash".equals(from)) {
            titleName.setText("如何获取现金");
            layoutGetCash.setVisibility(View.VISIBLE);
        } else {
            titleName.setText("如何提取现金");
            layoutGetCash.setVisibility(View.GONE);
            layoutExtractCash.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
