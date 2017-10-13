package cn.com.stableloan.ui.activity.settingdate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.Device;
import cn.com.stableloan.ui.activity.integarl.SafeSettingActivity;
import cn.com.stableloan.ui.adapter.DeviceAdapter;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

public class DeviceActivity extends AppCompatActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.st_Protect)
    SuperTextView stProtect;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    private DeviceAdapter mDeviceAdapter;
    private Device device;

    private  boolean flag=false;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, DeviceActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        ButterKnife.bind(this);
        initToolbar();
        getDate();


    }

    private void getDate() {
        String token = (String) SPUtils.get(this, "token", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_Ip_url+Urls.user.DEVICE)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                            if(s!=null){
                                Gson gson=new Gson();
                                 device = gson.fromJson(s, Device.class);

                                if(device.getError_code()==0){
                                    mDeviceAdapter.addData(device.getData().get_$0());
                                }else {
                                    ToastUtils.showToast(DeviceActivity.this,device.getError_message());
                                }
                            }
                    }
                });


    }

    private void initToolbar() {
        titleName.setText("常用设备管理");
        tvSave.setText("编辑");
        tvSave.setVisibility(View.VISIBLE);
        mDeviceAdapter=new DeviceAdapter(null);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(mDeviceAdapter);
    }


    @OnClick({R.id.iv_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_save:
                if(flag){
                    tvSave.setText("编辑");
                    flag=false;
                }else {
                    tvSave.setText("完成");
                    flag=true;
                }
                mDeviceAdapter.setVisiable(false);
                break;
        }
    }


}
