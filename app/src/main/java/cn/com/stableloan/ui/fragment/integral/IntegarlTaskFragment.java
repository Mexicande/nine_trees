package cn.com.stableloan.ui.fragment.integral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coorchice.library.SuperTextView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.bean.IntegarUpEvent;
import cn.com.stableloan.bean.IntregarlEvent;
import cn.com.stableloan.bean.ShareEvent;
import cn.com.stableloan.model.Identity;
import cn.com.stableloan.model.integarl.IntegarlBean;
import cn.com.stableloan.ui.activity.IdentityinformationActivity;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.ui.adapter.Integarl_taskAdapter;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 积分任务
 */
public class IntegarlTaskFragment extends Fragment {

    @Bind(R.id.task_recycler)
    RecyclerView taskRecycler;

    private Integarl_taskAdapter adapter;
    private String shareUrl = "";
    private SuperTextView integar;
    public IntegarlTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_integarl_task, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        initRecyclerView();
        getInDate();
        setListener();
        return view;
    }

    private void getInDate() {
        String token = (String) SPUtils.get(getActivity(), "token", "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.Ip_url + Urls.Integarl.getAccumulatePoints)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            IntegarlBean bean = gson.fromJson(s, IntegarlBean.class);
                            if (bean.getCode() == 200) {
                                adapter.addData(bean.getData().getCode());
                                integar.setText(bean.getData().getStatus());
                                EventBus.getDefault().post(new IntregarlEvent(bean.getData().getOffical(), bean.getData().getCredits(),bean.getData().getTopCredits()));
                            } else {
                                ToastUtils.showToast(getActivity(), bean.getError_message());
                            }
                        }
                    }
                });


    }

    private void setListener() {


        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(position==0){

                    getDate();

                }else {
                    IntegarlBean.DataBean.CodeBean o = (IntegarlBean.DataBean.CodeBean) adapter.getData().get(position);
                    LogUtils.i("Share==", o.getUrl());
                    shareUrl = o.getUrl();
                    EventBus.getDefault().post(new ShareEvent(1, o.getUrl()));
                }


            }
        });

    }


    private void initRecyclerView() {

        adapter = new Integarl_taskAdapter(null);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskRecycler.setAdapter(adapter);
        View view = getActivity().getLayoutInflater().inflate(R.layout.integarl_header_layout, null);
        integar= (SuperTextView) view.findViewById(R.id.integar);
        adapter.addHeaderView(view,0);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Subscribe
    public void onMessageEvent(IntegarUpEvent event) {
        if (event != null) {
            if(event.up.equals("integar")) {
                initRecyclerView();
            }
        }
    }

    /*@OnClick({R.id.user_information})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_information:
                getDate();
                break;
        }
    }*/

    private void getDate() {
        Map<String, String> parms = new HashMap<>();
        String token = (String) SPUtils.get(getActivity(), "token", "1");
        String signature = (String) SPUtils.get(getActivity(), "signature", "1");
        parms.put("token", token);
        parms.put("signature", signature);
        parms.put("source", "");
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.Ip_url + Urls.Identity.GetIdentity)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            Identity identity = gson.fromJson(s, Identity.class);
                            if (identity.getError_code() == 0) {
                                if (identity.getData().getIsSuccess().equals("1")) {
                                    if (identity.getData().getStatus().equals("1")) {
                                        startActivity(new Intent(getActivity(), IdentityinformationActivity.class).putExtra("integarl", "integarl"));
                                    } else {
                                        Intent intent = new Intent(getActivity(), Verify_PasswordActivity.class)
                                                .putExtra("from", "integarl");
                                        startActivity(intent);
                                    }

                                }

                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }


}