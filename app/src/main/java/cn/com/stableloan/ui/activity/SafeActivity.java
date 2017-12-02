package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mancj.slideup.SlideUp;

import org.greenrobot.eventbus.EventBus;
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
import cn.com.stableloan.model.SaveBean;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.WaitTimeUtils;
import cn.com.stableloan.view.dialog.SafeInformationDialog;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 安全设置，存储时间
 */
public class SafeActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.time)
    TextView tv_time;
    @Bind(R.id.slide_Safe_Time)
    RelativeLayout slideSafeTime;
    @Bind(R.id.select_time)
    SuperTextView selectTime;
    @Bind(R.id.st_deleteDate)
    SuperTextView stDeleteDate;

    private String[] list;
    private String[] list1;


    private SlideUp timeSlideUp;
    private SlideUp waySlideUp;

    private SaveBean saveBean;
    private SafeInformationDialog safeSettingDialog;


    private String period;
    private String manage;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SafeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting);
        ButterKnife.bind(this);
        initToolbar();
        setLisenter();
    }

   /* @Subscribe
    public void onMessageEvent(MsgEvent event) {
        if (event.msg != null && "ok".equals(event.msg)) {
            lock.setText(list[0]);
        } else if (event.msg == null) {
            lock.setText(list[1]);
        }
    }*/


    private void setLisenter() {
        stDeleteDate.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                if (WaitTimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    exit();
                }

            }
        });
    }


    private void initToolbar() {

        titleName.setText("存储设置");
        ivBack.setVisibility(View.VISIBLE);
        list1 = getResources().getStringArray(R.array.times);

        timeSlideUp = new SlideUp.Builder(slideSafeTime)
                .withListeners(new SlideUp.Listener.Slide() {
                    @Override
                    public void onSlide(float percent) {
                    }
                })
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
             saveBean = (SaveBean) getIntent().getSerializableExtra("save");
            if (saveBean != null) {
            if (saveBean.getPeriod()==null||saveBean.getPeriod().isEmpty()) {
                tv_time.setText("无数据");
            } else {
                tv_time.setText("当前档案将于 "+saveBean.getPeriod()+"日后自动清除");
            }
            period=saveBean.getPeriod();
            int i = Integer.parseInt(saveBean.getManaged());
            manage = list1[i];
            selectTime.setRightString(manage);
        }
    }

    private void setTimes(int times) {
        LogUtils.i("time----", list1[times]);
            settingTime(times);

    }

    private void settingTime(int index) {
       // long l = 0l;

        //l = dateToStamp(period);
        long ls = 0;
        switch (index) {
            case 0:
                ls = 604800000;
                setDateTo(0,ls);
                break;
            case 1:
                ls = 2592000000L;
                setDateTo(1,ls);
                break;
            case 2:
                ls = 7776000000L;
                setDateTo(2,ls);
                break;
            case 3:
                ls = 15552000000L;
                setDateTo(3,ls);
                break;
            case 4:
                Save(4, "2222-01-01");
                break;
            default:
                break;

        }


    }
    private void setDateTo(int index,long ls){
        long stamp=0;
        if(period!=null){
            if(!period.isEmpty()){
                stamp = dateToStamp(period);
            }
        }
        long l1 = stamp + ls;
        long l2 = System.currentTimeMillis();

        String of = String.valueOf(l2 + ls);
        String date = stampToDate(of);
        Save(index, date);
       /* if (l2 > l1) {
            String of = String.valueOf(l2 + ls);
            String date = stampToDate(of);
            Save(index, date);
        } else {
            String of = String.valueOf(l2 + ls);
            String date = stampToDate(of);
            saveBean.setPeriod(date);
            tv_time.setText("当前档案将于 "+date+" 日后自动清除");
            Save(index, date);
        }*/
    }


    /**
     * 转为时间戳
     *
     * @param s
     * @return
     */
    public static long dateToStamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts=0L;
        if(date!=null){
            ts = date.getTime();
        }
        return ts;
    }

    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = Long.valueOf(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    private void Save(int managed, String date) {
        String man = String.valueOf(managed);
        String token = (String) SPUtils.get(this, "token", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("managed", man);
        parms.put("period", date);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_URL + Urls.STATUS.Save_Setting)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            String isSuccess = object.getString("isSuccess");
                            String msg = object.getString("msg");
                            if ("1".equals(isSuccess)) {
                                //period=date;
                              /*  saveBean.setPeriod(date);
                                saveBean.setManaged(man)*/;
                                tv_time.setText("当前档案将于 "+date+" 日后自动清除");
                                selectTime.setRightString(list1[managed]);
                                ToastUtils.showToast(SafeActivity.this, "修改清档日期成功");
                            } else {

                                ToastUtils.showToast(SafeActivity.this, msg);
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

    }

    @OnClick({R.id.iv_back, R.id.tv_OneWeek, R.id.tv_OneMonth, R.id.tv_ThreeMonth,
            R.id.tv_SixMonth, R.id.tv_forever, R.id.bt_cancel, R.id.select_time
            , R.id.time_Visiable
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (tv_time.getText().toString().equals(period) && selectTime.getRightString().equals(manage)) {
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("month",selectTime.getRightString());
                    intent.putExtra("time", tv_time.getText().toString());
                    setResult(Urls.SettingResultCode.SAFE_DATE, intent);
                    finish();
                }
                break;
            case R.id.tv_OneWeek:
                timeSlideUp.hide();
                setTimes(0);
                break;
            case R.id.tv_OneMonth:
                timeSlideUp.hide();
                setTimes(1);
                break;
            case R.id.tv_ThreeMonth:
                timeSlideUp.hide();
                setTimes(2);
                break;
            case R.id.tv_SixMonth:
                timeSlideUp.hide();
                setTimes(3);
                break;
            case R.id.tv_forever:
                timeSlideUp.hide();
                setTimes(4);
                break;
            case R.id.bt_cancel:
                timeSlideUp.hide();
                break;
            case R.id.select_time:
                timeSlideUp.show();
                break;
            case R.id.time_Visiable:
                timeSlideUp.hide();
                break;
          /*  case R.id.tv_HeaderLock:
                waySlideUp.hide();
               // lock.setText(list[0]);
                final TinyDB tinyDB = new TinyDB(this);
                UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
                String userphone = user.getUserphone();
             *//*   if (lock.getText().toString().equals(list[0])) {
                    String gesturePassword = aCache.getAsString(userphone);
                    if (gesturePassword == null || "".equals(gesturePassword)) {
                        startActivity(new Intent(SafeActivity.this, CreateGestureActivity.class).putExtra("ok", "1"));
                    }
                }*//*
                break;*/
           /* case R.id.tv_WordLock:
                waySlideUp.hide();
               // lock.setText(list[1]);
                break;*/
          /*  case R.id.bt_CancelWay:
                waySlideUp.hide();
                break;*/
         /*   case R.id.way_Visiable:
                waySlideUp.hide();
                break;
            case R.id.tv_save:
                Save();
                break;*/
        }
    }

    private void exit() {

        safeSettingDialog = new SafeInformationDialog(this);
        safeSettingDialog.setYesOnclickListener("保存", new SafeInformationDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                //保存
                safeSettingDialog.dismiss();
                finish();
            }
        });
        safeSettingDialog.setNoOnclickListener("清除", new SafeInformationDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                //清除
                safeSettingDialog.dismiss();
                deleteDate();
            }
        });
        safeSettingDialog.show();

    }
    private String dateTime;
    /**
     * 资料清除
     */
    private void deleteDate() {
        String token = (String) SPUtils.get(this, "token", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_Ip_url + Urls.update.DELETE_DATE)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.getInt("error_code");
                            String msg = object.getString("error_message");
                            if (code == 0) {
                                ToastUtils.showToast(SafeActivity.this, "清除成功,档案将在一天后清除");
                                long l = System.currentTimeMillis();
                                dateTime = stampToDate(String.valueOf(l));
                                tv_time.setText("当前档案将于 "+dateTime+" 日后自动清除");
                                saveBean.setPeriod(dateTime);
                                selectTime.setRightString("无数据");
                                if (tv_time.getText().toString().equals(period) && selectTime.getRightString().equals(manage)) {
                                    finish();
                                } else {
                                    Intent intent = new Intent();
                                    setResult(Urls.SettingResultCode.SAFE_DATE, intent);
                                    finish();
                                }
                            }else if(code==2){
                                Intent intent=new Intent(SafeActivity.this,LoginActivity.class);
                                intent.putExtra("message",msg);
                                intent.putExtra("from","SafeActivity");
                                startActivityForResult(intent, Urls.REQUEST_CODE.PULLBLIC_CODE);
                            }else if(code==Urls.ERROR_CODE.FREEZING_CODE){
                                Intent intent=new Intent(SafeActivity.this,LoginActivity.class);
                                intent.putExtra("message","1136");
                                intent.putExtra("from","1136");
                                startActivity(intent);
                                finish();

                            } else {
                                ToastUtils.showToast(SafeActivity.this, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(timeSlideUp.isVisible()){
                timeSlideUp.hide();
                return false;
            }else {
                if (tv_time.getText().toString().equals(period) && selectTime.getRightString().equals(manage)) {
                    finish();
                } else {
                    Intent intent = new Intent();
                    setResult(Urls.SettingResultCode.SAFE_DATE, intent);
                    finish();
                }
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Urls.REQUEST_CODE.PULLBLIC_CODE){
            deleteDate();
        }
    }
}

