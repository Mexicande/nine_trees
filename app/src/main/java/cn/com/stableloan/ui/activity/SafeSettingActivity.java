package cn.com.stableloan.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.view.BetterSpinner;

public class SafeSettingActivity extends AppCompatActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_way)
    BetterSpinner etWay;
    @Bind(R.id.et_times)
    BetterSpinner etTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {

        titleName.setText("存储设置");
        ivBack.setVisibility(View.VISIBLE);

        String[] list = getResources().getStringArray(R.array.pass_change);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);
        etWay.setAdapter(adapter);

        String[] list1 = getResources().getStringArray(R.array.times);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list1);
        etTimes.setAdapter(adapter1);



    }

    @OnClick({R.id.iv_back, R.id.et_way, R.id.et_times})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.et_way:
                break;
            case R.id.et_times:
                break;
        }
    }

}
