package cn.com.stableloan.ui.activity.settingdate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.Device;
import cn.com.stableloan.ui.activity.CertificationActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.integarl.SafeSettingActivity;
import cn.com.stableloan.ui.activity.integarl.WithdrawalCashActivity;
import cn.com.stableloan.ui.adapter.DeviceAdapter;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.MyDecoration;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

public class DeviceActivity extends BaseActivity {

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
    private String token;
    private  boolean flag=false;
    private  int deviceInt=0;

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
        token = (String) SPUtils.get(this, "token", "1");
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
                                    int is_device = device.getData().getIs_device();
                                    if(is_device==0){
                                        deviceInt=1;
                                        stProtect.setSwitchIsChecked(false);
                                        deviceInt=0;
                                    }else {
                                        deviceInt=1;
                                        stProtect.setSwitchIsChecked(true);
                                        deviceInt=0;
                                    }
                                    mDeviceAdapter.addData(device.getData().getList());
                                }else if(device.getError_code()==2){
                                    Intent intent=new Intent(DeviceActivity.this,LoginActivity.class);
                                    intent.putExtra("message",device.getError_message());
                                    intent.putExtra("from","DeviceError");
                                    startActivityForResult(intent,1000);
                                }else if(device.getError_code()==Urls.ERROR_CODE.FREEZING_CODE){
                                    Intent intent=new Intent(DeviceActivity.this,LoginActivity.class);
                                    intent.putExtra("message","1136");
                                    intent.putExtra("from","1136");
                                    startActivity(intent);
                                    finish();

                                }else {
                                    ToastUtils.showToast(DeviceActivity.this,device.getError_message());
                                }
                            }
                    }
                });
        stProtect.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(deviceInt==0){
                    if(!isChecked){
                        offDevice(0);
                    }else {
                        offDevice(1);
                    }
                }

            }
        });

    }

    /**
     * 关闭设备
     */
    private void offDevice(int offOr) {

        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("is_device",String.valueOf(offOr));
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_Ip_url+Urls.user.OPEN_DEVICE)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if(s!=null){
                            try {
                                JSONObject object=new JSONObject(s);
                                int error_code = object.getInt("error_code");
                                if(error_code==0){

                                }else {
                                    String error_message = object.getString("error_message");
                                    ToastUtils.showToast(DeviceActivity.this,error_message);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

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
        recycler.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));


        mDeviceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                deleteDevice(position+1,device.getData().getList().get(position).getId());
            }
        });
    }

    private void deleteDevice(int position,int id) {
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("id", String.valueOf(id));
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_Ip_url+Urls.user.DELETE_DEVICE)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if(s!=null){
                            try {
                                JSONObject object=new JSONObject(s);
                                int error_code = object.getInt("error_code");
                                if(error_code==0){

                                    device.getData().getList().remove(position);
                                    mDeviceAdapter.remove(position-1);
                                    ToastUtils.showToast(DeviceActivity.this,"成功删除");

                                }else if(error_code==2) {
                                    String error_message = object.getString("error_message");

                                    Intent intent = new Intent(DeviceActivity.this, LoginActivity.class);
                                    intent.putExtra("message", error_message);
                                    intent.putExtra("from", "CertificationActivity");
                                    startActivityForResult(intent, Urls.REQUEST_CODE.PULLBLIC_CODE);

                                } else if(device.getError_code()==Urls.ERROR_CODE.FREEZING_CODE){
                                    Intent intent=new Intent(DeviceActivity.this,LoginActivity.class);
                                    intent.putExtra("message","1136");
                                    intent.putExtra("from","1136");
                                    startActivity(intent);
                                    finish();

                                }else{
                                    String error_message = object.getString("error_message");
                                    ToastUtils.showToast(DeviceActivity.this,error_message);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                });


    }


    @OnClick({R.id.iv_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                if(flag){
                    tvSave.setText("编辑");
                    flag=false;
                }else {
                    tvSave.setText("完成");
                    flag=true;
                }
                mDeviceAdapter.setVisiable(flag);
                mDeviceAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==1000){
                if(resultCode==100){
                    int device = data.getIntExtra("device", 0);
                    if(device==1){
                        getDate();
                    }

                }
            }
    }
}
