package cn.com.stableloan.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;
import com.mancj.slideup.SlideUp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.bean.ProductListBean;
import cn.com.stableloan.ui.adapter.Recycler_Classify_Adapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends ImmersionFragment {

    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout SwipeRefreshLayout;
    private SlideUp slideUp;

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.classify_recycl)
    RecyclerView classifyRecycl;
    @Bind(R.id.layout_select)
    RelativeLayout layoutSelect;
    @Bind(R.id.slideView)
    LinearLayout slideView;
    List<ProductListBean.ProductBean> list2;
    private Recycler_Classify_Adapter classify_recycler_adapter;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(false)
                .navigationBarColor(R.color.colorPrimary)
                .init();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        initViewTitle();
        initRecyclView();
        setListener();
        return view;
    }

    private void initViewTitle() {
        titleName.setText("产品列表");
        slideUp = new SlideUp.Builder(slideView)
                .withListeners(new SlideUp.Listener.Slide() {
                    @Override
                    public void onSlide(float percent) {

                    }
                })
                .withStartGravity(Gravity.TOP)
                .withLoggingEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
    }

    private void initRecyclView() {
        list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductListBean.ProductBean bean = new ProductListBean.ProductBean();
            bean.setProduct_introduction("人皆寻梦 梦里不分西东");
            bean.setMin_algorithm("0.5%");
            list2.add(bean);
        }
        classify_recycler_adapter = new Recycler_Classify_Adapter(list2);
        classifyRecycl.setLayoutManager(new LinearLayoutManager(getActivity()));
        classifyRecycl.setAdapter(classify_recycler_adapter);

    }

    private void setListener() {
        SwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        classify_recycler_adapter.addData(list2);
                        SwipeRefreshLayout.setRefreshing(false);

                    }
                },1000);
            }

        });
        classify_recycler_adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                classifyRecycl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (classify_recycler_adapter.getData().size() >= 16) {
                            classify_recycler_adapter.loadMoreEnd();
                        } else {
                            ArrayList<ProductListBean.ProductBean> list1 = new ArrayList<>();
                            for (int i = 100; i < 110; i++) {
                                ProductListBean.ProductBean bean = new ProductListBean.ProductBean();
                                bean.setMin_algorithm("0.5%");
                                list1.add(bean);
                            }
                            classify_recycler_adapter.addData(list1);
                            classify_recycler_adapter.loadMoreComplete();

                        }
                    }

                }, 1000);

            }
        }, classifyRecycl);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.layout_select, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_select:
                slideUp.show();
                break;
            case R.id.button:
                slideUp.hide();
                break;
        }
    }
}
