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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mancj.slideup.SlideUp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.Class_ListProductBean;
import cn.com.stableloan.model.SelectProduct;
import cn.com.stableloan.ui.activity.ProductDesc;
import cn.com.stableloan.ui.adapter.Recycler_Classify_Adapter;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.SmoothCheckBox;
import me.wangyuwei.slackloadingview.SlackLoadingView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends ImmersionFragment {

    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout SwipeRefreshLayout;
    @Bind(R.id.tv_save)
    ImageView tvSave;
    @Bind(R.id.checkbox1)
    SmoothCheckBox checkbox1;
    @Bind(R.id.checkbox2)
    SmoothCheckBox checkbox2;
    @Bind(R.id.checkbox3)
    SmoothCheckBox checkbox3;
    @Bind(R.id.checkbox4)
    SmoothCheckBox checkbox4;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.loading_view)
    SlackLoadingView loadingView;
    @Bind(R.id.layoutGo)
    LinearLayout layoutGo;
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
    private Recycler_Classify_Adapter classify_recycler_adapter;

    private final int ACTION_DOWN = 1;
    private final int ACTION_UP = 2;

    private int MORE = 1;

    private int[] s;

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
        loadingView.setVisibility(View.VISIBLE);
        loadingView.start();
        initViewTitle();
        initRecyclView();
        setListener();
        return view;
    }


    private void getDate(int var, final int action) {

        HashMap<String, Integer> params = new HashMap<>();
        params.put("var", var);
        final JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Urls.puk_URL + Urls.product.ProductList)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        loadingView.setVisibility(View.GONE);
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

                                } else {
                                    classify_recycler_adapter.loadMoreEnd();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        loadingView.setVisibility(View.GONE);
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

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.layout_select, R.id.button,R.id.layoutGo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_select:
                slideUp.addSlideListener(new SlideUp.Listener.Visibility() {
                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if(visibility==View.GONE){
                            slideUp.show();
                        }else {
                            slideUp.hide();
                        }
                    }
                });
                break;
            case R.id.button:
                list.clear();
                selectProduct();
                slideUp.hide();
               /* slideUp.addSlideListener(new SlideUp.Listener.Visibility() {
                    @Override
                    public void onVisibilityChanged(int visibility) {

                    }
                });*/
            case R.id.layoutGo:
                slideUp.hide();

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
        LogUtils.i("var-----", Arrays.toString(iarr));
    }

    private void selectGetProduct(int[] arr, String stat) {
        SelectProduct selectProduct = new SelectProduct(arr, stat);
        Gson gson = new Gson();
        String json = gson.toJson(selectProduct);
        OkGo.post(Urls.puk_URL + Urls.product.ProDuctScreening)
                .tag(this)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        loadingView.setVisibility(View.GONE);
                        if (s != null) {
                            try {
                                JSONObject object = new JSONObject(s);
                                String success = object.getString("isSuccess");
                                if (success.equals("1")) {
                                    Gson gson = new Gson();
                                    Class_ListProductBean productBean = gson.fromJson(s, Class_ListProductBean.class);
                                    classify_recycler_adapter.setNewData(productBean.getProduct());
                                    classify_recycler_adapter.loadMoreEnd(false);

                                } else {
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
                        loadingView.setVisibility(View.GONE);

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
