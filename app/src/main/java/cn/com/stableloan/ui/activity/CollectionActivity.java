package cn.com.stableloan.ui.activity;

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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

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
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.bean.ProcuctCollectionEvent;
import cn.com.stableloan.model.Class_ListProductBean;
import cn.com.stableloan.model.clsaa_special.Class_Special;
import cn.com.stableloan.ui.adapter.Recycler_Classify_Adapter;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.statuslayout.FadeViewAnimProvider;
import cn.com.stableloan.view.statuslayout.StateLayout;
import okhttp3.Call;
import okhttp3.Response;

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

    private View errorView;
    private View notDataView;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, CollectionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initToolber();
        initData();
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
                        initData();
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

    @Subscribe
    public void onCollection(ProcuctCollectionEvent event) {
        if ("ok".equals(event.msg)) {
            initData();
        }

    }

    private void initData() {

        String token = (String) SPUtils.get(this, "token", "1");

        Map<String, String> parms1 = new HashMap<>();
        parms1.put("token", token);
        JSONObject jsonObject = new JSONObject(parms1);
        OkGo.<String>post(Urls.NEW_Ip_url + Urls.product.ProductCollectionList)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        stateLayout.showContentView();
                        try {
                            JSONObject json = new JSONObject(s);
                            int error_code = json.getInt("error_code");
                            if (error_code == 0) {
                                String data = json.getString("data");
                                Gson gson = new Gson();
                                Class_Special.DataBean.ProductBean[] productBeen = gson.fromJson(data, Class_Special.DataBean.ProductBean[].class);
                                if (productBeen.length == 0) {
                                    classify_recycler_adapter.setNewData(null);
                                    classify_recycler_adapter.setEmptyView(notDataView);
                                } else {
                                    classify_recycler_adapter.setNewData(Arrays.asList(productBeen));
                                    recyclerView.smoothScrollToPosition(0);
                                }
                            }else if(error_code==2){
                                Intent intent=new Intent(CollectionActivity.this,LoginActivity.class);
                                intent.putExtra("message",json.getString("error_message"));
                                intent.putExtra("from","CollectionError");
                                startActivity(intent);
                            } else{
                                classify_recycler_adapter.setNewData(null);
                                classify_recycler_adapter.setEmptyView(notDataView);
                                String error_message = json.getString("error_message");
                                ToastUtils.showToast(CollectionActivity.this, error_message);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                classify_recycler_adapter.setEmptyView(errorView);
                                ToastUtils.showToast(CollectionActivity.this, "服务器异常");
                            }
                        });
                    }
                });

    }

    private void initToolber() {
        titleName.setText("我的收藏");
        ivBack.setVisibility(View.VISIBLE);

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
}
