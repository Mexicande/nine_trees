package cn.com.stableloan.ui.activity.vip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.ui.adapter.PayVipAdapter;
import cn.com.stableloan.utils.CommonUtil;
import cn.com.stableloan.utils.StatusBarUtil;
import cn.com.stableloan.view.ProductItemDecoration;

/**
 * @author apple
 */
public class VipActivity extends AppCompatActivity {

    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.pay_Recycler)
    RecyclerView payRecycler;
    private PayVipAdapter mPayVipAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(tvPrice.getText().toString());
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.blacktext));
        builder.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvPrice.setText(builder);
        ArrayList<String>list=new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        mPayVipAdapter=new PayVipAdapter(list);
        payRecycler.setLayoutManager(new GridLayoutManager(this,3));
        payRecycler.addItemDecoration(new ProductItemDecoration(3,50,true));

        payRecycler.setAdapter(mPayVipAdapter);
    }

    @OnClick({R.id.id_payAgreement, R.id.bt_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_payAgreement:
                break;
            case R.id.bt_open:
                break;
            default:
                break;
        }
    }
}
