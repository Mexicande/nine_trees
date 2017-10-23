package cn.com.stableloan.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.WelfareBean;
import cn.com.stableloan.ui.activity.HtmlActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.ProductDesc;
import cn.com.stableloan.ui.adapter.WelfareAdapter;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.view.statuslayout.StateLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LotteryFragment extends ImmersionFragment {


    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.stateLayout)
    StateLayout stateLayout;
    @Bind(R.id.welfare_recycler)
    RecyclerView welfareRecycler;
    @Bind(R.id.swip)
    SwipeRefreshLayout swip;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_default)
    ImageView ivDefault;

    private WelfareAdapter welfareAdapter;

    private static final int LOTTERY_CODE = 500;
    private static final int LOTTERY_SNED = 5000;


    public LotteryFragment() {
        // Required empty public constructor
    }

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
        View view = inflater.inflate(R.layout.fragment_lottery, container, false);
        ButterKnife.bind(this, view);
        titleName.setText("福利");
        ivBack.setVisibility(View.GONE);
        CheckInternet();
        SetListener();
        return view;

    }

    private void SetListener() {
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("福利", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//记录事件
        ZhugeSDK.getInstance().track(getActivity(), "popuppage", eventObject);

        welfareRecycler.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String token = (String) SPUtils.get(getActivity(), Urls.lock.TOKEN, "1");
                if ("1".equals(token)||token == null) {
                        startActivity(new Intent(getActivity(), LoginActivity.class).putExtra("welfare", welfareAdapter.getData().get(position)));
                    } else {
                        startActivity(new Intent(getActivity(), HtmlActivity.class).putExtra("welfare", welfareAdapter.getData().get(position)));
                    }

            }
        });

        swip.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swip.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CheckInternet();
                        swip.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }


    private void CheckInternet() {
        stateLayout.showProgressView();
        String token = (String) SPUtils.get(getActivity(), "token", "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        final JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Urls.Ip_url + Urls.LOTTERY.GetLottery)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        stateLayout.showContentView();
                        LogUtils.i("福利", s);
                        Gson gson = new Gson();
                        WelfareBean bean = gson.fromJson(s, WelfareBean.class);
                        if(bean.getData().size()==0){
                            ivDefault.setVisibility(View.VISIBLE);
                        }else {
                            ivDefault.setVisibility(View.GONE);
                            welfareAdapter = new WelfareAdapter(bean.getData());
                            welfareRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                            welfareRecycler.setAdapter(welfareAdapter);

                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
