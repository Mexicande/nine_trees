package cn.com.stableloan.ui.activity.vip;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.ApiService;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.bean.Login;
import cn.com.stableloan.bean.PayEvent;
import cn.com.stableloan.common.Constants;
import cn.com.stableloan.interfaceutils.OnRequestDataListener;
import cn.com.stableloan.model.Notification;
import cn.com.stableloan.model.VipBean;
import cn.com.stableloan.pay.alipay.Alipay;
import cn.com.stableloan.pay.alipay.PayResult;
import cn.com.stableloan.pay.wechat.Wechat;
import cn.com.stableloan.ui.activity.CollectionActivity;
import cn.com.stableloan.ui.activity.HtmlActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.adapter.PayVipAdapter;
import cn.com.stableloan.utils.ActivityUtils;
import cn.com.stableloan.utils.OnClickStatistics;
import cn.com.stableloan.utils.SPUtil;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.ProductItemDecoration;
import cn.com.stableloan.view.marqueeview.MarqueeView;

/**
 * @author apple
 */
public class VipActivity extends BaseActivity {

    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.pay_Recycler)
    RecyclerView payRecycler;
    @Bind(R.id.bt_open)
    TextView btOpen;
    @Bind(R.id.marqueeView)
    MarqueeView marqueeView;
    private ImmersionBar mImmersionBar;

    private PayVipAdapter mPayVipAdapter;
    private VipBean vipBean;
    private String mToken;
    private String vip;
    private BottomSheetDialog mBottomSheetDialog;
    private static final int SDK_PAY_FLAG = 1;
    private ImageView mWeiChat;
    private ImageView mAlipay;
    private SwitchHandler mHandler = new SwitchHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        initView();
        getDate();
        setListener();
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.white)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        marqueeView.startFlipping();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        marqueeView.stopFlipping();
    }

    @Subscribe
    public void onMessageEvent(Login event) {
        if (event != null) {
            vip = SPUtil.getString(VipActivity.this, Constants.VIP);
            mToken = SPUtil.getString(VipActivity.this, Urls.lock.TOKEN);
            if (TextUtils.isEmpty(vip) || "0".equals(vip)) {
                btOpen.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else if (!TextUtils.isEmpty(vip) && "1".equals(vip)) {
                btOpen.setBackgroundColor(getResources().getColor(R.color.gay));
                btOpen.setText("已开通");
            }
        }
    }

    @Subscribe
    public void onPayRusult(PayEvent type) {
        if (type.type == 1) {
            SPUtil.putString(this, Constants.VIP, "1");
            vip = "1";
            btOpen.setBackgroundColor(getResources().getColor(R.color.gay));
            btOpen.setText("已开通");
            btOpen.setEnabled(false);
        } else {
            mBottomSheetDialog.dismiss();
        }
    }


    private void initView() {
        vip = SPUtil.getString(VipActivity.this, Constants.VIP);
        mToken = SPUtil.getString(VipActivity.this, Urls.lock.TOKEN);
        if (TextUtils.isEmpty(vip) || "0".equals(vip)) {
            btOpen.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (!TextUtils.isEmpty(vip) && "1".equals(vip)) {
            btOpen.setBackgroundColor(getResources().getColor(R.color.gay));
            btOpen.setText("已开通");
            btOpen.setEnabled(false);
        }

        mPayVipAdapter = new PayVipAdapter(null);
        payRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        payRecycler.addItemDecoration(new ProductItemDecoration(3, 30, true));
        payRecycler.setAdapter(mPayVipAdapter);

        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.silde_charge_pay, null, false);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);

        mAlipay = view.findViewById(R.id.pay_aplipay);
        mWeiChat = view.findViewById(R.id.pay_weichat);
    }


    private void getDate() {
        //支付方式
        ApiService.GET_SERVICE(Urls.Vip.PAYMENT, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject data1 = data.getJSONObject("data");
                    String wechat = data1.getString("wechat");
                    if ("0".equals(wechat)) {
                        mWeiChat.setVisibility(View.GONE);
                    }
                    String alipay = data1.getString("alipay");
                    if ("0".equals(alipay)) {
                        mAlipay.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });


        ApiService.GET_SERVICE(Urls.Vip.vipService, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject vip = data.getJSONObject("data");
                    Gson gson = new Gson();
                    vipBean = gson.fromJson(vip.toString(), VipBean.class);
                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    builder.append("总价 ").append(vipBean.getPrice());
                    ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.blacktext));
                    builder.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    tvPrice.setText(builder);
                    mPayVipAdapter.addData(vipBean.getService());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(VipActivity.this, msg);
            }
        });

        ApiService.GET_SERVICE(Urls.Vip.NOTICE_NEWS, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    String data1 = data.getString("data");
                    Gson gson = new Gson();
                    Notification[] notification = gson.fromJson(data1, Notification[].class);
                    List<Notification> notifications = Arrays.asList(notification);
                    marqueeView.startWithList(notifications);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    private void setListener() {

        mPayVipAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<VipBean.ServiceBean> data = mPayVipAdapter.getData();
                VipBean.ServiceBean serviceBean = data.get(position);
                if (TextUtils.isEmpty(mToken)) {
                    ActivityUtils.startActivity(LoginActivity.class);
                } else if (TextUtils.isEmpty(vip) || "0".equals(vip)) {
                    ToastUtils.showToast(VipActivity.this, "请先开通Vip才能享受特权");
                } else {
                    switch (serviceBean.getExplain()) {
                        case "double":
                            Intent lotteryIntent = new Intent(VipActivity.this, LotteryActivity.class);
                            lotteryIntent.putExtra("title", serviceBean.getTitle());
                            startActivity(lotteryIntent);
                            break;
                        case "product":
                            Intent intent = new Intent(VipActivity.this, CollectionActivity.class);
                            intent.putExtra("from", "vip");
                            intent.putExtra("title", serviceBean.getTitle());
                            startActivity(intent);
                            break;
                        case "credit":
                            getCredit(serviceBean.getTitle());
                            break;
                        case "card":
                            Intent creditIntent = new Intent(VipActivity.this, VipCreditActivity.class);
                            creditIntent.putExtra("title", serviceBean.getTitle());
                            startActivity(creditIntent);
                            break;
                        default:
                            Intent htmIntent = new Intent(VipActivity.this, HtmlActivity.class);
                            if (!TextUtils.isEmpty(serviceBean.getLink())) {
                                htmIntent.putExtra("link", serviceBean.getLink());
                                htmIntent.putExtra("title", serviceBean.getTitle());
                                startActivity(htmIntent);
                            }
                            break;
                    }
                }
            }
        });

    }

    /**
     * 信用报告
     */
    private void getCredit(String title) {
        JSONObject jsonObjec = new JSONObject();
        try {
            jsonObjec.put(Urls.lock.TOKEN, mToken);

            ApiService.GET_SERVICE(Urls.Vip.CREDIT_INFO, jsonObjec, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject data) {
                    try {
                        String url = data.getString("data");
                        Intent intent = new Intent(VipActivity.this, HtmlActivity.class);
                        intent.putExtra("link", url);
                        intent.putExtra("title", title);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void requestFailure(int code, String msg) {
                    ToastUtils.showToast(VipActivity.this, msg);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.id_payAgreement, R.id.bt_open, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_payAgreement:
                if (!TextUtils.isEmpty(vipBean.getLink())) {
                    Intent intent = new Intent(this, HtmlActivity.class);
                    intent.putExtra("link", vipBean.getLink());
                    intent.putExtra("title", "会员协议");
                    startActivity(intent);
                }
                break;
            case R.id.bt_open:
                showPay();
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    private void showPay() {
        OnClickStatistics.buriedStatistics(mToken, Constants.Recharge);
        mBottomSheetDialog.show();

        mWeiChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payMoney(Constants.WECHAT_TYPE);
            }
        });
        mAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payMoney(Constants.ALIPAY_TYPE);
            }
        });


    }

    private void payMoney(int type) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Urls.lock.TOKEN, mToken);
            jsonObject.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Urls.Vip.PAY, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (type == 1) {
                        JSONObject payInfo1 = data.getJSONObject("data");
                        Wechat wechat = new Wechat(VipActivity.this);
                        wechat.pay(payInfo1.toString());
                    } else if (type == 2) {
                        String payInfo = data.getString("data");
                        Alipay alipay = new Alipay(VipActivity.this);
                        alipay.setHander(mHandler);
                        alipay.pay(payInfo);
                    } else {
                        String payInfo2 = data.getString("data");
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri uri = Uri.parse(payInfo2);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(VipActivity.this, msg);
            }
        });


    }


    private static class SwitchHandler extends Handler {

        private WeakReference<VipActivity> mWeakReference;

        SwitchHandler(VipActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            VipActivity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((String) msg.obj);
                        /**
                         * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                         * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                         * docType=1) 建议商户依赖异步通知
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                            activity.mBottomSheetDialog.dismiss();
                            EventBus.getDefault().post(new PayEvent(1));

                        } else {
                            EventBus.getDefault().post(new PayEvent(0));
                            // 判断resultStatus 为非"9000"则代表可能支付失败
                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                Toast.makeText(activity, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }

            }
        }
    }


}
