package cn.com.stableloan.ui.fragment.integral;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.bean.IntregarlEvent;
import cn.com.stableloan.model.integarl.ExchangeIntegralBean;
import cn.com.stableloan.utils.SPUtil;
import cn.com.stableloan.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 *
 * 积分兑换
 */
public class IntegarlExchangeFragment extends Fragment {


    public IntegarlExchangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_integarl_exchange, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.exchange_two, R.id.exchange_five})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.exchange_two:
                ExChangeIntegral(Urls.NUMBER_ONE);
                break;
            case R.id.exchange_five:
                ExChangeIntegral(Urls.NUMBER_TWO);
                break;
        }
    }
    private KProgressHUD hud;
    private void ExChangeIntegral(String type) {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(true)
                .show();
        HashMap<String, String> params = new HashMap<>();

        String token = SPUtil.getString(getActivity(), Urls.lock.TOKEN);
        params.put(Urls.lock.TOKEN, token);
        params.put("type", type);
        JSONObject object = new JSONObject(params);
        OkGo.post(Urls.Ip_url+Urls.Integarl.EXCHANGEPOINTS)
                .tag(this)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        hud.dismiss();
                        if(s!=null){
                            Gson gson=new Gson();
                            ExchangeIntegralBean bean = gson.fromJson(s, ExchangeIntegralBean.class);
                            if(bean.getError_code()==0){
                                ToastUtils.showToast(getActivity(),"兑换成功");
                                EventBus.getDefault().post(new IntregarlEvent(null,bean.getData().getCredit(),0));
                            }else {
                                ToastUtils.showToast(getActivity(),bean.getError_message());
                            }
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });


    }
}
