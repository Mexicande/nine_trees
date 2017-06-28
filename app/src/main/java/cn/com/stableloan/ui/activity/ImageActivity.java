package cn.com.stableloan.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;

public class ImageActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.spinner)
    NiceSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        titleName.setText("图片信息");
        ivBack.setVisibility(View.VISIBLE);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        spinner.attachDataSource(dataset);
    }

    @OnClick({R.id.identity, R.id.bank, R.id.CreditBank, R.id.camp, R.id.userCard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.identity:

                break;
            case R.id.bank:

                break;
            case R.id.CreditBank:

                break;

            case R.id.camp:

                break;
            case R.id.userCard:

                break;
        }
    }
}
