package cn.com.stableloan.ui.fragment.cash;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.bean.CashEvent;
import cn.com.stableloan.bean.cash.CashActivityBean;
import cn.com.stableloan.model.integarl.CashBean;
import cn.com.stableloan.model.integarl.InviteFriendList;
import cn.com.stableloan.ui.activity.HtmlActivity;
import cn.com.stableloan.ui.activity.IntegralActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.integarl.InviteFriendsActivity;
import cn.com.stableloan.ui.adapter.CashGetAdapter;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 获取现金
 */
public class GetCash_Fragment extends Fragment {


    @Bind(R.id.activity_recycler)
    RecyclerView activityRecycler;
    private CashGetAdapter getAdapter;

    private static final int REQUEST_CODE=100;
    private static final int INVITE_CODE=200;

    private  CashActivityBean cashBean;
    public GetCash_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_cash_, container, false);
        ButterKnife.bind(this, view);
        initView();
        getDate();
        initFooter();
        setListener();
        return view;
    }

    private void setListener() {

        activityRecycler.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getActivity(), HtmlActivity.class);
                intent.putExtra("cashActivityBean",cashBean.getData().getActivity().get(position));
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }


    private RelativeLayout invite_layout,integar_layout;

    private   View  initFooter() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.getcash_footer_layout, null);
        invite_layout= (RelativeLayout) view.findViewById(R.id.invite_layout);
        integar_layout= (RelativeLayout) view.findViewById(R.id.layout_integar);

        integar_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), IntegralActivity.class);
                intent.putExtra("from","cash");
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        invite_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), InviteFriendsActivity.class);
                intent.putExtra("from","cash");
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        return view;
    }

    private void initView() {
        getAdapter=new CashGetAdapter(null);
        activityRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        activityRecycler.setAdapter(getAdapter);
        getAdapter.addFooterView(initFooter());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void getDate() {

        HashMap<String, Integer> params = new HashMap<>();
        params.put("type", 1);
        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.NEW_Ip_url + Urls.Integarl.getActivity)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                             cashBean = gson.fromJson(s, CashActivityBean.class);
                            if (cashBean.getError_code() == 0) {
                              /*  EventBus.getDefault().post(new CashEvent("¥" + cashBean.getData().getTotal()));
                                getAdapter.addData(cashBean.getData().get);*/
                                getAdapter.setNewData(cashBean.getData().getActivity());
                            } else if (cashBean.getError_code() == 2) {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.putExtra("message", cashBean.getError_message());
                                intent.putExtra("from", "CashError");
                                startActivityForResult(intent,REQUEST_CODE);
                            } else {
                                ToastUtils.showToast(getActivity(), cashBean.getError_message());
                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            getDate();
        }
    }
}


