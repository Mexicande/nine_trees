package cn.com.stableloan.ui.fragment;


import android.content.Intent;
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
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.coorchice.library.SuperTextView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mancj.slideup.SlideUp;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.Class_ListProductBean;
import cn.com.stableloan.model.SelectProduct;
import cn.com.stableloan.ui.activity.HtmlActivity;
import cn.com.stableloan.ui.activity.ProductDesc;
import cn.com.stableloan.ui.adapter.Recycler_Classify_Adapter;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.SmoothCheckBox;
import cn.com.stableloan.view.statuslayout.FadeViewAnimProvider;
import cn.com.stableloan.view.statuslayout.StateLayout;
import cn.com.stableloan.view.statuslayout.ViewAnimProvider;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends ImmersionFragment {

    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout SwipeRefreshLayout;
    @Bind(R.id.checkbox1)
    SmoothCheckBox checkbox1;
    @Bind(R.id.checkbox2)
    SmoothCheckBox checkbox2;
    @Bind(R.id.checkbox3)
    SmoothCheckBox checkbox3;
    @Bind(R.id.checkbox4)
    SmoothCheckBox checkbox4;
    @Bind(R.id.layout_select)
    SuperTextView layoutSelect;
    private SlideUp slideUp;

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.classify_recycl)
    RecyclerView classifyRecycl;
    @Bind(R.id.slideView)
    LinearLayout slideView;
    private Recycler_Classify_Adapter classify_recycler_adapter;

    private final int ACTION_DOWN = 1;
    private final int ACTION_UP = 2;

    private int MORE = 1;

    private int[] s;

    public ProductFragment() {
        // Required empty public constructor
    }

    private StateLayout stateLayout;

    private View errorView;
    private View notDataView;

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
        stateLayout= (StateLayout) view.findViewById(R.id.stateLayout);
        stateLayout.setViewSwitchAnimProvider(new FadeViewAnimProvider());
        initViewTitle();
        initRecyclView();
        setListener();

        return view;
    }

    private void getDate(final int var, final int action) {
        if (var == 1) {
            stateLayout.showProgressView();
         }
        HashMap<String, Integer> params = new HashMap<>();
        params.put("var", var);
        final JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Urls.puk_URL + Urls.product.ProductList)
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
                                    Class_ListProductBean json = gson.fromJson(s, Class_ListProductBean.class);
                                    switch (action) {
                                        case ACTION_DOWN:
                                            classify_recycler_adapter.setNewData(json.getProduct());
                                            break;
                                        default:
                                            classify_recycler_adapter.addData(json.getProduct());
                                            classify_recycler_adapter.loadMoreComplete();
                                            break;
                                    }
                                    if (var == 1) {
                                        stateLayout.setVisibility(View.GONE);
                                    }
                                } else {
                                    classify_recycler_adapter.loadMoreEnd();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (var == 1) {
                            stateLayout.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        stateLayout.showErrorView();
                       /* classify_recycler_adapter .setNewData(null);
                        classify_recycler_adapter.setEmptyView(notDataView);*/
                    }
                });
    }


    private boolean isGetData = false;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            int plat = (int) SPUtils.get(getActivity(), "plat", 0);
            if (plat != 0) {
                int[] s = {};
                switch (plat) {
                    case 1:
                        s = new int[]{0};
                        break;
                    case 2:
                        s = new int[]{1};
                        break;
                    case 3:
                        s = new int[]{2};
                        break;
                    case 4:
                        s = new int[]{3};
                        break;
                }
                selectGetProduct(s, "1");
                SPUtils.remove(getActivity(), "plat");
            } else {
                getDate(1, ACTION_DOWN);
            }
            //   这里可以做网络请求或者需要的数据刷新操作
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isGetData) {
            //   这里可以做网络请求或者需要的数据刷新操作
            int plat = (int) SPUtils.get(getActivity(), "plat", 0);
            if (plat != 0) {
                int[] s = {};
                switch (plat) {
                    case 1:
                        s = new int[]{0};
                        break;
                    case 2:
                        s = new int[]{1};
                        break;
                    case 3:
                        s = new int[]{2};
                        break;
                    case 4:
                        s = new int[]{3};
                        break;
                    default:
                        break;
                }
                selectGetProduct(s, "1");
                SPUtils.remove(getActivity(), "plat");
            } else {
                getDate(1, ACTION_DOWN);
            }
            isGetData = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
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
      /*  int plat = (int) SPUtils.get(getActivity(), "plat", 0);
        if (plat != 0) {
            int[] s = {};
            switch (plat) {
                case 1:
                    s = new int[]{0};
                    break;
                case 2:
                    s = new int[]{1};
                    break;
                case 3:
                    s = new int[]{2};
                    break;
                case 4:
                    s = new int[]{3};
                    break;
            }
            selectGetProduct(s, "1");
            SPUtils.remove(getActivity(), "plat");
        } else {
            getDate(1, ACTION_DOWN);
        }*/
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.view_empty, (ViewGroup) classifyRecycl.getParent(), false);
        errorView = getActivity().getLayoutInflater().inflate(R.layout.view_error, (ViewGroup) classifyRecycl.getParent(), false);

    }

    private void initRecyclView() {

        classify_recycler_adapter = new Recycler_Classify_Adapter(null);
        classifyRecycl.setLayoutManager(new LinearLayoutManager(getActivity()));
        classifyRecycl.setAdapter(classify_recycler_adapter);
        classifyRecycl.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                startActivity(new Intent(getActivity(), ProductDesc.class).putExtra("pid", classify_recycler_adapter.getData().get(position).getId()));
            }
        });
    }


    private void setListener() {
        SwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDate(1, ACTION_DOWN);
                        SwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });


        classify_recycler_adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                classifyRecycl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MORE++;
                        getDate(MORE, ACTION_UP);

                    }
                }, 1000);
            }
        }, classifyRecycl);

        stateLayout.setErrorAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(1, ACTION_DOWN);

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.layout_select, R.id.button, R.id.layoutGo, R.id.reset,R.id.cardBank})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_select:
                boolean visible = slideUp.isVisible();
                if (visible) {
                    slideUp.hide();
                } else {
                    slideUp.show();
                }

                break;
            case R.id.cardBank:
                startActivity(new Intent(getActivity(), HtmlActivity.class).putExtra("bank",Urls.CardBack));
                break;
            case R.id.button:
                list.clear();
                slideUp.hide();
                selectProduct();
                break;
            case R.id.layoutGo:
                slideUp.hide();
                break;

            case R.id.reset:
                checkbox1.setChecked(false);
                checkbox2.setChecked(false);
                checkbox3.setChecked(false);
                checkbox4.setChecked(false);
                getDate(1, ACTION_DOWN);
                slideUp.hide();
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    private void selectProduct() {
        int i1 = checkbox1.isChecked() ? 0 : 4;
        if (i1 == 0) {
            list.add(0);
        }
        addCheck(checkbox2.isChecked() ? 1 : 0);
        addCheck(checkbox3.isChecked() ? 2 : 0);
        addCheck(checkbox4.isChecked() ? 3 : 0);
        int[] iarr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iarr[i] = list.get(i);
        }
        if (iarr.length != 0) {
            selectGetProduct(iarr, "2");
        }
    }

    private void selectGetProduct(int[] arr, String stat) {
        stateLayout.showProgressView();
        SelectProduct selectProduct = new SelectProduct(arr, stat);
        Gson gson = new Gson();
        String json = gson.toJson(selectProduct);
        OkGo.post(Urls.puk_URL + Urls.product.ProDuctScreening)
                .tag(this)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            try {
                                JSONObject object = new JSONObject(s);
                                String success = object.getString("isSuccess");
                                if (success.equals("1")) {
                                    Gson gson = new Gson();
                                    Class_ListProductBean productBean = gson.fromJson(s, Class_ListProductBean.class);
                                    classify_recycler_adapter.setNewData(productBean.getProduct());
                                    classifyRecycl.smoothScrollToPosition(0);
                                    stateLayout.showContentView();
                                } else {
                                    classify_recycler_adapter .setNewData(null);
                                    stateLayout.showEmptyView();
                                    ToastUtils.showToast(getActivity(), "没有符合的产品");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showToast(getActivity(),"网络异常");
                        stateLayout.showErrorView();

                    }
                });

    }

    List<Integer> list = new ArrayList<>();

    private void addCheck(int s) {
        if (s != 0) {
            list.add(s);
        }

    }


}
