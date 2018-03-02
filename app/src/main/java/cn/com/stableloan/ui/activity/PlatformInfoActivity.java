package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.BR;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.Product_Detail;
import cn.com.stableloan.utils.LogUtils;
import okhttp3.Call;
import okhttp3.Response;

public class PlatformInfoActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.product_logo)
    ImageView productLogo;
    @Bind(R.id.summary)
    TextView summary;
    @Bind(R.id.online_time)
    TextView onlineTime;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, PlatformInfoActivity.class));
    }

    private ViewDataBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_platform_info);
        ButterKnife.bind(this);
        titleName.setText("平台详情");
        ivBack.setVisibility(View.VISIBLE);
        String pid = getIntent().getStringExtra("pid");
        if (pid != null) {
            LogUtils.i("PlatformInfoActivity", pid);
            HashMap<String, String> params = new HashMap<>();
            params.put("pl_id", pid);
            final JSONObject jsonObject = new JSONObject(params);
            OkGo.post(Urls.puk_URL + Urls.product.GetSlotdetail).tag(this)
                    .upJson(jsonObject.toString())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            if (s != null) {
                                try {
                                    JSONObject jsonObject1 = new JSONObject(s);
                                    String success = jsonObject1.getString("isSuccess");
                                    if (("1").equals(success)) {
                                        Gson gson = new Gson();
                                        Product_Detail product_detail = gson.fromJson(s, Product_Detail.class);
                                        dataBinding.setVariable(BR.product, product_detail);
                                        if (product_detail.getPlatform().getIntroduction() != null) {
                                            String details = product_detail.getPlatform().getIntroduction();
                                            String aaa = details.replace("aaa", "\n");
                                            summary.setText(aaa);

                                        }
                                        Glide.with(PlatformInfoActivity.this).load(product_detail.getPlatform().getLogo()).into(productLogo);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);

                        }
                    });

        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
