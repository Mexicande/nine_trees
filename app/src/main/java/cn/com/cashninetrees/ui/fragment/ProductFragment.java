package cn.com.cashninetrees.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mancj.slideup.SlideUp;
import com.qiniu.android.utils.Json;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.bean.IdentityProduct;
import cn.com.cashninetrees.model.Class_ListProductBean;
import cn.com.cashninetrees.model.TagFlowBean;
import cn.com.cashninetrees.model.clsaa_special.Class_Special;
import cn.com.cashninetrees.model.home.ProductList_Param;
import cn.com.cashninetrees.ui.activity.ProductDesc;
import cn.com.cashninetrees.ui.activity.vip.VipActivity;
import cn.com.cashninetrees.ui.adapter.Recycler_Classify_Adapter;
import cn.com.cashninetrees.utils.ActivityUtils;
import cn.com.cashninetrees.utils.CommonUtil;
import cn.com.cashninetrees.utils.CommonUtils;
import cn.com.cashninetrees.utils.LogUtils;
import cn.com.cashninetrees.utils.ToastUtils;
import cn.com.cashninetrees.view.flowlayout_tag.FlowLayout;
import cn.com.cashninetrees.view.flowlayout_tag.TagAdapter;
import cn.com.cashninetrees.view.flowlayout_tag.TagFlowLayout;
import cn.com.cashninetrees.view.seekbar.SeekbarWithIntervals;
import cn.com.cashninetrees.view.statuslayout.FadeViewAnimProvider;
import cn.com.cashninetrees.view.statuslayout.StateLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * @author apple
 * 产品列表
 */
public class ProductFragment extends ImmersionFragment {

    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout SwipeRefreshLayout;
    @Bind(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @Bind(R.id.tag_flowlayout)
    TagFlowLayout tagFlowlayout;

    public static SlideUp slideUp;

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.classify_recycl)
    RecyclerView classifyRecycl;
    @Bind(R.id.slideView)
    LinearLayout slideView;
    @Bind(R.id.seekbarWithIntervals)
    SeekbarWithIntervals seekbarWithIntervals;
    private Recycler_Classify_Adapter classify_recycler_adapter;

    private final int ACTION_DOWN = 1;
    private final int ACTION_UP = 2;

    private int AMOUT=0;
    private int MORE = 1;
    private String[] mVals = new String[]
            {"上班族", "逍遥客 ", "企业主"};

    private List<TagFlowBean.DataBean> tagData = new ArrayList<>();

    public ProductFragment() {
        // Required empty public constructor
    }


    private View errorView;
    private View notDataView;

    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(false)
               // .navigationBarColor(R.color.md_grey_900)
                .statusBarAlpha(0.3f)
                .init();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        initViewTitle();
        getTagFlowData();
        initRecyclView();
        setListener();
        getDate(1, ACTION_DOWN);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        return view;
    }

    /**
     * Product TagFlow
     */
    private void getTagFlowData() {
        OkGo.<String>post(Urls.Ip_url + Urls.product.ProTagFlow)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            TagFlowBean flowBean = gson.fromJson(s, TagFlowBean.class);
                            if (flowBean.getError_code() == 0) {
                                tagData = flowBean.getData();
                                List<String> list = new ArrayList<String>();
                                for (TagFlowBean.DataBean tag : tagData) {
                                    list.add(tag.getName());
                                }
                                tagFlowlayout.setAdapter(new TagAdapter<String>(list) {
                                    @Override
                                    public View getView(FlowLayout parent, int position, String s) {
                                        LinearLayout inflate = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.tag_item, null);
                                        TextView mTextView = (TextView) inflate.findViewById(R.id.tv_tag);
                                        inflate.removeView(mTextView);
                                        mTextView.setText(s);

                                        return mTextView;
                                    }
                                });

                            } else {
                                ToastUtils.showToast(getActivity(), flowBean.getError_message());
                            }

                        }

                    }
                });

    }

    private void getDate(final int var, final int action) {
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
                                if (("1").equals(success)) {
                                    Gson gson = new Gson();
                                    Class_ListProductBean json = gson.fromJson(s, Class_ListProductBean.class);
                                    switch (action) {
                                        case ACTION_DOWN:
                                           classify_recycler_adapter.setNewData(json.getProduct());
                                            break;
                                        case ACTION_UP:
                                            if (json.getProduct().size() > 0) {
                                                classify_recycler_adapter.addData(json.getProduct());
                                            }
                                            classify_recycler_adapter.loadMoreComplete();
                                            break;
                                        default:
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
                    }
                });
    }

    @Subscribe
    public void onPicSatus(IdentityProduct event) {
        int msg = event.msg;
        String[] s = new String[]{String.valueOf(msg)};
        if(CommonUtil.isNetworkAvailable(getActivity())){
            idFlowlayout.getAdapter().setSelectedList(msg - 1);
            AMOUT=0;
            if(event.amount!=100){
                selectProduct(s, event.amount);
            }else {
                selectProduct(s, 0);
            }
        }

    }



    private void initViewTitle() {
        titleName.setText("产品列表");
        slideUp = new SlideUp.Builder(slideView)
                .withStartGravity(Gravity.TOP)
                .withLoggingEnabled(true)
                .withAutoSlideDuration(1)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
        List<String> seekbarIntervals = getIntervals();

        seekbarWithIntervals.setIntervals(seekbarIntervals);




        idFlowlayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                LinearLayout inflate = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.tag_item, null);
                TextView mTextView = (TextView) inflate.findViewById(R.id.tv_tag);
                inflate.removeView(mTextView);
                mTextView.setText(s);
                return mTextView;
            }
        });
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.view_empty, (ViewGroup) classifyRecycl.getParent(), false);
        errorView = getActivity().getLayoutInflater().inflate(R.layout.view_error, (ViewGroup) classifyRecycl.getParent(), false);


    }



    private List<String> getIntervals() {
        return new ArrayList<String>() {{
            add("");
            add("1000");
            add("2000");
            add("5000");
            add("8000");
            add("");
        }};
    }
    private void initRecyclView() {

        classify_recycler_adapter = new Recycler_Classify_Adapter(R.layout.product_trem,null);
        classifyRecycl.setLayoutManager(new LinearLayoutManager(getActivity()));
        classifyRecycl.setAdapter(classify_recycler_adapter);
        classifyRecycl.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                startActivity(new Intent(getActivity(), ProductDesc.class).putExtra("pid", classify_recycler_adapter.getData().get(position).getId()));
            }
        });
        //header
        View view = getActivity().getLayoutInflater().inflate(R.layout.product_header_layout, null);

        //footer
        View footer = getActivity().getLayoutInflater().inflate(R.layout.product_footer_layout, null);

        classify_recycler_adapter.addHeaderView(view);
        classify_recycler_adapter.addFooterView(footer);
        ImageView mCloseHeader = view.findViewById(R.id.iv_close);

        mCloseHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classify_recycler_adapter.removeAllHeaderView();
            }
        });
        TextView tvOpen = view.findViewById(R.id.tv_vip);
        tvOpen.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(VipActivity.class);
            }
        });

        classify_recycler_adapter.getFooterLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(VipActivity.class);
            }
        });



    }


    private void setListener() {

        seekbarWithIntervals.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                AMOUT=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        SwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDate(1, ACTION_DOWN);
                        if(SwipeRefreshLayout!=null){
                            SwipeRefreshLayout.setRefreshing(false);
                        }
                        MORE = 1;
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

    @OnClick({R.id.layout_select, R.id.button, R.id.layoutGo, R.id.reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_select:

                boolean visible = slideUp.isVisible();
                if (visible) {
                    slideUp.hide();
                } else {
                    slideUp.show();
                }
                if (tagData.size() == 0) {
                    getTagFlowData();
                }
                break;
            case R.id.button:
                slideUp.hide();
                selectProduct(null,0);
                break;
            case R.id.layoutGo:
                slideUp.hide();
                break;
            case R.id.reset:
                getDate(1, ACTION_DOWN);
                slideUp.hide();
                idFlowlayout.onChanged();
                tagFlowlayout.onChanged();
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    private List<Integer> amount=new ArrayList<>();
    private void selectProduct(String[] ident,int money) {
        ProductList_Param param=new ProductList_Param();
        amount.add(500);
        amount.add(1000);
        amount.add(2000);
        amount.add(5000);
        amount.add(8000);
        amount.add(10000);
        param.setAmount(amount.get(AMOUT));
        if(AMOUT==0){
            param.setAmount(money);
        }
        if (ident == null) {
            Set<Integer> idenlist = idFlowlayout.getSelectedList();
            if (idenlist.size() > 0) {
                Integer[] arr = new Integer[1];
                Integer[] i = idenlist.toArray(arr);
                if(i[0]>=1){
                    String string = String.valueOf(i[0] + 2);
                    String[] str = new String[]{string};
                    param.setIdentity(Arrays.asList(str));
                }else {
                    String string = String.valueOf(i[0] + 1);
                    String[] str = new String[]{string};
                    param.setIdentity(Arrays.asList(str));
                }
            } else {
                String[] str1 = new String[]{};
                param.setIdentity(Arrays.asList(str1));
            }
            Set<Integer> taglist = tagFlowlayout.getSelectedList();
            List<String> strlist = new ArrayList<>();
            for (Integer s : taglist) {
                strlist.add(String.valueOf(s + 1));
            }
            String[] strs = new String[strlist.size()];
            String[] strings = strlist.toArray(strs);
            if (strings.length > 0) {
                param.setLabels(Arrays.asList(strings));
            } else {
                String[] str1 = new String[]{};
                param.setLabels(Arrays.asList(str1));
            }
        } else {
            param.setIdentity(Arrays.asList(ident));
            String[] str1 = new String[]{};
            param.setLabels(Arrays.asList(str1));
        }
        Gson gson=new Gson();
        String json = gson.toJson(param);
        OkGo.<String>post(Urls.NEW_Ip_url + Urls.product.ProductSelect)
                .tag(this)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
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
                                    List<Class_Special.DataBean.ProductBean> been = Arrays.asList(productBeen);
                                    List<Class_Special.DataBean.ProductBean> arrayList = new ArrayList<Class_Special.DataBean.ProductBean>(been);
                                    classify_recycler_adapter.setNewData(arrayList);
                                    classify_recycler_adapter.disableLoadMoreIfNotFullPage();
                                    classifyRecycl.smoothScrollToPosition(0);
                                }
                            } else {
                                classify_recycler_adapter.setNewData(null);
                                classify_recycler_adapter.setEmptyView(notDataView);
                                String errorMessage = json.getString("errorMessage");
                                ToastUtils.showToast(getActivity(), errorMessage);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                                classify_recycler_adapter.setEmptyView(errorView);
                                ToastUtils.showToast(getActivity(), "服务器异常");
                            }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            backHandlerInterface = (BackHandlerInterface) getActivity();
        } catch (Exception e) {
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        }
    }

    protected BackHandlerInterface backHandlerInterface;

    public interface BackHandlerInterface {
        public void setSelectedFragment(ProductFragment backHandledFragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandlerInterface)) {
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        } else {
            backHandlerInterface = (BackHandlerInterface) getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        backHandlerInterface.setSelectedFragment(this);
    }

    private boolean mHandledPress = false;

    public boolean onBackPressed() {
        if (!mHandledPress) {
            mHandledPress = true;
            return true;
        }
        return false;
    }
}
