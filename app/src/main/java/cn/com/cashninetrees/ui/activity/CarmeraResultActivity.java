package cn.com.cashninetrees.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.AppApplication;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.bean.CameraEvent;
import cn.com.cashninetrees.model.CardBean;
import cn.com.cashninetrees.utils.LogUtils;
import cn.com.cashninetrees.utils.SPUtil;
import cn.com.cashninetrees.utils.ToastUtils;
import cn.com.cashninetrees.view.RoundButton;
import okhttp3.Call;
import okhttp3.Response;

public class CarmeraResultActivity extends AppCompatActivity {
    @Bind(R.id.bt_backButton)
    RoundButton btBackButton;
    @Bind(R.id.bt_saveButton)
    RoundButton btSaveButton;
    @Bind(R.id.image_back_layout)
    LinearLayout imageBackLayout;
    @Bind(R.id.photoView)
    ImageView photoView;
    @Bind(R.id.Card)
    TextView Card;
    private String path;
    private static boolean flag = false;

    private String userToken;
    private static final String positive_photo = "positive_photo";

    private mHandlerCarmera mHandler = new mHandlerCarmera(this);
    private static CardBean.OutputsBean.OutputValueBean.DataValueBean value;
    private KProgressHUD hd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carmera_result);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarColor(R.color.window_background)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();
        hd = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加密存储中,请稍后...")
                .setDimAmount(0.5f);

        Bundle bundle = getIntent().getBundleExtra("bundle");


        value = (CardBean.OutputsBean.OutputValueBean.DataValueBean) bundle.getSerializable("carmera");


        path = bundle.getString("path");

          //btSaveButton.setText("保存" + "\n" + "图片+文字");
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .placeholder(R.mipmap.image_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(this).load(path).apply(options).into(photoView);

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PictureActivity.newIntent(CarmeraResultActivity.this, path, "手持身份证照片");
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        CarmeraResultActivity.this, photoView, "身份证照片");
                try {
                    ActivityCompat.startActivity(CarmeraResultActivity.this, intent, optionsCompat.toBundle());
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    startActivity(intent);
                }

            }
        });


        Card.setText("姓   名: " + value.getName() + "\n" + "姓   别 : " + value.getSex() + "\n" + "民   族 : " + value.getNationality() + "\n" + "出生日期 : " + value.getBirth() + "\n" + "身份证号 : " + value.getNum() + "\n" + "住   址 : " + value.getAddress());


    }

    @OnClick({R.id.bt_backButton, R.id.bt_saveButton, R.id.image_back_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_backButton:
                getToken();
                break;
            case R.id.bt_saveButton:
                flag = true;
                getToken();
                break;
            case R.id.image_back_layout:
                finish();
                break;
        }
    }

    private void getToken() {
        hd.show();

        userToken= SPUtil.getString(this, Urls.lock.TOKEN);
        Map<String, String> parms = new HashMap<>();
        parms.put("token", userToken);
        parms.put("signature", "");
        parms.put("source", "");

        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_URL + Urls.Pictrue.GET_QINIUTOKEN)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        hd.dismiss();
                        try {
                            JSONObject object = new JSONObject(s);
                            String isSuccess = object.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                String status = object.getString("status");
                                    String qiNiuToken = object.getString("token");
                                    savePicture(qiNiuToken);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hd.dismiss();
                            }
                        });
                    }
                });


    }

    private void savePicture(String qiNiuToken) {
        UploadOptions uploadOptions1 = new UploadOptions(null, null, false,
                new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {
                    }
                }, null);
        AppApplication.getUploadManager().put(path, null, qiNiuToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                hd.dismiss();

                if (info.isOK()) {
                    try {
                        String key1 = response.getString("key");
                        Message message = Message.obtain();
                        message.obj = key1;
                        message.what = 1;
                        mHandler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    hd.dismiss();
                    ToastUtils.showToast(CarmeraResultActivity.this, "保存失败,请重新尝试");
                }
            }
        }, uploadOptions1);
    }


    private static class mHandlerCarmera extends Handler {
        private WeakReference<CarmeraResultActivity> mWeakReference;

        mHandlerCarmera(CarmeraResultActivity activity) {
            mWeakReference = new WeakReference<CarmeraResultActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            CarmeraResultActivity activity = mWeakReference.get();

            String token= SPUtil.getString(activity, Urls.lock.TOKEN);
            if(token!=null){
                if (activity != null) {
                    switch (msg.what) {
                        case 1:
                            UpLoadImage(activity, token, s, "0", positive_photo);
                            break;
                    }
                }
            }else {
                ToastUtils.showToast(activity,"账号异常请重新登陆");
            }

        }



        private void UpLoadImage(final Activity activity, String tolen, String url, String var, String photo) {
            Map<String, String> parms = new HashMap<>();
            parms.put(photo, url);
            parms.put("type", "1");
            parms.put("token",tolen);
            parms.put("source", "");
            JSONObject jsonObject = new JSONObject(parms);
            OkGo.<String>post(Urls.Ip_url + Urls.Pictrue.UpLoadImage)
                    .tag(this)
                    .upJson(jsonObject)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject object = new JSONObject(s);
                                int error_code = object.getInt("error_code");
                                if (error_code==0) {
                                    String data = object.getString("data");
                                    JSONObject json=new JSONObject(data);
                                    String msg = json.getString("msg");
                                    ToastUtils.showToast(activity, msg);
                                    if (flag) {
                                        EventBus.getDefault().post(new CameraEvent(value));
                                    }
                                    ToastUtils.showToast(activity, "保存成功");
                                    activity.finish();
                                } else {
                                    String msg = object.getString("error_message");
                                    ToastUtils.showToast(activity, msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }

                        }
                    });

        }
    }
}


