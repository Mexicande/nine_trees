package cn.com.stableloan.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.bean.Picture;
import cn.com.stableloan.bean.Product;
import cn.com.stableloan.bean.ProductListBean;
import cn.com.stableloan.bean.TestProductList;
import cn.com.stableloan.ui.activity.ProductClassifyActivity;
import cn.com.stableloan.ui.activity.ProductDesc;
import cn.com.stableloan.ui.adapter.Classify_Recycler_Adapter;
import cn.com.stableloan.ui.adapter.ListProductAdapter;
import cn.com.stableloan.ui.adapter.Recycler_Adapter;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.view.EasyRefreshLayout;
import cn.com.stableloan.view.ScrollSpeedLinearLayoutManger;
import cn.com.stableloan.view.SpacesItemDecoration;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @Bind(R.id.recylerview)
    RecyclerView recylerview;
    @Bind(R.id.easylayout)
    EasyRefreshLayout easylayout;

    private String P_id;
    private ListProductAdapter productAdapter;
    private ArrayList<ProductListBean.ProductBean> list;



    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        getDate();
        setListener();
        return view;
    }

    /**
     * 下拉刷新
     */
    private void setListener() {
        //easylayout.autoRefresh();
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<ProductListBean.ProductBean> list2 = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            ProductListBean.ProductBean bean = new ProductListBean.ProductBean();
                            bean.setProduct_introduction("人皆寻梦 梦里不分西东");
                            bean.setMin_algorithm("0.5%");
                            list2.add(bean);
                        }
                        productAdapter.setNewData(list2);

                        easylayout.refreshComplete();
                    }
                },1000);
                //horizontal 水平 滑动位置
                re_View.smoothScrollToPosition(rc_adapter.getData().size()-1);
            }
        });

        easylayout.setEnableLoadMore(false);


        productAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                recylerview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (productAdapter.getData().size() >= 16) {
                            productAdapter.loadMoreEnd();
                        } else {
                            ArrayList<ProductListBean.ProductBean> list1 = new ArrayList<>();
                            for (int i = 100; i < 110; i++) {
                                ProductListBean.ProductBean bean = new ProductListBean.ProductBean();
                                bean.setMin_algorithm("0.5%");
                                list1.add(bean);
                            }
                            productAdapter.addData(list1);
                            productAdapter.loadMoreComplete();

                        }
                    }

                }, 1000);

            }
        }, recylerview);

        recylerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
               // ProductDesc.launch(getActivity());
                startActivity(new Intent(getActivity(),ProductDesc.class).putExtra("pid",P_id));

            }
        });
    }

    private void getDate() {

        HashMap<String, String> params = new HashMap<>();
        params.put("var", "1");
        JSONObject jsonObject = new JSONObject(params);
        String url="http://47.93.197.52:8080/anwendai/Home/Api/GetProduct";

        OkGo.post(url)
                .tag(getActivity())
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        Gson gson=new Gson();
                        TestProductList list = gson.fromJson(s, TestProductList.class);
                        P_id= list.getProduct().get(0).getPl_id();
                        LogUtils.i("pl_id",P_id);

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            ProductListBean.ProductBean bean = new ProductListBean.ProductBean();
            bean.setMin_algorithm("0.5%");
            list.add(bean);
        }
        View view = setHeaderView();
        productAdapter = new ListProductAdapter(list);
        recylerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        productAdapter.addHeaderView(view, 0);
        SpacesItemDecoration decoration = new SpacesItemDecoration(5);

        recylerview.addItemDecoration(decoration);

        recylerview.setAdapter(productAdapter);

    }


    private BGABanner banner;

    private RecyclerView re_View,classify_recyclView;
    private Recycler_Adapter rc_adapter;
    private Classify_Recycler_Adapter classify_recycler_adapter;



    private View setHeaderView() {

        View view = getActivity().getLayoutInflater().inflate(R.layout.head_layout, null);
        banner = (BGABanner) view.findViewById(R.id.banner_fresco_demo_content);
        banner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                // startActivity(new Intent(getContext(), HtmlActivity.class).putExtra("html", daohang.get(position).getLink()));
            }
        });
      /*  banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {

                Glide.with(getActivity())
                        .load(model)
                        .dontAnimate()
                        .centerCrop()
                        .into(itemView);
            }
        });*/
         banner.setData(R.drawable.banner_p1, R.drawable.banner_p2, R.drawable.banner_p2);
        // banner.setData(Arrays.asList("http://p1.wmpic.me/article/2017/05/23/1495505613_uvZxGGWh.jpg","https://imgsa.baidu.com/forum/w%3D580/sign=8ca3b055fddcd100cd9cf829428a47be/eec0723eb13533fa35ef8976add3fd1f41345b22.jpg"), null);

        List<String>list=new ArrayList<>();


        list.add("http://oqr7ez7w2.bkt.clouddn.com/%E5%AE%89%E7%A8%B3%E8%B4%B7-%E9%A6%96%E9%A1%B5_11.png");
        list.add("http://oqr7ez7w2.bkt.clouddn.com/%E5%AE%89%E7%A8%B3%E8%B4%B7-%E9%A6%96%E9%A1%B5_11.png");
        list.add("http://oqr7ez7w2.bkt.clouddn.com/%E5%AE%89%E7%A8%B3%E8%B4%B7-%E9%A6%96%E9%A1%B5_11.png");
        list.add("http://oqr7ez7w2.bkt.clouddn.com/%E5%AE%89%E7%A8%B3%E8%B4%B7-%E9%A6%96%E9%A1%B5_11.png");
        list.add("http://oqr7ez7w2.bkt.clouddn.com/%E5%AE%89%E7%A8%B3%E8%B4%B7-%E9%A6%96%E9%A1%B5_11.png");
        list.add("http://oqr7ez7w2.bkt.clouddn.com/%E5%AE%89%E7%A8%B3%E8%B4%B7-%E9%A6%96%E9%A1%B5_11.png");
        list.add("http://oqr7ez7w2.bkt.clouddn.com/%E5%AE%89%E7%A8%B3%E8%B4%B7-%E9%A6%96%E9%A1%B5_11.png");
        list.add("http://oqr7ez7w2.bkt.clouddn.com/%E5%AE%89%E7%A8%B3%E8%B4%B7-%E9%A6%96%E9%A1%B5_11.png");

        //热门推荐
        re_View= (RecyclerView) view.findViewById(R.id.linear_recyclerView);
        rc_adapter=new Recycler_Adapter(list);
        re_View.setLayoutManager(new ScrollSpeedLinearLayoutManger(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        re_View.setAdapter(rc_adapter);


        // 分类
        classify_recyclView = (RecyclerView) view.findViewById(R.id.classify_recycler);
        List<String> listUrl=new ArrayList<>();
        listUrl.add("http://oqr7z36cx.bkt.clouddn.com/image/jpg/classify_01.png");
        listUrl.add("http://oqr7z36cx.bkt.clouddn.com/classify_02.png");
        listUrl.add("http://oqr7z36cx.bkt.clouddn.com/image/jpg/classify_03.png");
        /*listUrl.add("http://oqr7z36cx.bkt.clouddn.com/fenlei_default.png");
        listUrl.add("http://oqr7z36cx.bkt.clouddn.com/fenlei_default.png");
        listUrl.add("http://oqr7z36cx.bkt.clouddn.com/fenlei_default.png");
        listUrl.add("http://oqr7z36cx.bkt.clouddn.com/fenlei_default.png");*/

        classify_recycler_adapter=new Classify_Recycler_Adapter(listUrl);
        classify_recyclView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        classify_recyclView.setAdapter(classify_recycler_adapter);
        classify_recyclView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductClassifyActivity.launch(getActivity());

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
