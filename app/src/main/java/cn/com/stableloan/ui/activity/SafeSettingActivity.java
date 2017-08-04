package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.MsgEvent;
import cn.com.stableloan.model.SaveBean;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.utils.constant.Constant;
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

    private String[] list;
    private String[] list1;

    private ACache aCache;

    private   ArrayAdapter<String> adapter;
    private  SaveBean saveBean;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, SafeSettingActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        aCache = ACache.get(this);

        initToolbar();
        getDate();
        setLisenter();
    }

    @Subscribe
    public void onMessageEvent(MsgEvent event){
            if(event.msg!=null&&"ok".equals(event.msg)){
                etWay.setText(list[1]);
                lock.setText(list[1]);
            }else if(event.msg!=null){
                etWay.setText(list[0]);
                lock.setText(list[0]);


            }
    }


    private void setLisenter() {

        etTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                month.setText(etTimes.getText());
                settingTime();

            }
        });



        etWay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(etWay.getText().toString().equals("手机解锁")){
                    String gesturePassword = aCache.getAsString(Constant.GESTURE_PASSWORD);
                    if(gesturePassword == null || "".equals(gesturePassword)) {
                        startActivity(new Intent(SafeSettingActivity.this,CreateGestureActivity.class).putExtra("ok","1"));
                        //CreateGestureActivity.launch(SafeSettingActivity.this);
                    }
                }
                lock.setText(etWay.getText());
/*
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
*/

            }
        });

    }

    private void getDate() {


        String lock1 = aCache.getAsString("lock");
        if(lock1!=null){

            if(lock1.length()>1){

                etWay.setText(list[1]);
                lock.setText(etWay.getText());
            }

        }

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
                                String way1 = saveBean.getWay();

                                if(way1!=null){
                                    if("1".equals(way1)){
                                        EventBus.getDefault().post(new MsgEvent("1"));
                                    }
                                }
                                String managed = saveBean.getManaged();

                                if(managed!=null&&managed.length()==1){
                                    int i = Integer.parseInt(managed);
                                    month.setText(list1[i]);
                                    etTimes.setText(list1[i]);
                                }
                                if(saveBean.getPeriod().length()<2){
                                    tv_time.setText("无数据 ");

                                }else {

                                    tv_time.setText(saveBean.getPeriod());
                                }
                            }else {
                                String msg = object.getString("msg");
                                ToastUtils.showToast(SafeSettingActivity.this,msg);
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

        list = getResources().getStringArray(R.array.pass_change);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);
        etWay.setAdapter(adapter);
        etWay.setText(list[0]);


        list1 = getResources().getStringArray(R.array.times);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list1);
        etTimes.setAdapter(adapter1);



    }

    @OnClick({R.id.iv_back,R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                Save();
                break;
        }
    }

    private void settingTime() {
     int position=0;
        long l=0;
        if(saveBean.getLass_time()!=null){
            l = dateToStamp(saveBean.getLass_time());
        }
        String string = etTimes.getText().toString();
        for(int i=0;i<list1.length;i++){
            if(list1[i].equals(string)){
                position=i;
            }
        }
        long ls=0;
            switch (position){
                case 0:
                    ls= 604800000;
                    break;
                case 1:
                    ls=2592000000L;
                    break;
                case 2:
                    ls=7776000000L;
                    break;
                case 3:
                    ls=15552000000L;
                    break;

            }

        long l1 = l + ls;
        String of = String.valueOf(l1);
        String date = stampToDate(of);

        tv_time.setText(date);
        if(string.equals("永久")){
            tv_time.setText("永久");
        }

    }

    /**
     * 转为时间戳
     * @param s
     * @return
     */
    public static long dateToStamp(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return ts;
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
        String var="";
        String string = etTimes.getText().toString();
        for(int i=0;i<list1.length;i++){
            if(list1[i].equals(string)){
                var= String.valueOf(i);
            }
        }



        String String1 = etWay.getText().toString();

        String parrtern="";
        for(int i=0;i<list.length;i++){
            if(list[i].equals(String1)){
                parrtern= String.valueOf(i);
            }
        }
        String token = (String) SPUtils.get(this, "token", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("way", parrtern );
        parms.put("managed",var);
        parms.put("period",tv_time.getText().toString());
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
                                if(etWay.getText().toString().equals("手机解锁")){
                                    aCache.put("lock","off");
                                }else {
                                    aCache.put("lock","on");
                                }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
