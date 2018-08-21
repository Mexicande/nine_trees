package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.api.ApiService;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.bean.Login;
import cn.com.stableloan.interfaceutils.OnRequestDataListener;
import cn.com.stableloan.model.ProductBean;
import cn.com.stableloan.model.Product_DescBean;
import cn.com.stableloan.model.home.Hot_New_Product;
import cn.com.stableloan.ui.adapter.ListProductAdapter;
import cn.com.stableloan.ui.adapter.SuperTextAdapter;
import cn.com.stableloan.utils.ActivityUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtil;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.top_menu.MenuItem;
import cn.com.stableloan.utils.top_menu.TopRightMenu;
import cn.com.stableloan.view.RecyclerViewDecoration;
import cn.com.stableloan.view.share.StateListener;
import cn.com.stableloan.view.share.TPManager;
import cn.com.stableloan.view.share.WXManager;
import cn.com.stableloan.view.share.WXShareContent;

/**
 * 产品desc
 *
 * @author apple
 */
public class ProductDesc extends BaseActivity {


    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.review)
    TextView review;
    @Bind(R.id.arrive)
    TextView arrive;
    @Bind(R.id.repayment)
    TextView repayment;
    @Bind(R.id.repayment_channels)
    TextView repaymentChannels;
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
    @Bind(R.id.linla)
    LinearLayout linla;
    @Bind(R.id.apply)
    Button apply;
    @Bind(R.id.bt_share)
    ImageView btShare;
    @Bind(R.id.tv_DescAmount)
    TextView tvDescAmount;
    @Bind(R.id.tv_cycle)
    TextView tvCycle;
    @Bind(R.id.fastest_time)
    TextView fastestTime;
    @Bind(R.id.tv_interest_algorithm)
    TextView tvInterestAlgorithm;
    @Bind(R.id.et_MaxLimit)
    EditText etMaxLimit;
    @Bind(R.id.et_MaxTime)
    EditText etMaxTime;
    @Bind(R.id.tv_Desccharge)
    TextView tvDesccharge;
    @Bind(R.id.zero_algorithm)
    TextView zeroAlgorithm;
    @Bind(R.id.out_view)
    RelativeLayout outView;
    @Bind(R.id.tv_DescTerminally)
    TextView tvDescTerminally;
    @Bind(R.id.desc_advertising)
    ImageView descAdvertising;
    @Bind(R.id.iv_news)
    ImageView ivNews;
    @Bind(R.id.iv_hots)
    ImageView ivHots;
    @Bind(R.id.tv_descLimit)
    TextView tvDescLimit;
    @Bind(R.id.view_line)
    View viewLine;
    @Bind(R.id.tv_procedure)
    TextView tvProcedure;
    @Bind(R.id.recommend)
    RecyclerView recommend;
    private int pid;
    private ListProductAdapter productAdapter;

    private Product_DescBean.DataBean descBean;
    private boolean umeng=false;
    private boolean shareFlag = false;
    private static final int REQUEST_CODE = 3000;
    private String token;
    private static String TAG = ProductDesc.class.getName();

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProductDesc.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);
        ButterKnife.bind(this);
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.colorPrimary)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();
        initToolbar();
        token = SPUtil.getString(this, Urls.lock.TOKEN);
        pid = getIntent().getIntExtra("pid", 0);
        if (pid != 0) {
            getProductDate();
        }
        setListener();
    }



/*

    @Override
    public void onResume() {
        super.onResume();
        Bundle bun = getIntent().getExtras();
        if(bun!=null){
            String id = bun.getString("id");
            if(id!=null){
                umeng=true;
                pid=Integer.parseInt(id);
                getProductDate();
            }
        }
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        if(body!=null){
            try {
                JSONObject jsonObject=new JSONObject(body);
                JSONObject extra = jsonObject.getJSONObject("extra");
                String id = extra.getString("id");
                if(id!=null){
                    umeng=true;
                    pid=Integer.parseInt(id);
                    getProductDate();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
*/

    /**
     * 微信分享
     * ediText
     */
    private void setListener() {


        TPManager.getInstance().initAppConfig(Urls.KEY.WEICHAT_APPID, null, null, null);
        wxManager = new WXManager(this);
        StateListener<String> wxStateListener = new StateListener<String>() {
            @Override
            public void onComplete(String s) {
                ToastUtils.showToast(ProductDesc.this, s);
            }

            @Override
            public void onError(String err) {
                ToastUtils.showToast(ProductDesc.this, err);
            }

            @Override
            public void onCancel() {
                ToastUtils.showToast(ProductDesc.this, "取消");
            }
        };

        wxManager.setListener(wxStateListener);
        //贷款金额

        etMaxTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    computations();
                }
            }
        });

        etMaxLimit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    computations();
                }
            }
        });

        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(ProductDesc.this, ProductDesc.class).putExtra("pid", productAdapter.getData().get(position).getId()));
            }
        });


    }

    private void computations() {
        if (descBean != null) {
            String hint = etMaxTime.getText().toString();
            String str = etMaxLimit.getText().toString();
            String minimumAmount = descBean.getMinimum_amount();
            String maximumAmount = descBean.getMaximum_amount();
            if (!hint.isEmpty() && !str.isEmpty() && minimumAmount != null && maximumAmount != null) {
                int lim = Integer.parseInt(str);
                if (lim < Integer.parseInt(minimumAmount)) {
                    etMaxLimit.setText(minimumAmount);
                }
                if (lim > Integer.parseInt(maximumAmount)) {
                    etMaxLimit.setText(maximumAmount);
                }

                int time = Integer.parseInt(hint);
                if (time == 0) {
                    etMaxTime.setText(descBean.getMin_cycle());
                    time = Integer.parseInt(descBean.getMin_cycle());
                } else {
                    etMaxTime.setText(time + "");
                }
                String min_algorithm = descBean.getMin_algorithm();

                Double aDouble = Double.valueOf(min_algorithm);


                String fee = descBean.getFee();
                int i1 = fee.lastIndexOf(".");
                String substring = fee.substring(0, i1);

                double v1 = aDouble * time / 100 * lim + Integer.parseInt(substring);

                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String format = decimalFormat.format(v1);
                int i = format.indexOf(".");
                if (i == 0) {
                    zeroAlgorithm.setText("0" + format + "元");
                } else {
                    zeroAlgorithm.setText(format + "元");
                }


                double v2 = (lim + v1) / time;

                int everyRate = (int) v2;

                String everyTime = String.valueOf(everyRate);

                tvDescTerminally.setText(everyTime + "元");
            }
        }
    }

    private void getProductDate() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(pid));
        if (!TextUtils.isEmpty(token)) {
            params.put("token", token);
        }
        params.put("terminal", "1");
        final JSONObject jsonObject = new JSONObject(params);

        ApiService.GET_SERVICE(Urls.product.PRODUCT_DETAIL, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    String data1 = data.getString("data");
                    Gson gson = new Gson();
                    descBean = gson.fromJson(data1, Product_DescBean.DataBean.class);
                    if (descBean != null) {
                        dateInset(descBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(ProductDesc.this, msg);
            }
        });
        HashMap<String, String> params1 = new HashMap<>();
        params1.put("id", String.valueOf(pid));
        final JSONObject jsonObject1 = new JSONObject(params1);

        ApiService.GET_SERVICE(Urls.product.DETAILS_LIST, jsonObject1, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    String str = data.getString("data");
                    Gson gson=new Gson();
                    Hot_New_Product.DataBean[] dataBeans = gson.fromJson(str, Hot_New_Product.DataBean[].class);
                    List<Hot_New_Product.DataBean> beans = Arrays.asList(dataBeans);
                    productAdapter.addData(beans);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });

    }


    private String substringmin = "";
    private String substringmax = "";

    private SuperTextAdapter superTextAdapter;

    private void dateInset(Product_DescBean.DataBean product) {

        int collectioStatus = product.getCollectioStatus();

        if (collectioStatus == 1) {
            shareFlag = true;
        }

        List<Product_DescBean.DataBean.LabelsBean> labels = product.getLabels();

        flowRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        RecyclerViewDecoration decoration = new RecyclerViewDecoration(0, 0);
        flowRecyclerView.addItemDecoration(decoration);
        superTextAdapter = new SuperTextAdapter(null);
        flowRecyclerView.setAdapter(superTextAdapter);
        superTextAdapter.setNewData(labels);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (isValidContextForGlide(this)) {
            // Load image via Glide lib using context
            Glide.with(this).load(product.getProduct_logo()).apply(options)
                    .into(productLogo);
            if (product.getAd_image() != null && !product.getAd_image().isEmpty()) {
                descAdvertising.setVisibility(View.VISIBLE);
                Glide.with(this).load(product.getAd_image())
                        .apply(options).into(descAdvertising);
                viewLine.setVisibility(View.GONE);

            } else {
                descAdvertising.setVisibility(View.GONE);
                viewLine.setVisibility(View.VISIBLE);
            }
        }


        if ("1".equals(product.getActivity())) {
            ivHots.setVisibility(View.VISIBLE);
        } else {
            ivHots.setVisibility(View.GONE);
        }
        if ("1".equals(product.getOnline())) {
            ivNews.setVisibility(View.VISIBLE);
        } else {
            ivNews.setVisibility(View.GONE);
        }
        tvPname.setText(product.getPname());
        productIntroduction.setText(product.getProduct_introduction());
        String minAl = product.getMin_algorithm();
        minAlgorithm.setText(minAl + "%");
        String product_review = product.getReview();
        String product_repayment = product.getRepayment();
        String product_repayment_channels = product.getRepayment_channels();
        String minimum_amount = product.getMinimum_amount();
        String maximum_amount = product.getMaximum_amount();
        String interest_algorithm = product.getInterest_algorithm();
        if ("0".equals(interest_algorithm)) {
            tvInterestAlgorithm.setText("参考日利率");
            tvCycle.setText(product.getMin_cycle() + "~" + product.getMax_cycle() + "日");
            tvDescLimit.setText("贷款期限(日)");
        } else {
            tvInterestAlgorithm.setText("参考月利率");
            tvCycle.setText(product.getMin_cycle() + "~" + product.getMax_cycle() + "月");
            tvDescLimit.setText("贷款期限(月)");
        }
        if (minimum_amount.length() > 4) {
            substringmin = minimum_amount.substring(0, minimum_amount.length() - 4);
            substringmin = substringmin + "万";
        } else {
            substringmin = minimum_amount;
        }
        fastestTime.setText(product.getFastest_time());
        if (maximum_amount.length() > 4) {
            substringmax = maximum_amount.substring(0, maximum_amount.length() - 4);
            substringmax = substringmax + "万";
        } else {
            substringmax = maximum_amount;
        }

        etMaxLimit.setText(minimum_amount);
        etMaxTime.setText(product.getMin_cycle());
        if (product.getActual_account() != null && !product.getActual_account().isEmpty()) {
            setTextViewColor(arrive, "到账方式: " + product.getActual_account());
        } else {
            arrive.setVisibility(View.GONE);
        }
        tvDescAmount.setText(substringmin + "~" + substringmax + "元");
        if (product_review != null && !product_review.isEmpty()) {
            setTextViewColor(review, "审核方式: " + product_review);
        } else {
            review.setVisibility(View.GONE);
        }
        if (product_repayment != null && !product_repayment.isEmpty()) {
            setTextViewColor(repayment, "还款方式: " + product_repayment);
        } else {
            repayment.setVisibility(View.GONE);
        }
        if (product_repayment_channels != null && !product_repayment_channels.isEmpty()) {
            setTextViewColor(repaymentChannels, "还款渠道: " + product_repayment_channels);
        } else {
            repaymentChannels.setVisibility(View.GONE);

        }
        if (product.getProduct_details() != null) {
            String details = product.getProduct_details();
            String replace = details.replace("aaa", "\n");
            productDetails.setText(replace);
        }
        if (!"0".equals(product.getFee())) {
            tvDesccharge.setText(product.getFee() + "元");
        }
        computations();

    }

    private void setTextViewColor(TextView view, String s) {

        SpannableString spanString = new SpannableString(s);
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.gay));
        spanString.setSpan(span, 0, 5, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        view.setText(spanString);
    }

    private void initToolbar() {
        titleName.setText("产品详情");
        productAdapter=new ListProductAdapter(null);
        recommend.setLayoutManager(new LinearLayoutManager(this));
        recommend.setAdapter(productAdapter);

    }


    @OnClick({R.id.iv_back, R.id.apply, R.id.bt_share
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.apply:
                if (TextUtils.isEmpty(token)) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    startActivity(new Intent(ProductDesc.this, HtmlActivity.class).putExtra("product", descBean));
                }
                break;
            case R.id.bt_share:
                setShared_Collection();
                break;
            default:
                break;
        }
    }

    private void setShared_Collection() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.mipmap.iv_share_wechat, "分享到微信"));
        menuItems.add(new MenuItem(R.mipmap.iv_share_friend, "分享到朋友圈"));
        if (shareFlag) {
            menuItems.add(new MenuItem(R.mipmap.iv_collectioning, "取消收藏"));
        } else {
            menuItems.add(new MenuItem(R.mipmap.iv_collection, "收藏"));
        }
        TopRightMenu mTopRightMenu = new TopRightMenu(this, R.layout.trm_item_popup_menu_list);
        mTopRightMenu
                .showIcon(true)
                //显示菜单图标，默认为true
                .dimBackground(true)
                //背景变暗，默认为true
                .needAnimationStyle(true)
                //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                //默认为R.style.TRM_ANIM_STYLE
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(position -> {
                    switch (position) {
                        case 0:
                            shareWechat(WXShareContent.WXSession);
                            break;
                        case 1:
                            shareWechat(WXShareContent.WXTimeline);
                            break;
                        case 2:
                            if (TextUtils.isEmpty(token)) {
                                ActivityUtils.startActivity(LoginActivity.class);
                            } else {
                                if (shareFlag) {
                                    collectionproduct("2");
                                } else {
                                    collectionproduct("1");
                                }
                            }
                            break;
                        default:
                            break;
                    }

                })
                .showAsDropDown(btShare, -245, 0);


    }

    private WXManager wxManager;

    private void shareWechat(int scence) {
        if( descBean.getId()!=null){
            WXShareContent contentWX = new WXShareContent();
            contentWX.setScene(scence)
                    .setType(WXShareContent.share_type.WebPage)
                    .setWeb_url(Urls.KEY.PageWeb + descBean.getId())
                    .setTitle("安稳钱包")
                    .setDescription(descBean.getProduct_introduction())
                    .setImage_url(Urls.logoUrl);
            wxManager.share(contentWX);
        }else {
            ToastUtils.showToast(this,"网络异常！");
        }


    }

    /**
     * 收藏
     *
     * @param status
     */
    private void collectionproduct(final String status) {
        if (TextUtils.isEmpty(token)) {
            ActivityUtils.startActivity(LoginActivity.class);
        } else {
            Map<String, String> parms = new HashMap<>();
            parms.put("token", token);
            parms.put("status", status);
            parms.put("product_id", String.valueOf(pid));
            JSONObject jsonObject = new JSONObject(parms);
            ApiService.GET_SERVICE(Urls.product.PRODUCT_COLLECTION, jsonObject, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject data) {
                    if (("1").equals(status)) {
                        shareFlag = true;
                        ToastUtils.showToast(ProductDesc.this, "收藏成功");
                    } else {
                        shareFlag = false;
                        ToastUtils.showToast(ProductDesc.this, "取消成功");
                    }
                }

                @Override
                public void requestFailure(int code, String msg) {
                    ToastUtils.showToast(ProductDesc.this, msg);

                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == 100) {
                    startActivity(new Intent(ProductDesc.this, HtmlActivity.class).putExtra("product", descBean));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 刷新数据
     *
     * @param event
     */
    @Subscribe
    public void onMessageEvent(Login event) {
        if (event.mlogin == 1) {
            token = SPUtil.getString(this, Urls.lock.TOKEN);
            getProductDate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        return true;
    }

}
