package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.umeng.analytics.MobclickAgent;

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
import cn.com.stableloan.model.clsaa_special.Class_Special;
import cn.com.stableloan.model.home.SpecialClassBean;
import cn.com.stableloan.ui.adapter.Recycler_Classify_Adapter;
import cn.com.stableloan.ui.adapter.Special_FootAdapter;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.view.SpacesItemDecoration;
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
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout SwipeRefreshLayout;
    private Recycler_Classify_Adapter classify_recycler_adapter;

    private Class_Special classBean;
    private List<ProductListBean.ProductBean> list2;
    private SpecialClassBean.DataBean specialClassBean;
    private ImageView mImageView;
    private Context mContext;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProductClassifyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_classify);
        ButterKnife.bind(this);
        mContext=this;
        specialClassBean = (SpecialClassBean.DataBean) getIntent().getSerializableExtra("class_product");

        initView();
        getDate();
        setListener();
    }

    private void getDate() {
        if(specialClassBean!=null){
            int id = specialClassBean.getId();
            titleName.setText(specialClassBean.getProject_name());
            ivBack.setVisibility(View.VISIBLE);
            if (specialClassBean != null && id !=0) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id",String.valueOf( id));
                final JSONObject jsonObject = new JSONObject(params);
                OkGo.post(Urls.NEW_Ip_url + Urls.product.ClassProduct)
                        .tag(this)
                        .upJson(jsonObject.toString())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                if (s != null) {
                                    Gson gson=new Gson();
                                    classBean = gson.fromJson(s, Class_Special.class);
                                    if(classBean.getError_code()==0){
                                        RequestOptions options = new RequestOptions()
                                                .fitCenter()
                                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                                        Glide.with(ProductClassifyActivity.this).load(classBean.getData().getProject().get(0).getProject_logo()).apply(options).into(mImageView);
                                        mProject_name.setText(classBean.getData().getProject().get(0).getBody_project());
                                        mBody_Project.setText(classBean.getData().getProject().get(0).getBody());
                                        classify_recycler_adapter.setNewData(classBean.getData().getProduct());
                                        if(classBean.getData().getMdse().size()!=0){
                                            mSpecial_footAdapter.setNewData(classBean.getData().getMdse());
                                            View foot = getLayoutInflater().inflate(R.layout.special_foot_layout, null);
                                            mFootRecycler= (RecyclerView) foot.findViewById(R.id.foot_recycler);
                                            TextView viewById = (TextView) foot.findViewById(R.id.list_title);
                                            classify_recycler_adapter.removeAllFooterView();
                                            classify_recycler_adapter.addFooterView(foot);
                                            viewById.setText(classBean.getData().getTitle());
                                            mFootRecycler.setLayoutManager(new LinearLayoutManager(ProductClassifyActivity.this));
                                            mFootRecycler.setAdapter(mSpecial_footAdapter);
                                            mFootRecycler.addOnItemTouchListener(new OnItemClickListener() {
                                                @Override
                                                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                                    String token = (String) SPUtils.get(mContext, Urls.lock.TOKEN, "1");
                                                    if ("1".equals(token)||token == null) {
                                                        startActivity(new Intent(mContext, LoginActivity.class).putExtra("ProductClassifyActivity", classBean.getData().getMdse().get(position)));
                                                    } else {
                                            /*            JSONObject eventObject = new JSONObject();
                                                        try {
                                                            eventObject.put("persmaterials2", "");
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
//记录事件
                                                        ZhugeSDK.getInstance().track(ProductClassifyActivity.this, "专题详情页--商品+"+classBean.getData().getMdse().get(position).getMdse_name(), eventObject);*/
                                                        startActivity(new Intent(ProductClassifyActivity.this, HtmlActivity.class).putExtra("class", classBean.getData().getMdse().get(position)));
                                                    }

                                                }
                                            });
                                        }
                                    }else {
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
            /*    JSONObject eventObject = new JSONObject();
                try {
                    eventObject.put("persmaterials2", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//记录事件
                ZhugeSDK.getInstance().track(ProductClassifyActivity.this, "专题详情页--产品+"+classBean.getData().getProduct().get(position).getPname(), eventObject);
*/

                startActivity(new Intent(ProductClassifyActivity.this, ProductDesc.class).putExtra("pid", classBean.getData().getProduct().get(position).getId()));


            }
        });

    }

    private RecyclerView mFootRecycler;

    private Special_FootAdapter mSpecial_footAdapter;

    private  TextView mProject_name,mBody_Project;
    private void initView() {
        View view = getLayoutInflater().inflate(R.layout.class_top_imageview, null);
        mImageView = (ImageView) view.findViewById(R.id.ic_default_top);
        mProject_name= (TextView) view.findViewById(R.id.project_name);
        mBody_Project= (TextView) view.findViewById(R.id.body_project);

     /*   View foot = getLayoutInflater().inflate(R.layout.special_foot_layout, null);

        mFootRecycler= (RecyclerView) foot.findViewById(R.id.foot_recycler);*/
    /*    imageView = new ImageView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(lp);*/
        classify_recycler_adapter = new Recycler_Classify_Adapter(R.layout.special_product_item,null);
        classifyRecycl.setLayoutManager(new LinearLayoutManager(this));
      /*  SpacesItemDecoration decoration = new SpacesItemDecoration(2);
        classifyRecycl.addItemDecoration(decoration);*/
        classify_recycler_adapter.addHeaderView(view, 0);
       // classify_recycler_adapter.addFooterView(foot);
        mSpecial_footAdapter=new Special_FootAdapter(null);

        classifyRecycl.setAdapter(classify_recycler_adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if(requestCode==10000){
            switch (resultCode){
                case 1:
                    int id1 = data.getIntExtra("id", -1);
                    if(id1!=-1){
                        startActivity(new Intent(ProductClassifyActivity.this, HtmlActivity.class).putExtra("pid", classBean.getData().getMdse().get(id1)));
                    }
                    break;
            }
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Util.isOnMainThread()&&!this.isFinishing())
        {
            Glide.with(this).pauseRequests();
        }

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
