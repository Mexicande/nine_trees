package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;

public class CashActivity extends BaseActivity {

    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.alipay_name)
    TextView alipayName;
    @Bind(R.id.cash_recycler)
    RecyclerView cashRecycler;
    @Bind(R.id.bt_withdrawal)
    Button btWithdrawal;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, CashActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.cash_back, R.id.cash_data, R.id.bt_withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cash_back:
                break;
            case R.id.cash_data:
                break;
            case R.id.bt_withdrawal:
                break;
        }
    }
}
