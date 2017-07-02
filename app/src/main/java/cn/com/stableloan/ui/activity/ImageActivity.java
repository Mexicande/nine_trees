package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;

public class ImageActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ImageActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {

        titleName.setText("图片信息");
    }

    @OnClick({R.id.identity, R.id.bank, R.id.CreditBank, R.id.camp, R.id.userCard,R.id.layout_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.identity:
                startActivity(new Intent(this, IdentityUploadActivity.class));
                break;
            case R.id.bank:
                break;
            case R.id.CreditBank:
                break;

            case R.id.camp:

                break;
            case R.id.userCard:
                break;
            case R.id.layout_go:
                finish();
                break;
        }
    }

}
