package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mancj.slideup.SlideUp;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.bean.ProcuctCollectionEvent;
import cn.com.stableloan.model.Product_DescBean;
import cn.com.stableloan.ui.adapter.SuperTextAdapter;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.top_menu.MenuItem;
import cn.com.stableloan.utils.top_menu.TopRightMenu;
import cn.com.stableloan.view.DescDialog;
import cn.com.stableloan.view.likebutton.LikeButton;
import cn.com.stableloan.view.share.StateListener;
import cn.com.stableloan.view.share.TPManager;
import cn.com.stableloan.view.share.WXManager;
import cn.com.stableloan.view.share.WXShareContent;
import okhttp3.Call;
import okhttp3.Response;

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
    @Bind(R.id.bt_share)
    ImageView btShare;
    private String pid;
    private Product_DescBean descBean;
    private boolean shareFlag=false;
    private KProgressHUD hud;
    private static final int COLLECTION = 2000;

    private boolean flag = false;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProductDesc.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);
        ButterKnife.bind(this);
        ZhugeSDK.getInstance().init(getApplicationContext());
        initToolbar();
        pid = getIntent().getStringExtra("pid");
        if (pid != null) {
            getProductDate();
        }

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
    }


    private void getProductDate() {
        Boolean login = (Boolean) SPUtils.get(this, "login", false);
        String token = (String) SPUtils.get(this, "token", "1");

        HashMap<String, String> params = new HashMap<>();
        params.put("id", pid);
        if (login != null) {
            if (login) {
                params.put("token", token);
            }
        }
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(true)
                .show();

        final JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Urls.Ip_url + Urls.product.Productdetail)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            try {
                                JSONObject object = new JSONObject(s);
                                int error_code = object.getInt("error_code");
                                if (error_code == 0) {
                                    Gson gson = new Gson();
                                    descBean = gson.fromJson(s, Product_DescBean.class);
                                    if (descBean != null) {
                                        dateInset(descBean);
                                    }

                                } else {
                                    String error_message = object.getString("error_message");
                                    ToastUtils.showToast(ProductDesc.this, error_message);
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

    private void dateInset(Product_DescBean date) {


        Product_DescBean.DataBean product = date.getData();
        int collectioStatus = product.getCollectioStatus();
        if (collectioStatus == 1) {
            shareFlag=true;
        }
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("产品", product.getPname() + "detail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//记录事件
        ZhugeSDK.getInstance().track(this, "产品详情贷款", eventObject);

        List<Product_DescBean.DataBean.LabelsBean> labels = product.getLabels();


       /* FlexboxLayoutManager manager = new FlexboxLayoutManager();

        manager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setAlignItems(AlignItems.STRETCH);

        flowRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        flowRecyclerView.setLayoutManager(manager);*/

        flowRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));

        superTextAdapter = new SuperTextAdapter(labels);
        flowRecyclerView.setAdapter(superTextAdapter);
        //superTextAdapter.addData(labels);


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(this).load(product.getProduct_logo()).apply(options)
                .into(productLogo);

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
            substringmin = minimum_amount;
        }
        if (maximum_amount.length() > 4) {
            substringmax = maximum_amount.substring(0, maximum_amount.length() - 4);
            substringmax = substringmax + "万";
        } else {
            substringmax = maximum_amount;
        }
        if (product.getActual_account() != null) {
            arrive.setVisibility(View.VISIBLE);
            setTextViewColor(arrive, "到账方式: " + product.getActual_account());

        }
        minMax.setText(substringmin + "~" + substringmax);
        if (product_crowd != null) {
            crowd.setVisibility(View.VISIBLE);
            setTextViewColor(crowd, "面向人群: " + product_crowd);

        }
        if (product_review != null) {
            review.setVisibility(View.VISIBLE);
            setTextViewColor(review, "审核方式: " + product_review);

        }


        if (product_actual_account != null) {
            actualAccount.setVisibility(View.VISIBLE);
            setTextViewColor(actualAccount, "实际到账: " + product_actual_account);


        }
        if (product_repayment != null) {
            repayment.setVisibility(View.VISIBLE);
            setTextViewColor(repayment, "还款方式: " + product_repayment);


        }
        if (product_repayment_channels != null) {
            repaymentChannels.setVisibility(View.VISIBLE);
            setTextViewColor(repaymentChannels, "还款渠道: " + product_repayment_channels);


        }
        if (min != null && max != null) {
            interestAlgorithm.setVisibility(View.VISIBLE);
            setTextViewColor(interestAlgorithm, "利息算法: " + product.getInterest_algorithm());

        }

        if (product_prepayment != null) {
            prepayment.setVisibility(View.VISIBLE);
            setTextViewColor(prepayment, "提前还款: " + product_prepayment);
        }
        if (product.getProduct_details() != null) {
            String details = product.getProduct_details();
            String aaa = details.replace("aaa", "\n");
            productDetails.setText(aaa);
        }
    }

    private void setTextViewColor(TextView view, String s) {
        SpannableString spanString = new SpannableString(s);
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.select_text_color));
        spanString.setSpan(span, 0, 5, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        view.setText(spanString);
    }
    private void initToolbar() {
        titleName.setText("产品详情");

    }

    private DescDialog descDialog;

    @OnClick({R.id.iv_back, R.id.platform_desc, R.id.apply, R.id.ic_strategy, R.id.bt_share
            })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.platform_desc:
                startActivity(new Intent(this, PlatformInfoActivity.class).putExtra("pid", String.valueOf(descBean.getData().getPl_id())));
                break;
            case R.id.ic_strategy:
                if (!descBean.getData().getRaiders_connection().isEmpty()) {
                    startActivity(new Intent(this, HtmlActivity.class).putExtra("Strate", descBean));
                } else {
                    ToastUtils.showToast(this, "此产品暂无攻略");
                }
                break;
            case R.id.apply:
                Boolean login = (Boolean) SPUtils.get(this, "login", false);
                if (!login) {
                    LoginActivity.launch(this);
                } else {
                    Boolean dialog = (Boolean) SPUtils.get(this, "dialog", false);
                    if (dialog) {
                        sendIO();
                        startActivity(new Intent(this, HtmlActivity.class).putExtra("product", descBean));
                    } else {
                        descDialog = new DescDialog(this);
                        descDialog.setTitle("提示");
                        descDialog.setMessage("确定退出登陆?");
                        descDialog.setYesOnclickListener("确定", new DescDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                descDialog.dismiss();
                                sendIO();
                                startActivity(new Intent(ProductDesc.this, HtmlActivity.class).putExtra("product", descBean));
                            }
                        });

                        descDialog.show();
                    }
                }
                break;
            case R.id.bt_share:
                setShared_Collection();
                break;
            default:
                break;
        }
    }
    private void setShared_Collection(){
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.mipmap.iv_share_wechat, "分享到微信"));
        menuItems.add(new MenuItem(R.mipmap.iv_share_friend, "分享到朋友圈"));
        if(shareFlag){
            menuItems.add(new MenuItem(R.mipmap.iv_collectioning, "取消收藏"));
        }else {
            menuItems.add(new MenuItem(R.mipmap.iv_collection, "收藏"));
        }
        TopRightMenu mTopRightMenu = new TopRightMenu(this);
        mTopRightMenu
                .setHeight(430)
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(true)           //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        switch (position){
                            case 0:
                                shareWechat(WXShareContent.WXSession);
                                break;
                            case 1:
                                shareWechat(WXShareContent.WXTimeline);
                                break;
                            case 2:
                                Boolean login1 = (Boolean) SPUtils.get(ProductDesc.this, "login", false);
                                if (!login1) {
                                    startActivityForResult(new Intent(ProductDesc.this, LoginActivity.class).putExtra("from", "collection"), COLLECTION);
                                } else {
                                    if(shareFlag){
                                        CollectionProduct("2");
                                    }else {
                                        CollectionProduct("1");
                                    }
                                }
                                break;
                        }

                    }
                })
                .showAsDropDown(btShare);


    }

    private WXManager wxManager;

    private void shareWechat(int scence) {


        WXShareContent contentWX = new WXShareContent();
        contentWX.setScene(scence)
                .setType(WXShareContent.share_type.WebPage)
                .setWeb_url(Urls.KEY.PageWeb + descBean.getData().getId())
                .setTitle("安稳钱包")
                .setDescription(descBean.getData().getProduct_introduction())
                .setImage_url("http://orizavg5s.bkt.clouddn.com/logo.png");
        wxManager.share(contentWX);

    }


    private void CollectionProduct(final String status) {

        String token = (String) SPUtils.get(this, "token", "1");

        Map<String, String> parms1 = new HashMap<>();
        parms1.put("token", token);
        parms1.put("status", status);
        parms1.put("product_id", pid);
        JSONObject jsonObject = new JSONObject(parms1);

        OkGo.post(Urls.Ip_url + Urls.product.CollectionDesc)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            try {
                                JSONObject json = new JSONObject(s);
                                int error_code = json.getInt("error_code");
                                if (error_code == 0) {
                                    flag = true;
                                    EventBus.getDefault().post(new ProcuctCollectionEvent("ok"));
                                    if (status.equals("1")) {
                                        shareFlag=true;
                                        ToastUtils.showToast(ProductDesc.this, "收藏成功");
                                    } else {
                                        shareFlag=false;
                                        ToastUtils.showToast(ProductDesc.this, "取消成功");
                                    }
                                } else {
                                    String error_message = json.getString("error_message");
                                    ToastUtils.showToast(ProductDesc.this, error_message);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(ProductDesc.this, "服务器异常");
                            }
                        });
                    }
                });

    }

    private void sendIO() {
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("产品名称", descBean.getData().getPname());
            ZhugeSDK.getInstance().track(getApplicationContext(), "立即申请",
                    eventObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZhugeSDK.getInstance().flush(getApplicationContext());
        if (flag) {
            EventBus.getDefault().post(new ProcuctCollectionEvent("ok"));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case COLLECTION:
                if (resultCode == 2000) {
                    String ok = data.getStringExtra("ok");
                    if (ok != null) {
                        getProductDate();
                        if (!shareFlag) {
                            shareFlag=true;
                        } else {
                            ToastUtils.showToast(this, "已经收藏过了");
                        }
                    }
                }
                break;

        }

    }

}
