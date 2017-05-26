package cn.com.stableloan.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.com.stableloan.R;
import cn.com.stableloan.bean.ProductListBean;
import cn.com.stableloan.ui.activity.ProductDesc;
import cn.com.stableloan.ui.adapter.ListProductAdapter;
import cn.com.stableloan.view.EasyRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @Bind(R.id.recylerview)
    RecyclerView recylerview;
    @Bind(R.id.easylayout)
    EasyRefreshLayout easylayout;

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

    private void setListener() {

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
                            bean.setPname("00"+i);
                            bean.setProduct_introduction("人皆寻梦 梦里不分西东");
                            bean.setMin_algorithm("0.5%");
                            bean.setMinimum_amount("1000");
                            bean.setMaximum_amount("8000");
                            list2.add(bean);
                        }
                        productAdapter.setNewData(list2);

                        easylayout.refreshComplete();
                    }
                },1000);

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
                                bean.setPname(i + "");
                                bean.setProduct_introduction("风火雷电，急急如律令");
                                bean.setMin_algorithm("0.5%");
                                bean.setMinimum_amount("1000");
                                bean.setMaximum_amount("8000");
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
                ProductDesc.launch(getActivity());
            }
        });
    }

    private void getDate() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            ProductListBean.ProductBean bean = new ProductListBean.ProductBean();
            bean.setPname(i + "");
            bean.setProduct_introduction("风火雷电，急急如律令");
            bean.setMin_algorithm("0.5%");
            bean.setMinimum_amount("1000");
            bean.setMaximum_amount("8000");
            list.add(bean);
        }
        View view = setHeaderView();
        productAdapter = new ListProductAdapter(list);
        recylerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        productAdapter.addHeaderView(view, 0);
        recylerview.setAdapter(productAdapter);

    }


    private BGABanner banner;


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
                itemView.setImageResource(model);

                Glide.with(getActivity())
                        .load(model)
                        .dontAnimate()
                        .centerCrop()
                        .into(itemView);
            }
        });*/
        banner.setData(R.drawable.banner_p1, R.drawable.banner_p2, R.drawable.banner_p2);
        //  banner.setData(Arrays.asList("http://p1.wmpic.me/article/2017/05/23/1495505613_uvZxGGWh.jpg","https://imgsa.baidu.com/forum/w%3D580/sign=8ca3b055fddcd100cd9cf829428a47be/eec0723eb13533fa35ef8976add3fd1f41345b22.jpg"), null);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
