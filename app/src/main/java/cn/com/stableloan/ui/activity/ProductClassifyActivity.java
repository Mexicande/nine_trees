package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
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
import cn.com.stableloan.bean.ProductListBean;
import cn.com.stableloan.model.Class_ListProductBean;
import cn.com.stableloan.model.News_ClassBean;
import cn.com.stableloan.ui.adapter.Recycler_Classify_Adapter;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.statuslayout.FadeViewAnimProvider;
import cn.com.stableloan.view.statuslayout.StateLayout;
import okhttp3.Call;
import okhttp3.Response;

public class ProductClassifyActivity extends BaseActivity {

    @Bind(R.id.classify_recycl)
    RecyclerView classifyRecycl;
    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout SwipeRefreshLayout;
    @Bind(R.id.stateLayout)
    StateLayout stateLayout;
    private Recycler_Classify_Adapter classify_recycler_adapter;

    private List<ProductListBean.ProductBean> list2;
    private News_ClassBean.ClassBean class_product;
    private ImageView imageView;
    private Class_ListProductBean class_List;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProductClassifyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_classify);
        ButterKnife.bind(this);
      /*  ImmersionBar.with(this)
                .navigationBarColor(R.color.mask)
                .barAlpha(0.2f)
                .init();*/
        stateLayout.setViewSwitchAnimProvider(new FadeViewAnimProvider());

        class_product = (News_ClassBean.ClassBean) getIntent().getSerializableExtra("class_product");

        initView();
        getDate();
        setListener();
    }

    private void getDate() {
        if(class_product!=null){
            String id = class_product.getId();
            titleName.setText(class_product.getName());
            ivBack.setVisibility(View.VISIBLE);
            stateLayout.showProgressView();
            if (class_product != null && id != null) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);
                final JSONObject jsonObject = new JSONObject(params);
                OkGo.post(Urls.puk_URL + Urls.product.ClassProduct)
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
                                            stateLayout.showContentView();
                                            class_List = gson.fromJson(s, Class_ListProductBean.class);
                                            Glide.with(ProductClassifyActivity.this).load(class_List.getImage()).crossFade().into(imageView);
                                            classify_recycler_adapter.setNewData(class_List.getProduct());
                                        } else {
                                            stateLayout.showEmptyView();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                stateLayout.showErrorView();
                            }


                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                stateLayout.showErrorView();
                            }
                        });
            }
        }
    }

    private void setListener() {
        SwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDate();
                        SwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }

        });
        classifyRecycl.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                // ProductDesc.launch(getActivity());
                startActivity(new Intent(ProductClassifyActivity.this, ProductDesc.class).putExtra("pid", class_List.getProduct().get(position).getId()));

            }
        });
    }

    private void initView() {

        imageView = new ImageView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(lp);
        classify_recycler_adapter = new Recycler_Classify_Adapter(null);
        classifyRecycl.setLayoutManager(new LinearLayoutManager(this));
        classify_recycler_adapter.addHeaderView(imageView, 0);
        classifyRecycl.setAdapter(classify_recycler_adapter);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
