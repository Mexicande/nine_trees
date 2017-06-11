package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
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
import cn.com.stableloan.model.Product_DescBean;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
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
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.product_logo)
    ImageView productLogo;
    @Bind(R.id.tv_pname)
    TextView tvPname;
    @Bind(R.id.product_introduction)
    TextView productIntroduction;
    @Bind(R.id.tv_limt)
    TextView tvLimt;
    @Bind(R.id.min_max)
    TextView minMax;
    @Bind(R.id.tv_rate)
    TextView tvRate;
    @Bind(R.id.min_algorithm)
    TextView minAlgorithm;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.average_time)
    TextView averageTime;
    @Bind(R.id.crowd)
    TextView crowd;
    @Bind(R.id.review)
    TextView review;
    @Bind(R.id.arrive)
    TextView arrive;
    @Bind(R.id.actual_account)
    TextView actualAccount;
    @Bind(R.id.repayment)
    TextView repayment;
    @Bind(R.id.repayment_channels)
    TextView repaymentChannels;
    @Bind(R.id.interest_algorithm)
    TextView interestAlgorithm;
    @Bind(R.id.prepayment)
    TextView prepayment;
    private String pid;
    private Product_DescBean descBean;
    private   ViewDataBinding dataBinding;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProductDesc.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_desc);
        ButterKnife.bind(this);
        initToolbar();
        pid = getIntent().getStringExtra("pid");
        if (pid != null) {
            LogUtils.i("ProductDesc", pid);
            getProductDate(pid);
        }

    }

    private void getProductDate(String id) {

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        final JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Urls.puk_URL + Urls.product.Productdetail)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            try {
                                JSONObject object = new JSONObject(s);
                                boolean isSuccess = object.getBoolean("isSuccess");
                                if (isSuccess) {
                                    Gson gson = new Gson();
                                    descBean = gson.fromJson(s, Product_DescBean.class);
                                    dataBinding.setVariable(BR.product,descBean);
                                    if(descBean!=null){
                                        dateInset(descBean);
                                    }
                                } else {
                                    ToastUtils.showToast(ProductDesc.this, "网络异常,请检查网络连接");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

    }

    private void dateInset(Product_DescBean descBean) {
        Product_DescBean.ProductBean product = descBean.getProduct();
        Glide.with(this).load(product.getProduct_logo()).crossFade().into(productLogo);
        String product_crowd = product.getCrowd();
        String product_review = product.getReview();
        String product_actual_account = product.getActual_account();
        String product_repayment = product.getRepayment();
        String product_repayment_channels = product.getRepayment_channels();
        String min = product.getMin_algorithm();
        String max = product.getMax_algorithm();
        String product_prepayment = product.getPrepayment();
        if(product_crowd!=null){
            this.crowd.setText("面向人群:"+product_crowd);
        }else if(product_review!=null){
            review.setText("审核人群:"+product_review);
        }else if(product_actual_account!=null){
            actualAccount.setText("实际到账金额:"+product_actual_account);
        }else if(product_repayment!=null){
            repayment.setText("还款方式:"+product_repayment);
        }else if(product_repayment_channels!=null){
            repaymentChannels.setText("还款渠道:"+product_repayment_channels);
        }else  if(min!=null&&max!=null){
            minMax.setText("利息算法:月息利息"+min+"-"+max);
        }else if(product_prepayment!=null){
            String or=null;
            switch (Integer.parseInt(product_prepayment)){
                case 0:
                    or="可以";
                    break;
                case 1:
                    or="不可以";
                    break;
                case 2:
                    or="其他";
                    break;
            }
            prepayment.setText("以前还款:"+or);
        }

    }

    private void initToolbar() {
        ivBack.setVisibility(View.VISIBLE);
        titleName.setText("产品详情");
    }

    @OnClick({R.id.iv_back, R.id.platform_desc, R.id.apply, R.id.ic_strategy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.platform_desc:
                startActivity(new Intent(this, PlatformInfoActivity.class).putExtra("pid", descBean.getProduct().getPl_id()));
                break;
            case R.id.ic_strategy:
                startActivity(new Intent(this, StrateActivity.class).putExtra("pid", pid));
                break;
            case R.id.apply:
                Boolean login = (Boolean) SPUtils.get(this, "login", false);
                if (!login) {
                    LoginActivity.launch(this);
                } else {
                    HtmlActivity.launch(this);
                }
                break;
        }
    }

}
