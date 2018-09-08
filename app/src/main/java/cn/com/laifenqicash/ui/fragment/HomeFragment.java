package cn.com.laifenqicash.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.com.laifenqicash.R;
import cn.com.laifenqicash.api.ApiService;
import cn.com.laifenqicash.api.Urls;
import cn.com.laifenqicash.bean.IdentityProduct;
import cn.com.laifenqicash.common.Api;
import cn.com.laifenqicash.common.Constants;
import cn.com.laifenqicash.interfaceutils.OnRequestDataListener;
import cn.com.laifenqicash.model.Banner_HotBean;
import cn.com.laifenqicash.model.home.Hot_New_Product;
import cn.com.laifenqicash.model.home.Seckill_Bean;
import cn.com.laifenqicash.model.integarl.AdvertisingBean;
import cn.com.laifenqicash.ui.activity.HtmlActivity;
import cn.com.laifenqicash.ui.activity.MainActivity;
import cn.com.laifenqicash.ui.activity.NoticeActivity;
import cn.com.laifenqicash.ui.activity.ProductDesc;
import cn.com.laifenqicash.ui.adapter.ListProductAdapter;
import cn.com.laifenqicash.ui.adapter.Recycler_Adapter;
import cn.com.laifenqicash.ui.fragment.dialogfragment.AdialogFragment;
import cn.com.laifenqicash.ui.fragment.dialogfragment.WeChatDialogFragment;
import cn.com.laifenqicash.utils.OnClickStatistics;
import cn.com.laifenqicash.utils.SPUtil;
import cn.com.laifenqicash.utils.TimeUtils;
import cn.com.laifenqicash.utils.ToastUtils;
import cn.com.laifenqicash.view.EasyRefreshLayout;
import cn.com.laifenqicash.view.MyDecoration;
import cn.com.laifenqicash.view.RecyclerIntervalDecoration;
import cn.com.laifenqicash.view.countdownview.CountdownView;
import cn.com.laifenqicash.view.countdownview.EasyCountDownView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{


    @Bind(R.id.recylerview)
    RecyclerView recylerview;
    @Bind(R.id.easylayout)
    EasyRefreshLayout easylayout;
    @Bind(R.id.iv_notice)
    ImageView ivNotice;
    @Bind(R.id.select_money)
    RelativeLayout selectMoney;

    private ListProductAdapter productAdapter;
    private Seckill_Bean seckillBean;
    int ACTION = 1;
    private List<String> list;
    private String mToken;
    private  boolean mStateEnable=true;
    private String status;
    private String url;
    private String name;
    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initDialog();
        getDate();
        setListener();
        getHelp();


        return view;
    }

    private void getHelp() {
        ApiService.GET_SERVICE(Urls.HOME_FRAGMENT.HELP, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject object = data.getJSONObject("data");
                     status = object.getString("status");
                     url = object.getString("url");
                     name = object.getString("name");
                     SPUtil.putString(getActivity(),"wechat",name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }


    /**
     * 秒杀活动
     */

    private void getSeckill() {


        OkGo.post(Urls.NEW_Ip_url + Urls.HOME_FRAGMENT.SPECKILL)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            seckillBean = gson.fromJson(s, Seckill_Bean.class);
                            if (seckillBean.getError_code() == 0) {
                                if (seckillBean.getData().size() != 0) {
                                    long time = System.currentTimeMillis();
                                    Calendar mCalendar = Calendar.getInstance();
                                    mCalendar.setTimeInMillis(time);
                                    int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
                                    int minute = mCalendar.get(Calendar.MINUTE);
                                    int second = mCalendar.get(Calendar.SECOND);
                                    int millisecond = mCalendar.get(Calendar.MILLISECOND);
                                    long h = (long) (24 - hour) * 60 * 60 * 1000;
                                    long m = (long) (60 - minute) * 60 * 1000;
                                    long se = (long) (60 - second) * 1000;

                                    long time12 = h + m + se + (1000 - millisecond);

                                    mskillLayout.setVisibility(View.VISIBLE);
                                    mCountdownView.start(time12);
                                    countdownhour.setTime(time12);
                                }else {
                                    mskillLayout.setVisibility(View.GONE);
                                }
                                switch (seckillBean.getData().size()) {
                                    case 1:
                                        reView.setVisibility(View.GONE);
                                        Seckill_Bean.DataBean dataBean = seckillBean.getData().get(0);
                                        RequestOptions options = new RequestOptions()
                                                .centerCrop()
                                                .dontAnimate()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                                        Glide.with(getActivity()).load(dataBean.getProduct_logo()).apply(options).into(productLogo);
                                        tvAmout.setText("最高" + dataBean.getAmount() + "元");

                                        String activity_desc = dataBean.getHeadline();
                                        if(activity_desc!=null&&activity_desc.length()>2){
                                            StringBuffer str = new StringBuffer(activity_desc);
                                            str.insert(2,"\n");
                                            tvActivityDesc.setText(str);
                                        }else {
                                            tvActivityDesc.setText(dataBean.getHeadline());
                                        }
                                        tvDesc.setText(dataBean.getActivity_desc());
                                        tvPname.setText(dataBean.getProduct_name());
                                        mCardView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(new Intent(getActivity(), ProductDesc.class).putExtra("pid", seckillBean.getData().get(0).getProduct_id()));
                                            }
                                        });
                                        break;
                                    case 2:
                                        mCardView.setVisibility(View.GONE);
                                        reView.setVisibility(View.VISIBLE);
                                        reView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                                        reView.setAdapter(rcAdapter);
                                        rcAdapter.setNewData(seckillBean.getData());
                                        break;
                                    case 3:
                                        mCardView.setVisibility(View.GONE);
                                        reView.setVisibility(View.VISIBLE);
                                        reView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                                        reView.setAdapter(rcAdapter);
                                        rcAdapter.setNewData(seckillBean.getData());
                                        break;
                                    default:
                                        mCardView.setVisibility(View.GONE);
                                        reView.setVisibility(View.VISIBLE);
                                        reView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                                        reView.setAdapter(rcAdapter);
                                        rcAdapter.setNewData(seckillBean.getData());
                                        break;
                                }
                            }
                        }
                    }
                });

    }
    private void initDialog() {
        mToken=SPUtil.getString(getActivity(),Urls.lock.TOKEN);
        String[] stringArray = getResources().getStringArray(R.array.home_money);
        list = Arrays.asList(stringArray);

        HashMap<String, String> params = new HashMap<>();
        params.put("position", "1");
        params.put("type", "1");
        JSONObject object = new JSONObject(params);
        OkGo.post(Urls.Ip_url + Urls.Dialog.advertising)
                .tag(this)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            AdvertisingBean bean = gson.fromJson(s, AdvertisingBean.class);
                            if (bean.getError_code() == 0) {
                                if(bean.getData().getImg()!=null){
                                    showAdvertising(bean);
                                }
                            }
                        }
                    }
                });
    }

    private void showAdvertising(final AdvertisingBean bean) {
        long date = SPUtil.getLong(getActivity(), "AdvertTime", 1111111111111L);
        boolean today = TimeUtils.isToday(date);
        if (today) {

        } else {
            AdialogFragment adialogFragment= AdialogFragment.newInstance(bean);
            if(isStateEnable() ){
                adialogFragment.show(getFragmentManager(),"adialogFragment");
            }
            long timeMillis = System.currentTimeMillis();
            SPUtil.putLong(getActivity(), "AdvertTime", timeMillis);
        }
    }

    /**
     * 下拉刷新
     */
    private void setListener() {

       /* ivNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeActivity.launch(getActivity());

            }
        });*/

        //Money选择
        selectMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPickerView( );
            }
        });

        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ACTION = 2;
                        getBannerDate(ACTION);
                    }
                }, 1000);

            }
        });
        easylayout.setEnableLoadMore(false);

        recylerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                startActivity(new Intent(getActivity(), ProductDesc.class).putExtra("pid", productAdapter.getData().get(position).getId()));

            }
        });
        /**关注微信**/
        layoutAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WeChatDialogFragment instance = WeChatDialogFragment.newInstance(name);
                instance.show(getChildFragmentManager(),"weChatDialog");
            }
        });
        /**帮你借**/
        layoutHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if("1".equals(status)&& !TextUtils.isEmpty(url)){
                    Intent intent=new Intent(getActivity(),HtmlActivity.class);
                    intent.putExtra("title","帮你借");
                    intent.putExtra("link",url);
                    startActivity(intent);
                }else {
                    ToastUtils.showToast(getActivity(),"升级维护中...");
                }
            }
        });

    }

    /**
     * 首页新品
     */
    private void getDate() {

        productAdapter = new ListProductAdapter(null);
        recylerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recylerview.setAdapter(productAdapter);
        recylerview.addItemDecoration(new RecyclerIntervalDecoration(15));
        View view = setHeaderView();
        View foot= setFootView();

        productAdapter.addHeaderView(view);
        productAdapter.addFooterView(foot);

    }
    private RelativeLayout layoutHelp, layoutAtt;

    private View setFootView() {
        View foot = getActivity().getLayoutInflater().inflate(R.layout.home_foot_layout, null);
        layoutHelp =foot.findViewById(R.id.layout_help);
        layoutAtt =foot.findViewById(R.id.layout_att);

        return foot;
    }


    private BGABanner banner;

    private RecyclerView reView;
    private Recycler_Adapter rcAdapter;

    private CardView mCardView;
    //秒杀
    private LinearLayout mskillLayout;
    private TextView tvPname, tvAmout, tvActivityDesc, tvDesc;
    private Banner_HotBean hotBean;
    private ImageView productLogo;
    private EasyCountDownView countdownhour;
    private CountdownView  mCountdownView;
    private View setHeaderView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.head_layout, null);
        banner = view.findViewById(R.id.banner_fresco_demo_content);
        productLogo = view.findViewById(R.id.product_logo);
        mskillLayout = view.findViewById(R.id.layout_kill);
        tvPname = view.findViewById(R.id.pname);
        tvActivityDesc = view.findViewById(R.id.activity_desc);
        tvAmout = view.findViewById(R.id.amount);
        tvDesc = view.findViewById(R.id.desc);
        mCountdownView = view.findViewById(R.id.cv_countdownViewTest1);
        mCardView = view.findViewById(R.id.seckill_item_one);
        countdownhour = view.findViewById(R.id.countdownhour);
        banner.setAdapter(new BGABanner.Adapter<ImageView, Banner_HotBean.AdvertisingBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, Banner_HotBean.AdvertisingBean model, int position) {

                RequestOptions options = new RequestOptions()
                        .dontAnimate()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(getActivity())
                        .load(model.getPictrue())
                        .apply(options)
                        .into(itemView);


            }
        });
        banner.setDelegate(new BGABanner.Delegate<ImageView, Banner_HotBean.AdvertisingBean>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, Banner_HotBean.AdvertisingBean model, int position) {

                OnClickStatistics.buriedStatistics(mToken, Constants.BANNER_CLICK);
                Banner_HotBean.AdvertisingBean advertisingBean = hotBean.getAdvertising().get(position);
                Intent intent = new Intent(getActivity(), HtmlActivity.class);
                intent.putExtra("link", advertisingBean.getApp());
                intent.putExtra("title", advertisingBean.getAdvername());
                startActivity(intent);

            }
        });
        //秒杀
        reView = view.findViewById(R.id.speckill_recycyler);

        rcAdapter = new Recycler_Adapter(null);
        getBannerDate(ACTION);

        reView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                startActivity(new Intent(getActivity(), ProductDesc.class).putExtra("pid", seckillBean.getData().get(position).getProduct_id()));
            }
        });
        // 职业选择
        ImageView ivFree = (ImageView) view.findViewById(R.id.iv_free);
        ImageView ivWork = (ImageView) view.findViewById(R.id.iv_work);
        ImageView ivEnterprise = (ImageView) view.findViewById(R.id.bussiones);
        ivFree.setOnClickListener(this);
        ivWork.setOnClickListener(this);
        ivEnterprise.setOnClickListener(this);
        return view;
    }

    /**
     * Banner_Hot 数据填充
     * <p>
     * 分类专题和新品
     */


    private void getBannerDate(final int Action) {

        JSONObject object=new JSONObject();
        try {
            object.put("VersionCode","2");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        OkGo.post(Urls.puk_URL + Urls.HOME_FRAGMENT.BANNER_HOT)
                .tag(getActivity())
                .upJson(object)
                .connTimeOut(5000)
                // 设置当前请求的连接超时时间
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String success = jsonObject.getString("isSuccess");
                                if (("1").equals(success)) {
                                    Gson gson = new Gson();
                                    hotBean = gson.fromJson(s, Banner_HotBean.class);
                                    if (Action == 2) {
                                        banner.setData(hotBean.getAdvertising(), null);
                                        easylayout.refreshComplete();
                                    } else {
                                        banner.setData(hotBean.getAdvertising(), null);
                                    }
                                } else {
                                    easylayout.refreshComplete();
                                    String msg = jsonObject.getString("msg");
                                    ToastUtils.showToast(getActivity(), msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            easylayout.refreshComplete();
                            ToastUtils.showToast(getActivity(), "网络异常");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        if(easylayout!=null){
                            easylayout.refreshComplete();

                        }
                        super.onError(call, response, e);
                    }
                });


        OkGo.post(Urls.NEW_Ip_url + Urls.HOME_FRAGMENT.HOT_NEW_PRODUCT)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            try {
                                Hot_New_Product hotNewProduct = gson.fromJson(s, Hot_New_Product.class);
                                if (hotNewProduct.getError_code() == 0) {
                                    productAdapter.setNewData(hotNewProduct.getData());
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        getSeckill();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_free:
                OnClickStatistics.buriedStatistics(mToken, Constants.FREE);
                EventBus.getDefault().post(new IdentityProduct(3,100));
                MainActivity.navigationController.setSelect(1);
                break;
            case R.id.iv_work:
                OnClickStatistics.buriedStatistics(mToken, Constants.WORK);
                EventBus.getDefault().post(new IdentityProduct(1,100));
                MainActivity.navigationController.setSelect(1);
                break;
            case R.id.bussiones:
                OnClickStatistics.buriedStatistics(mToken, Constants.ENTREPRENEUR);
                EventBus.getDefault().post(new IdentityProduct(4,100));
                MainActivity.navigationController.setSelect(1);
                break;
            default:
                break;

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countdownhour != null) {
            countdownhour.cancelTime();
            mCountdownView.stop();
        }
    }
    private  void setPickerView( ){
        int color = getActivity().getResources().getColor(R.color.colorPrimary);
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if(options1==list.size()-1){
                    EventBus.getDefault().post(new IdentityProduct(0,10000));
                    MainActivity.navigationController.setSelect(1);
                }else {
                    String s = list.get(options1);
                    String substring = s.substring(0, s.length() - 1);
                    int i = Integer.parseInt(substring);
                    EventBus.getDefault().post(new IdentityProduct(0,i));
                    MainActivity.navigationController.setSelect(1);
                }
            }
        })

                .setTitleText("我要借多少钱")
                .setTitleColor(color)//标题文字颜色
                .setDividerColor(color)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setSubmitColor(color)
                .setCancelColor(color)
                .build();
        pvOptions.setPicker(list);//一级选择器
        pvOptions.show();
    }
    @Override
    public void onStart() {
        super.onStart();
        // super.onStart();中将mStateSaved置为false
        mStateEnable = true;
    }
    @Override
    public void onResume() {
        // onPause之后便可能调用onSaveInstanceState，因此onresume中也需要置true
        mStateEnable = true;
        super.onResume();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState();中将mStateSaved置为true
        mStateEnable = false;
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onStop() {
        // super.onStop();中将mStateSaved置为true
        mStateEnable = false;
        super.onStop();
    }
    /**
     * activity状态是否处于可修改周期内，避免状态丢失的错误
     * @return
     */
    public boolean isStateEnable() {
        return mStateEnable;
    }
}
