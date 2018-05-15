package cn.com.stableloan.ui.activity.cash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.bean.cash.CashFlowBean;
import cn.com.stableloan.utils.SPUtil;
import cn.com.stableloan.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 现金提现详情
 */
public class CashFlowActivity extends AppCompatActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.status)
    TextView status;
    @Bind(R.id.created_at)
    TextView createdAt;
    @Bind(R.id.updated_at)
    TextView updatedAt;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.resault)
    TextView resault;
    private String  log_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_flow);
        ButterKnife.bind(this);
        initToolbar();
        log_id = getIntent().getStringExtra("log_id");
        if(log_id!=null){
            getDate(log_id);
        }

    }

    private void initToolbar() {

        titleName.setText("流水明细");

    }

    private void getDate(String log_id) {
        String token = SPUtil.getString(this, Urls.lock.TOKEN, "1");

        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("log_id", log_id);
        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.Ip_url + Urls.Integarl.CASH_DETAIL)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson=new Gson();
                            CashFlowBean cashFlowBean = gson.fromJson(s, CashFlowBean.class);
                            int error_code = cashFlowBean.getError_code();
                            if(error_code==0){
                                CashFlowBean.DataBean data = cashFlowBean.getData();
                                if(data!=null){
                                    money.setText(data.getMoney());
                                    title.setText(data.getTitle());
                                    createdAt.setText(data.getCreated_at());
                                    updatedAt.setText(data.getUpdated_at());
                                    resault.setText(data.getResault());
                                    status.setText(data.getStatus());
                                }
                            }else {
                                ToastUtils.showToast(CashFlowActivity.this,cashFlowBean.getError_message());
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==Urls.REQUEST_CODE.PULLBLIC_CODE){
                if(log_id!=null){
                    getDate(log_id);
                }
            }
    }
}
