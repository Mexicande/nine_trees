package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mancj.slideup.SlideUp;

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
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.view.dialog.SettingPassWordDialog;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 安全设置，存储方式
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
    @Bind(R.id.slide_Safe_Way)
    RelativeLayout slideSafeWay;
    @Bind(R.id.select_time)
    SuperTextView selectTime;
    @Bind(R.id.st_deleteDate)
    SuperTextView stDeleteDate;

    private String[] list;
    private String[] list1;


    private SlideUp timeSlideUp;
    private SlideUp waySlideUp;

    private SaveBean saveBean;
    private SettingPassWordDialog passWordDialog;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SafeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
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
        SaveBean saveBean = (SaveBean) getIntent().getSerializableExtra("save");
        if (saveBean != null) {
            tv_time.setText("当前档案将于"+saveBean.getPeriod()+"2007/07/07日 后自动清除。");
            int i = Integer.parseInt(saveBean.getManaged());
            selectTime.setRightString(list1[i]);
        }
    }

    private void settingTime() {
        int position = 0;
        long l = 0;
        l = dateToStamp(saveBean.getLass_time());
        String string = selectTime.getRightString();
        for (int i = 0; i < list1.length; i++) {
            if (list1[i].equals(string)) {
                position = i;
            }
        }
        long ls = 0;
        switch (position) {
            case 0:
                ls = 604800000;
                break;
            case 1:
                ls = 2592000000L;
                break;
            case 2:
                ls = 7776000000L;
                break;
            case 3:
                ls = 15552000000L;
                break;

        }

        long l1 = l + ls;
        String of = String.valueOf(l1);
        String date = stampToDate(of);

        tv_time.setText(date);
        if (string.equals("永久")) {
            tv_time.setText("永久");
        }

    }

    /**
     * 转为时间戳
     *
     * @param s
     * @return
     */
    public static long dateToStamp(String s) {
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


    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    private void Save() {
        if (tv_time.getText().toString().equals(saveBean.getLass_time())) {

            finish();

        } else {
            String var = "";
            String string = selectTime.getRightString();
            for (int i = 0; i < list1.length; i++) {
                if (list1[i].equals(string)) {
                    var = String.valueOf(i);
                }
            }
          /*String String1 = lock.getText().toString();
            String parrtern = "";
            for (int i = 0; i < list.length; i++) {
                if (list[i].equals(String1)) {
                    parrtern = String.valueOf(i);
                }
            }*/
            String token = (String) SPUtils.get(this, "token", "1");
            Map<String, String> parms = new HashMap<>();
            parms.put("token", token);
            parms.put("managed", var);
            parms.put("period", tv_time.getText().toString());
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

                                    ToastUtils.showToast(SafeActivity.this, msg);
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


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @OnClick({R.id.iv_back, R.id.tv_OneWeek, R.id.tv_OneMonth, R.id.tv_ThreeMonth,
            R.id.tv_SixMonth, R.id.tv_forever, R.id.bt_cancel, R.id.select_time
            , R.id.time_Visiable, R.id.tv_HeaderLock, R.id.tv_WordLock, R.id.bt_CancelWay, R.id.way_Visiable
            , R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (!tv_time.getText().toString().equals(saveBean.getLass_time())) {
                    exit();
                } else {
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
            case R.id.tv_HeaderLock:
                waySlideUp.hide();
               // lock.setText(list[0]);
                final TinyDB tinyDB = new TinyDB(this);
                UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
                String userphone = user.getUserphone();
             /*   if (lock.getText().toString().equals(list[0])) {
                    String gesturePassword = aCache.getAsString(userphone);
                    if (gesturePassword == null || "".equals(gesturePassword)) {
                        startActivity(new Intent(SafeActivity.this, CreateGestureActivity.class).putExtra("ok", "1"));
                    }
                }*/
                break;
            case R.id.tv_WordLock:
                waySlideUp.hide();
               // lock.setText(list[1]);
                break;
            case R.id.bt_CancelWay:
                waySlideUp.hide();
                break;
            case R.id.way_Visiable:
                waySlideUp.hide();
                break;
            case R.id.tv_save:
                Save();
                break;
        }
    }

    private void exit() {

        passWordDialog = new SettingPassWordDialog(this);
        passWordDialog.setYesOnclickListener("是", new SettingPassWordDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                passWordDialog.dismiss();
                finish();
            }
        });
        passWordDialog.setNoOnclickListener("否", new SettingPassWordDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                passWordDialog.dismiss();
            }
        });
        passWordDialog.show();

    }

    private void setTimes(int times) {
        selectTime.setRightString(list1[times]);
        LogUtils.i("time----", list1[times]);
        if (!saveBean.getLass_time().isEmpty()) {
            settingTime();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       /* if (keyCode == KeyEvent.KEYCODE_BACK) {
            lo = aCache.getAsString("lock");
            if (!tv_time.getText().toString().equals(saveBean.getLass_time()) || !lo.equals(lock.getText().toString())) {
                exit();
            } else {
                finish();
            }
        }*/
        return super.onKeyDown(keyCode, event);
    }
}

