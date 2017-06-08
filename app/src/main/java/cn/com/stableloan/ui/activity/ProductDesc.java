package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.bean.PlarformInfo;
import cn.com.stableloan.bean.UserBean;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

public class ProductDesc extends BaseActivity {


    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private String   pid;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProductDesc.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desc);
        ButterKnife.bind(this);
        initToolbar();
        pid = getIntent().getStringExtra("pid");
        if(pid!=null){
            LogUtils.i("ProductDesc",pid);
        }


    }

    private void initToolbar() {
        ivBack.setVisibility(View.VISIBLE);
        titleName.setText("产品详情");

    }


    @OnClick({R.id.iv_back, R.id.platform_desc, R.id.apply,R.id.ic_strategy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.platform_desc:
                startActivity(new Intent(this,PlatformInfoActivity.class).putExtra("pid",pid));
                break;
            case R.id.ic_strategy:
                startActivity(new Intent(this,StrateActivity.class).putExtra("pid",pid));
                break;
            case R.id.apply:
                HtmlActivity.launch(this);
                break;
        }
    }

}
