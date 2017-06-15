package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.Class_ListProductBean;
import cn.com.stableloan.model.Product_DescBean;
import cn.com.stableloan.ui.adapter.SuperTextAdapter;
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

    @Bind(R.id.space)
    Space space;
    @Bind(R.id.ic_strategy)
    TextView icStrategy;
    @Bind(R.id.platform_desc)
    TextView platformDesc;
    @Bind(R.id.min_algorithm)
    TextView minAlgorithm;

    @Bind(R.id.flow)
    RecyclerView flowRecyclerView;
    @Bind(R.id.tv_rate)
    TextView tvRate;
    @Bind(R.id.product_details)
    TextView productDetails;
    @Bind(R.id.product_logo)
    ImageView productLogo;
    @Bind(R.id.tv_pname)
    TextView tvPname;
    @Bind(R.id.product_introduction)
    TextView productIntroduction;
    @Bind(R.id.layout3)
    RelativeLayout layout3;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.tv_limt)
    TextView tvLimt;
    @Bind(R.id.min_max)
    TextView minMax;
    @Bind(R.id.layout4)
    RelativeLayout layout4;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.average_time)
    TextView averageTime;
    @Bind(R.id.crowd)
    TextView crowd;
    @Bind(R.id.linla)
    LinearLayout linla;
    @Bind(R.id.apply)
    Button apply;
    @Bind(R.id.layoutgo)
    RelativeLayout layoutgo;
    private String pid;
    private Product_DescBean descBean;


    private KProgressHUD hud;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProductDesc.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);

        ButterKnife.bind(this);
        initToolbar();
        pid = getIntent().getStringExtra("pid");
        if (pid != null) {
            LogUtils.i("ProductDesc", pid);
            getProductDate(pid);
        }

    }


    private void getProductDate(String id) {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(true)
                .show();
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
                                String success = object.getString("isSuccess");
                                if (success.equals("1")) {
                                    Gson gson = new Gson();
                                    descBean = gson.fromJson(s, Product_DescBean.class);
                                    if (descBean != null) {
                                        dateInset(descBean);
                                    }
                                } else {
                                    ToastUtils.showToast(ProductDesc.this, "网络异常,请检查网络连接");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        hud.dismiss();

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        hud.dismiss();

                    }
                });
    }

    private String substringmin = "";
    private String substringmax = "";

    private SuperTextAdapter superTextAdapter;

    private void dateInset(Product_DescBean descBean) {
        /*DescRecyler.setLayoutManager(new LinearLayoutManager(this));

        Classify_Recycler_Adapter classify_recycler_adapte=new Classify_Recycler_Adapter(null);

        DescRecyler.setAdapter(classify_recycler_adapte);

        View view = getLayoutInflater().inflate(R.layout.scroview, null);
        DescRecyler.addView(view);

        flowRecyclerView = (RecyclerView) view.findViewById(R.id.flow);
        */


        List<Class_ListProductBean.ProductBean.LabelsBean> lables = descBean.getProduct().getLabels();
        flowRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        superTextAdapter = new SuperTextAdapter(null);
        flowRecyclerView.setAdapter(superTextAdapter);
        superTextAdapter.addData(lables);

        Product_DescBean.ProductBean product = descBean.getProduct();
        Glide.with(this).load(product.getProduct_logo()).crossFade().into(productLogo);

        averageTime.setText(product.getAverage_time());

        tvPname.setText(product.getPname());
        productIntroduction.setText(product.getProduct_introduction());
        String minAl = product.getMin_algorithm();
        minAlgorithm.setText(minAl + "%");
        String product_crowd = product.getCrowd();
        String product_review = product.getReview();
        String product_actual_account = product.getActual_account();
        String product_repayment = product.getRepayment();
        String product_repayment_channels = product.getRepayment_channels();
        String min = product.getMin_algorithm();
        String max = product.getMax_algorithm();
        String product_prepayment = product.getPrepayment();
        String minimum_amount = product.getMinimum_amount();
        String maximum_amount = product.getMaximum_amount();
        tvRate.setText("参考" + product.getInterest_algorithm());

        if (minimum_amount.length() > 4) {
            substringmin = minimum_amount.substring(0, minimum_amount.length() - 4);
            substringmin = substringmin + "万";
        } else {
            substringmin = minimum_amount ;
        }
        if (maximum_amount.length() > 4) {
            substringmax = maximum_amount.substring(0, maximum_amount.length() - 4);
            substringmax = substringmax + "万";
        } else {
            substringmax = maximum_amount;
        }
        minMax.setText(substringmin + "~" + substringmax);

        if (product_crowd != null) {
            crowd.setVisibility(View.VISIBLE);
            crowd.setText(" 面向人群:" + product_crowd);
        }
        if (product_review != null) {
            review.setVisibility(View.VISIBLE);

            review.setText(" 审核方式:" + product_review);
        }
        if (product_actual_account != null) {
            actualAccount.setVisibility(View.VISIBLE);

            actualAccount.setText(" 实际到账:" + product_actual_account);
        }
        if (product_repayment != null) {
            repayment.setVisibility(View.VISIBLE);

            repayment.setText(" 还款方式:" + product_repayment);
        }
        if (product_repayment_channels != null) {
            repaymentChannels.setVisibility(View.VISIBLE);

            repaymentChannels.setText(" 还款渠道:" + product_repayment_channels);

        }
        if (min != null && max != null) {
            interestAlgorithm.setVisibility(View.VISIBLE);
            interestAlgorithm.setText(" 利息算法:" + product.getInterest_algorithm());
        }

        if (product_prepayment != null) {
            prepayment.setVisibility(View.VISIBLE);
            prepayment.setText(" 提前还款:" + product_prepayment);
        }
        if (product.getProduct_details() != null) {
            String details = product.getProduct_details();
            String aaa = details.replace("aaa", "\n");
            productDetails.setText(aaa);
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
                startActivity(new Intent(this, HtmlActivity.class).putExtra("Strate", descBean));
                break;
            case R.id.apply:
                Boolean login = (Boolean) SPUtils.get(this, "login", false);
                if (!login) {
                    LoginActivity.launch(this);
                } else {
                    startActivity(new Intent(this, HtmlActivity.class).putExtra("product", descBean));
                }
                break;
        }
    }

}
