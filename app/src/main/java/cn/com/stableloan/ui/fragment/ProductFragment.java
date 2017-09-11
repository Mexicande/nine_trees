package cn.com.stableloan.ui.fragment;


import android.app.Activity;
import android.content.Intent;
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
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.coorchice.library.SuperTextView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mancj.slideup.SlideUp;
import com.qiniu.android.utils.Json;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.bean.IdentityProduct;
import cn.com.stableloan.model.Class_ListProductBean;
import cn.com.stableloan.model.PicStatusEvent;
import cn.com.stableloan.model.SelectProduct;
import cn.com.stableloan.model.TagFlowBean;
import cn.com.stableloan.ui.activity.HtmlActivity;
import cn.com.stableloan.ui.activity.ProductDesc;
import cn.com.stableloan.ui.adapter.Recycler_Classify_Adapter;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.flowlayout_tag.FlowLayout;
import cn.com.stableloan.view.flowlayout_tag.TagAdapter;
import cn.com.stableloan.view.flowlayout_tag.TagFlowLayout;
import cn.com.stableloan.view.statuslayout.FadeViewAnimProvider;
import cn.com.stableloan.view.statuslayout.StateLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends ImmersionFragment {

    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout SwipeRefreshLayout;
    @Bind(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @Bind(R.id.tag_flowlayout)
    TagFlowLayout tagFlowlayout;

    public  static SlideUp slideUp;

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
    private String[] mVals = new String[]
            {"上班族", "学生党", "逍遥客 ", "企业主"};

    private  List<TagFlowBean.DataBean> tagData=new ArrayList<>();
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
                .navigationBarColor(R.color.md_grey_900)
                .statusBarAlpha(0.3f)
                .init();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);

        stateLayout = (StateLayout) view.findViewById(R.id.stateLayout);
        stateLayout.setViewSwitchAnimProvider(new FadeViewAnimProvider());
        initViewTitle();
        getTagFlowData();
        initRecyclView();
        setListener();
        getDate(1, ACTION_DOWN);
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("产品列表", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
            //记录事件
        ZhugeSDK.getInstance().track(getActivity(), "loanpage", eventObject);

        return view;
    }

    /**
     * Product TagFlow
     *
     */
    private void getTagFlowData() {
        OkGo.<String>post(Urls.Ip_url+Urls.product.ProTagFlow)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if(s!=null){
                            Gson gson=new Gson();
                            TagFlowBean flowBean = gson.fromJson(s, TagFlowBean.class);
                            if(flowBean.getError_code()==0){
                                tagData= flowBean.getData();
                                List<String>list=new ArrayList<String>();
                                for(TagFlowBean.DataBean tag:tagData){
                                    list.add(tag.getName());
                                }
                                tagFlowlayout.setAdapter(new TagAdapter<String>(list) {
                                    @Override
                                    public View getView(FlowLayout parent, int position, String s) {
                                        LinearLayout inflate = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.tag_item, null);
                                        TextView mTextView= (TextView) inflate.findViewById(R.id.tv_tag);
                                        inflate.removeView(mTextView);
                                        mTextView.setText(s);
                                        return mTextView;
                                    }
                                });

                            }else {
                                ToastUtils.showToast(getActivity(),flowBean.getError_message());
                            }

                        }

                    }
                });

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
                                            stateLayout.showContentView();
                                            classify_recycler_adapter.setNewData(json.getProduct());
                                            break;
                                        case ACTION_UP :
                                            stateLayout.showContentView();
                                            if(json.getProduct().size()>0){
                                                classify_recycler_adapter.addData(json.getProduct());
                                            }
                                            classify_recycler_adapter.loadMoreComplete();
                                            break;
                                    }
                                    if (var == 1) {

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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(getActivity(), "网络异常");

                            }
                        });
                    }
                });
    }

    @Subscribe
    public void onPicSatus(IdentityProduct event){
        int msg = event.msg;
        String[] s = new String[]{String.valueOf(msg)};
        idFlowlayout.getAdapter().setSelectedList(msg-1);
        selectProduct(s);

    }

   /*
    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }
*/

    private void initViewTitle() {
        titleName.setText("产品列表");
        slideUp = new SlideUp.Builder(slideView)
                .withStartGravity(Gravity.TOP)
                .withLoggingEnabled(true)
                .withAutoSlideDuration(1)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
        idFlowlayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView view = (TextView) getActivity().getLayoutInflater().inflate(R.layout.tv, null);
                view.setText(s);

                return view;
            }
        });
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
                        MORE=1;
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
                if(tagData.size()==0){
                    getTagFlowData();
                }
                break;
            case R.id.button:
                slideUp.hide();
                Set<Integer> selectedList = idFlowlayout.getSelectedList();
                Set<Integer> selectedList1 = tagFlowlayout.getSelectedList();
                if(selectedList.size()>0||selectedList1.size()>0){
                    selectProduct(null);
                }
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
    private int s = 0;

    private void selectProduct(String[] ident) {

        HashMap<String, String[]> params = new HashMap<>();
        if(ident==null){
            stateLayout.showProgressView();
            Set<Integer> idenlist = idFlowlayout.getSelectedList();
            if(idenlist.size()>0){
                Integer[]arr=new Integer[1];

                Integer[] i = idenlist.toArray(arr);

                String string = String.valueOf(i[0]+1);

                String[]str=new String[]{string};
                params.put("identity", str);
            }else {
                String[]str1=new String[]{};
                params.put("identity",str1);
            }

        Set<Integer> taglist = tagFlowlayout.getSelectedList();
        List<String> strlist=new ArrayList<>();
        for(Integer s:taglist){
            strlist.add(String.valueOf(s+1));
        }
        String []strs=new String[strlist.size()];

        String[] strings = strlist.toArray(strs);

            if(strings.length>0){
                params.put("labels",strings);
            }else {
                String[]str1=new String[]{};
                params.put("labels",str1);
            }
        }else {
            params.put("identity",ident);
            String[]str1=new String[]{};
            params.put("labels",str1);
        }
         JSONObject jsonObject = new JSONObject(params);
        OkGo.<String>post(Urls.Ip_url+Urls.product.ProductSelect)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        stateLayout.showContentView();
                        try {
                            JSONObject json=new JSONObject(s);
                            int error_code = json.getInt("error_code");
                            if(error_code==0){
                                String data = json.getString("data");
                                Gson gson=new Gson();
                                Class_ListProductBean.ProductBean[] productBeen = gson.fromJson(data, Class_ListProductBean.ProductBean[].class);

                                if(productBeen.length==0){
                                    classify_recycler_adapter.setNewData(null);
                                    classify_recycler_adapter.setEmptyView(notDataView);
                                }else {
                                    List<Class_ListProductBean.ProductBean> been = Arrays.asList(productBeen);
                                    List<Class_ListProductBean.ProductBean> arrayList = new ArrayList<Class_ListProductBean.ProductBean>(been);
                                    classify_recycler_adapter.setNewData(arrayList);
                                    classify_recycler_adapter.disableLoadMoreIfNotFullPage();
                                    classifyRecycl.smoothScrollToPosition(0);
                                }
                            }else {
                                classify_recycler_adapter.setNewData(null);
                                classify_recycler_adapter.setEmptyView(notDataView);
                                String error_message = json.getString("error_message");
                                ToastUtils.showToast(getActivity(),error_message);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                classify_recycler_adapter.setEmptyView(errorView);
                                ToastUtils.showToast(getActivity(), "服务器异常");
                            }
                        });
                    }
                });
    }
/*

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
                                    classify_recycler_adapter.setNewData(null);
                                    classify_recycler_adapter.setEmptyView(notDataView);
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(getActivity(), "网络异常");

                            }
                        });

                    }
                });

    }

    List<Integer> list = new ArrayList<>();

    private void addCheck(int s) {
        if (s != 0) {
            list.add(s);
        }
    }
*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            backHandlerInterface = (BackHandlerInterface) getActivity();
        }catch (Exception e){
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
    public void onStart() {
        super.onStart();
        backHandlerInterface.setSelectedFragment(this);
    }

    private boolean mHandledPress = false;
    public boolean onBackPressed(){
        if (!mHandledPress){
                mHandledPress = true;
                return true;
        }
        return false;
    }
}
