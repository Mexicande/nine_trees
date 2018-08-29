package cn.com.cashninetrees.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.api.ApiService;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.base.BaseActivity;
import cn.com.cashninetrees.bean.Login;
import cn.com.cashninetrees.interfaceutils.OnRequestDataListener;
import cn.com.cashninetrees.model.clsaa_special.Class_Special;
import cn.com.cashninetrees.ui.adapter.Recycler_Classify_Adapter;
import cn.com.cashninetrees.utils.SPUtil;
import cn.com.cashninetrees.utils.ToastUtils;
import cn.com.cashninetrees.view.statuslayout.FadeViewAnimProvider;
import cn.com.cashninetrees.view.statuslayout.StateLayout;

/**
 * @author apple
 * 我的收藏
 */
public class CollectionActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.stateLayout)
    StateLayout stateLayout;
    @Bind(R.id.SwipeLayout)
    SwipeRefreshLayout SwipeLayout;
    private Recycler_Classify_Adapter classify_recycler_adapter;
    private String   token;
    private View errorView;
    private View notDataView;
    private String from;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, CollectionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        initToolber();
        from = getIntent().getStringExtra("from");
        if("vip".equals(from)){
            String title = getIntent().getStringExtra("title");
            titleName.setText(title);
            initData(Urls.Vip.VIPPRODUCT);
        }else {
            titleName.setText("我的收藏");
            initData(Urls.product.ProductCollectionList);
        }
        setListener();

    }

    private void setListener() {

        SwipeLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        SwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if("vip".equals(from)) {
                            initData(Urls.Vip.VIPPRODUCT);
                        }else {
                            initData(Urls.product.ProductCollectionList);
                        }
                        SwipeLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(CollectionActivity.this, ProductDesc.class).putExtra("pid", classify_recycler_adapter.getData().get(position).getId()));
            }
        });

    }


    private void initData(String url) {
        token = SPUtil.getString(this, Urls.lock.TOKEN);
        Map<String, String> parms1 = new HashMap<>();
        parms1.put("token", token);
        JSONObject jsonObject = new JSONObject(parms1);

        ApiService.GET_SERVICE(url, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                stateLayout.showContentView();
                    try {

                            Gson gson = new Gson();
                            String data1 = data.getString("data");
                            Class_Special.DataBean.ProductBean[] productBeen = gson.fromJson(data1, Class_Special.DataBean.ProductBean[].class);
                            if (productBeen.length == 0) {
                                classify_recycler_adapter.setNewData(null);
                                classify_recycler_adapter.setEmptyView(notDataView);
                            } else {
                                classify_recycler_adapter.setNewData(Arrays.asList(productBeen));
                                recyclerView.smoothScrollToPosition(0);
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            @Override
            public void requestFailure(int code, String msg) {
                    classify_recycler_adapter.setEmptyView(errorView);
                    ToastUtils.showToast(CollectionActivity.this, "服务器异常");
            }
        });

    }
    private void initToolber() {

        notDataView = getLayoutInflater().inflate(R.layout.view_no_product_empty, (ViewGroup) recyclerView.getParent(), false);
        errorView = getLayoutInflater().inflate(R.layout.view_error, (ViewGroup) recyclerView.getParent(), false);
        stateLayout.setViewSwitchAnimProvider(new FadeViewAnimProvider());


        classify_recycler_adapter = new Recycler_Classify_Adapter(R.layout.product_trem,null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(classify_recycler_adapter);

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }    }


    /**
     * 刷新数据
     * @param event
     */
    @Subscribe
    public void onMessageEvent(Login event) {
        token= SPUtil.getString(this, Urls.lock.TOKEN);
        if("vip".equals(from)) {
            initData(Urls.Vip.VIPPRODUCT);
        }else {
            initData(Urls.product.ProductCollectionList);
        }
    }
}
