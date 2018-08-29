package cn.com.cashninetrees.ui.fragment.cash;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.bean.CashEvent;
import cn.com.cashninetrees.model.integarl.CashBean;
import cn.com.cashninetrees.ui.activity.cash.CashFlowActivity;
import cn.com.cashninetrees.ui.activity.integarl.WithdrawalCashActivity;
import cn.com.cashninetrees.ui.adapter.CashAdapter;
import cn.com.cashninetrees.utils.SPUtil;
import cn.com.cashninetrees.utils.ToastUtils;
import cn.com.cashninetrees.view.supertextview.SuperButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 现金明细
 * @author apple
 */
public class DetailCash_Fragment extends Fragment {


    @Bind(R.id.cash_recycler)
    RecyclerView cashRecycler;
    @Bind(R.id.bt_withdrawal)
    SuperButton btWithdrawal;
    private CashAdapter cashAdapter;

    private CashBean cashBean;
    private static final int REQUEST_CODE = 1;
    private static final int RESULT_CODE = 200;
    private int ACTION_UP=1;
    private int ACTION_DOWN=0;
    public DetailCash_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_cash_, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        getDate(ACTION_UP,1);
        setListener();
        return view;
    }

    private void setListener() {
        btWithdrawal.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("cash", cashBean);
            intent.setClass(getActivity(), WithdrawalCashActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
        cashRecycler.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), CashFlowActivity.class).putExtra("log_id",cashAdapter.getData().get(position).getLog_id()));

            }
        });
        cashAdapter.setOnLoadMoreListener(() -> cashRecycler.postDelayed(() -> {
            ACTION_UP++;
            if(cashAdapter.getData().size()>=10){
                getDate(ACTION_UP,1);
            }
        }, 1000), cashRecycler);

    }

    private void initRecyclerView() {
        cashAdapter = new CashAdapter(null);
        cashRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        cashRecycler.setAdapter(cashAdapter);
    }
    private KProgressHUD hud;




    private void getDate(int page,int action) {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(true)
                .show();

        String token = SPUtil.getString(getActivity(), Urls.lock.TOKEN);
        HashMap<String, String> params = new HashMap<>();
        params.put(Urls.lock.TOKEN, token);
        params.put("page", String.valueOf(page));
        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.Ip_url + Urls.Integarl.GETCASH)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        hud.dismiss();
                        if (s != null) {
                            Gson gson = new Gson();
                            cashBean = gson.fromJson(s, CashBean.class);
                            if (cashBean.getError_code() == 0) {

                                EventBus.getDefault().post(new CashEvent("¥" + cashBean.getData().getTotal()));
                                if(cashBean.getData().getCashRecord().size()==0){
                                    cashAdapter.loadMoreEnd();
                                }else {
                                    if(action==0){
                                        cashAdapter.setNewData(cashBean.getData().getCashRecord());
                                    }else {
                                        cashAdapter.addData(cashBean.getData().getCashRecord());
                                    }

                                    if(cashBean.getData().getCashRecord().size()<10){
                                        cashAdapter.loadMoreEnd();
                                    }else {
                                        cashAdapter.loadMoreComplete();

                                    }
                                }
                            }  else {
                                ToastUtils.showToast(getActivity(), cashBean.getError_message());
                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                                hud.dismiss();
                                ToastUtils.showToast(getContext(), "服务器异常");
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                getDate(ACTION_DOWN,0);
                break;
        }
    }
}
