package cn.com.stableloan.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.WelfareBean;
import cn.com.stableloan.ui.activity.HtmlActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.adapter.WelfareAdapter;
import cn.com.stableloan.utils.SPUtil;
import cn.com.stableloan.view.statuslayout.StateLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 *
 * A simple {@link Fragment} subclass.
 * @author apple
 * 福利
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

    private String mToken;
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
        mToken= SPUtil.getString(getActivity(), Urls.lock.TOKEN);

        titleName.setText("福利");
        ivBack.setVisibility(View.GONE);
        CheckInternet();
        SetListener();
        return view;

    }

    private void SetListener() {
        welfareRecycler.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (TextUtils.isEmpty(mToken)) {
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
        HashMap<String, String> params = new HashMap<>();
        params.put("token", mToken);
        final JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Urls.Ip_url + Urls.LOTTERY.GetLottery)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        stateLayout.showContentView();
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
