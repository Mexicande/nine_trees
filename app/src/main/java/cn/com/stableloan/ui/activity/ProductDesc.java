package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.android.databinding.library.baseAdapters.BR;
import com.bumptech.glide.Glide;
import com.coorchice.library.SuperTextView;
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
    @Bind(R.id.tv_rate)
    TextView tvRate;
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
    @Bind(R.id.label1)
    SuperTextView label1;
    @Bind(R.id.label2)
    SuperTextView label2;
    @Bind(R.id.label3)
    SuperTextView label3;
    @Bind(R.id.label4)
    SuperTextView label4;
    @Bind(R.id.label5)
    SuperTextView label5;
    @Bind(R.id.bel)
    LinearLayout bel;
    @Bind(R.id.apply)
    Button apply;
    @Bind(R.id.shengluehao)
    TextView shengluehao;
    @Bind(R.id.min_max)
    TextView minMax;
    @Bind(R.id.space)
    Space space;
    @Bind(R.id.ic_strategy)
    TextView icStrategy;
    @Bind(R.id.platform_desc)
    TextView platformDesc;
    @Bind(R.id.min_algorithm)
    TextView minAlgorithm;
    private String pid;
    private Product_DescBean descBean;
    private ViewDataBinding dataBinding;


    private KProgressHUD hud;

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
                                    dataBinding.setVariable(BR.product ,descBean);
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

    private  String substringmin="";
    private  String substringmax="";
    private void dateInset(Product_DescBean descBean) {

        List<Class_ListProductBean.ProductBean.LabelsBean> lables = descBean.getProduct().getLabels();
        int size = lables.size();
        switch (size) {
            case 0:
                break;
            case 1:
                label1.setVisibility(View.VISIBLE);
                label1.setTextColor(Color.parseColor(lables.get(0).getFont()));
                label1.setSolid(Color.parseColor(lables.get(0).getBackground()));
                label1.setStrokeColor(Color.parseColor(lables.get(0).getFont()));
                label1.setText(lables.get(0).getName());
                break;
            case 2:
                label1.setVisibility(View.VISIBLE);
                label1.setTextColor(Color.parseColor(lables.get(0).getFont()));
                label1.setSolid(Color.parseColor(lables.get(0).getBackground()));
                label1.setStrokeColor(Color.parseColor(lables.get(0).getFont()));
                label1.setText(lables.get(0).getName());

                label2.setVisibility(View.VISIBLE);
                label2.setTextColor(Color.parseColor(lables.get(1).getFont()));
                label2.setSolid(Color.parseColor(lables.get(1).getBackground()));
                label2.setStrokeColor(Color.parseColor(lables.get(1).getFont()));
                label2.setText(lables.get(1).getName());

                break;
            case 3:
                label1.setVisibility(View.VISIBLE);
                label1.setTextColor(Color.parseColor(lables.get(0).getFont()));
                label1.setSolid(Color.parseColor(lables.get(0).getBackground()));
                label1.setStrokeColor(Color.parseColor(lables.get(0).getFont()));
                label1.setText(lables.get(0).getName());

                label2.setVisibility(View.VISIBLE);
                label2.setTextColor(Color.parseColor(lables.get(1).getFont()));
                label2.setSolid(Color.parseColor(lables.get(1).getBackground()));
                label2.setStrokeColor(Color.parseColor(lables.get(1).getFont()));
                label2.setText(lables.get(1).getName());

                label3.setVisibility(View.VISIBLE);
                label3.setTextColor(Color.parseColor(lables.get(2).getFont()));
                label3.setSolid(Color.parseColor(lables.get(2).getBackground()));
                label3.setStrokeColor(Color.parseColor(lables.get(2).getFont()));
                label3.setText(lables.get(2).getName());
                break;
            case 4:
                label1.setVisibility(View.VISIBLE);
                label1.setTextColor(Color.parseColor(lables.get(0).getFont()));
                label1.setSolid(Color.parseColor(lables.get(0).getBackground()));
                label1.setStrokeColor(Color.parseColor(lables.get(0).getFont()));
                label1.setText(lables.get(0).getName());

                label2.setVisibility(View.VISIBLE);
                label2.setTextColor(Color.parseColor(lables.get(1).getFont()));
                label2.setSolid(Color.parseColor(lables.get(1).getBackground()));
                label2.setStrokeColor(Color.parseColor(lables.get(1).getFont()));
                label2.setText(lables.get(1).getName());

                label3.setVisibility(View.VISIBLE);
                label3.setTextColor(Color.parseColor(lables.get(2).getFont()));
                label3.setSolid(Color.parseColor(lables.get(2).getBackground()));
                label3.setStrokeColor(Color.parseColor(lables.get(2).getFont()));
                label3.setText(lables.get(2).getName());

                label4.setVisibility(View.VISIBLE);
                label4.setTextColor(Color.parseColor(lables.get(3).getFont()));
                label4.setSolid(Color.parseColor(lables.get(3).getBackground()));
                label4.setStrokeColor(Color.parseColor(lables.get(3).getFont()));
                label4.setText(lables.get(3).getName());
            default:
                label1.setVisibility(View.VISIBLE);
                label1.setTextColor(Color.parseColor(lables.get(0).getFont()));
                label1.setSolid(Color.parseColor(lables.get(0).getBackground()));
                label1.setStrokeColor(Color.parseColor(lables.get(0).getFont()));
                label1.setText(lables.get(0).getName());

                label2.setVisibility(View.VISIBLE);
                label2.setTextColor(Color.parseColor(lables.get(1).getFont()));
                label2.setSolid(Color.parseColor(lables.get(1).getBackground()));
                label2.setStrokeColor(Color.parseColor(lables.get(1).getFont()));
                label2.setText(lables.get(1).getName());

                label3.setVisibility(View.VISIBLE);
                label3.setTextColor(Color.parseColor(lables.get(2).getFont()));
                label3.setSolid(Color.parseColor(lables.get(2).getBackground()));
                label3.setStrokeColor(Color.parseColor(lables.get(2).getFont()));
                label3.setText(lables.get(2).getName());

                label4.setVisibility(View.VISIBLE);
                label4.setTextColor(Color.parseColor(lables.get(3).getFont()));
                label4.setSolid(Color.parseColor(lables.get(3).getBackground()));
                label4.setStrokeColor(Color.parseColor(lables.get(3).getFont()));
                label4.setText(lables.get(3).getName());
                shengluehao.setVisibility(View.VISIBLE);
                break;
        }


        Product_DescBean.ProductBean product = descBean.getProduct();
        Glide.with(this).load(product.getProduct_logo()).crossFade().into(productLogo);
        String minAl = product.getMin_algorithm();
        minAlgorithm.setText(minAl+"%");
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

        if(minimum_amount.length()>4){
           substringmin = minimum_amount.substring(0, minimum_amount.length() - 4);
            substringmin=substringmin+"万";
        }else {
            substringmin=minimum_amount;
        }
        if(maximum_amount.length()>4){
            substringmax = maximum_amount.substring(0, maximum_amount.length() - 4);
            substringmax=substringmax+"万";
        }else {
            substringmax=maximum_amount;
        }
        minMax.setText(substringmin+"~"+substringmax);

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

            actualAccount.setText(" 实际到账金额:" + product_actual_account);
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
            interestAlgorithm.setText(" 利息算法:月息利息" + min + "%" + "-" + max + "%");
        }

        if (product_prepayment != null) {
            prepayment.setVisibility(View.VISIBLE);
            prepayment.setText(" 提前还款:" + product_prepayment);
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
                    startActivity(new Intent(this, HtmlActivity.class).putExtra("product", descBean));
                }
                break;
        }
    }

}
