package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.integarl.CashBean;
import cn.com.stableloan.ui.activity.integarl.WithdrawalCashActivity;
import cn.com.stableloan.ui.adapter.CashAdapter;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.RoundButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 我的现金
 */
public class CashActivity extends BaseActivity {

    @Bind(R.id.cash_recycler)
    RecyclerView cashRecycler;
    @Bind(R.id.bt_withdrawal)
    RoundButton btWithdrawal;
    @Bind(R.id.total)
    TextView total;
    @Bind(R.id.account)
    TextView account;
    @Bind(R.id.bt_visiable)
    RoundButton btVisiable;

    private CashAdapter cashAdapter;

    private CashBean cashBean;
    private static int ACTION_NEWS=1;

    private static final int REQUEST_CODE=1;
    private static final int RESULT_CODE=200;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, CashActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        ButterKnife.bind(this);
        initRecyclerView();
        getDate();
        setListener();
    }

    private void setListener() {


    }

    private void initRecyclerView() {
        cashAdapter = new CashAdapter(null);
        cashRecycler.setLayoutManager(new LinearLayoutManager(this));
        cashRecycler.setAdapter(cashAdapter);

    }
    private KProgressHUD hud;
    private void getDate() {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(true)
                .show();

        String token = (String) SPUtils.get(this, "token", "1");
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
                            account.setText(cashBean.getData().getAccount());
                            total.setText(cashBean.getData().getTotal());
                                cashAdapter.setNewData(cashBean.getData().getCashRecord());
                            if (cashBean.getData().getAccount().length()>1) {
                                btVisiable.setVisibility(View.GONE);
                                btWithdrawal.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.cash_back, R.id.cash_data, R.id.bt_visiable, R.id.bt_withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cash_back:
                finish();
                break;
            case R.id.cash_data:
                CertificationActivity.launch(this);
                break;
            case R.id.bt_visiable:
                ToastUtils.showToast(this, "请先完善资料");
                break;
            case R.id.bt_withdrawal:
                //WithdrawalCashActivity.launch(this);
                Intent intent=new Intent();
                intent.putExtra("cash",cashBean);
                intent.setClass(this,WithdrawalCashActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
               // startActivity(new Intent(this,WithdrawalCashActivity.class).putExtra("cash",cashBean));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode){
                case REQUEST_CODE:
                    if(resultCode==RESULT_CODE){
                        getDate();
                    }
                    break;
            }


    }
}
