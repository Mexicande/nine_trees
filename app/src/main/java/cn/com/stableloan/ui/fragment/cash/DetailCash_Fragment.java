package cn.com.stableloan.ui.fragment.cash;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import cn.com.stableloan.model.integarl.CashBean;
import cn.com.stableloan.ui.activity.CashActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.integarl.WithdrawalCashActivity;
import cn.com.stableloan.ui.adapter.CashAdapter;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.supertextview.SuperButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 现金明细
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
        getDate();
        setListener();
        return view;
    }

    private void setListener() {
        btWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("cash", cashBean);
                intent.setClass(getActivity(), WithdrawalCashActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void initRecyclerView() {
        cashAdapter = new CashAdapter(null);
        cashRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        cashRecycler.setAdapter(cashAdapter);
    }
    private KProgressHUD hud;

    private void getDate() {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(true)
                .show();

        String token = (String) SPUtils.get(getActivity(), Urls.TOKEN, "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
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
                                cashAdapter.setNewData(cashBean.getData().getCashRecord());
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hud.dismiss();
                                ToastUtils.showToast(getContext(), "服务器异常");
                            }
                        });
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
                if (resultCode == RESULT_CODE) {
                    getDate();
                }
                break;
        }
    }
}
