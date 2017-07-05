package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.SaveBean;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.BetterSpinner;
import okhttp3.Call;
import okhttp3.Response;

public class SafeSettingActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_way)
    BetterSpinner etWay;
    @Bind(R.id.et_times)
    BetterSpinner etTimes;
    @Bind(R.id.lock)
    TextView lock;
    @Bind(R.id.month)
    TextView month;
    @Bind(R.id.time)
    TextView tv_time;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.view1)
    TextView view1;
    @Bind(R.id.view2)
    TextView view2;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.text1)
    TextView text1;

    private  SaveBean saveBean;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, SafeSettingActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting);
        ButterKnife.bind(this);
        initToolbar();
        getDate();
        setLisenter();
    }

    private void setLisenter() {
        etTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                month.setText(etTimes.getText());
            }
        });
        etWay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lock.setText(etWay.getText());
                if("0".equals(saveBean.getPeriod())){
                    long time=System.currentTimeMillis();

                    long limit_time=86400000;
                    long size=0;
                    switch (position){
                        case 1:
                            size = limit_time * 7;
                            break;
                        case 2:
                            size = limit_time * 30;
                            break;
                        case 3:
                            size = limit_time * 90;
                            break;
                        case 4:
                            lock.setText("永久");
                            break;

                    }


                }

            }
        });

    }

    private void getDate() {

        String token = (String) SPUtils.get(this, "token", "1");

        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_URL + Urls.STATUS.Getsetting)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);

                            String isSuccess = object.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                Gson gson = new Gson();
                                saveBean = gson.fromJson(s, SaveBean.class);
                                month.setText(saveBean.getManaged());
                                etTimes.setText(saveBean.getManaged());
                                lock.setText(saveBean.getWay());
                                etWay.setText(saveBean.getWay());
                                tv_time.setText(saveBean.getPeriod());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });


    }

    private void initToolbar() {

        tvSave.setVisibility(View.VISIBLE);
        tvSave.setText("保存");
        titleName.setText("存储设置");
        ivBack.setVisibility(View.VISIBLE);

        String[] list = getResources().getStringArray(R.array.pass_change);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);
        etWay.setAdapter(adapter);

        String[] list1 = getResources().getStringArray(R.array.times);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list1);
        etTimes.setAdapter(adapter1);


    }

    @OnClick({R.id.iv_back, R.id.et_way, R.id.et_times,R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.et_way:
                lock.setText(etWay.getText());
                break;
            case R.id.et_times:
               // settingTime();
                break;
            case R.id.tv_save:
                Save();
                break;
        }
    }

    private void settingTime() {
        long l = dataOne(saveBean.getPeriod());
        long time=System.currentTimeMillis();

        long l1 = l + time;
        String of = String.valueOf(l1);
        String timedate = stampToDate(of);
        month.setText(etTimes.getText());
        tv_time.setText(timedate);

    }
    public static long dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.CHINA);
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }



    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    private void Save() {
        String token = (String) SPUtils.get(this, "token", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("way",lock.getText().toString() );
        parms.put("managed",month.getText().toString());
        //parms.put("period",tv_time.getText().toString());
        parms.put("period","2014-12-05");
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_URL+Urls.STATUS.Save_Setting)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object=new JSONObject(s);
                            String isSuccess = object.getString("isSuccess");
                            String msg = object.getString("msg");
                            if("1".equals(isSuccess)){
                                ToastUtils.showToast(SafeSettingActivity.this,msg);
                                finish();
                            }else {
                                ToastUtils.showToast(SafeSettingActivity.this,msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });

    }

}
