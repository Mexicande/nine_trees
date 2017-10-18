package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.PicStatusEvent;
import cn.com.stableloan.ui.activity.integarl.UpImageIdentityActivity;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

public class ImageActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.identity)
    SuperTextView identity;
    @Bind(R.id.bank)
    SuperTextView bank;
    @Bind(R.id.CreditBank)
    SuperTextView CreditBank;
    @Bind(R.id.camp)
    SuperTextView camp;
    @Bind(R.id.userCard)
    SuperTextView userCard;

    private ACache aCache;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ImageActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        aCache = ACache.get(this);

        initToolbar();
        getPicStatus();

    }

    @Subscribe
    public void onPicSatus(PicStatusEvent event) {
        if ("update".equals(event.status)) {
            getPicStatus();
        }

    }

    private void getPicStatus() {
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("authmaterials", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//记录事件
        ZhugeSDK.getInstance().track(this, "图片材料页", eventObject);


        String token = (String) SPUtils.get(this, "token", "1");
        String signature = (String) SPUtils.get(this, "signature", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("signature", signature);
        parms.put("source", "");

        JSONObject jsonObject = new JSONObject(parms);

        OkGo.<String>post(Urls.Ip_url + Urls.STATUS.GetPictrueStatus)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int error_code = object.getInt("error_code");
                            if (error_code == 0) {
                                String data = object.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                String step1 = jsonObject1.getString("step1");

                                if ("1".equals(step1)) {

                                    Drawable drawable = ContextCompat.getDrawable(ImageActivity.this, R.drawable.button_succeed);
                                    identity.setTextBackground(drawable);
                                    identity.setRightString("已完成");
                                } else {
                                    Drawable drawable = ContextCompat.getDrawable(ImageActivity.this, R.drawable.button_fail);
                                    identity.setTextBackground(drawable);
                                    identity.setRightString("未完成");
                                }
                                String step2 = jsonObject1.getString("step2");
                                if ("1".equals(step2)) {
                                    Drawable drawable = ContextCompat.getDrawable(ImageActivity.this, R.drawable.button_succeed);
                                    bank.setTextBackground(drawable);
                                    bank.setRightString("已完成");
                                } else {
                                    Drawable drawable = ContextCompat.getDrawable(ImageActivity.this, R.drawable.button_fail);
                                    bank.setTextBackground(drawable);
                                    bank.setRightString("未完成");
                                }
                                String step3 = jsonObject1.getString("step3");

                                if ("1".equals(step3)) {
                                    Drawable drawable = ContextCompat.getDrawable(ImageActivity.this, R.drawable.button_succeed);
                                    CreditBank.setTextBackground(drawable);
                                    CreditBank.setRightString("已完成");
                                } else {
                                    Drawable drawable = ContextCompat.getDrawable(ImageActivity.this, R.drawable.button_fail);
                                    CreditBank.setTextBackground(drawable);
                                    CreditBank.setRightString("未完成");
                                }
                                String step4 = jsonObject1.getString("step4");

                                if ("1".equals(step4)) {
                                    Drawable drawable = ContextCompat.getDrawable(ImageActivity.this, R.drawable.button_succeed);
                                    camp.setTextBackground(drawable);
                                    camp.setRightString("已完成");
                                } else {
                                    Drawable drawable = ContextCompat.getDrawable(ImageActivity.this, R.drawable.button_fail);
                                    camp.setTextBackground(drawable);
                                    camp.setRightString("未完成");
                                }
                                String step5 = jsonObject1.getString("step5");
                                if ("1".equals(step5)) {
                                    Drawable drawable = ContextCompat.getDrawable(ImageActivity.this, R.drawable.button_succeed);
                                    userCard.setTextBackground(drawable);
                                    userCard.setRightString("已完成");
                                } else {
                                    Drawable drawable = ContextCompat.getDrawable(ImageActivity.this, R.drawable.button_fail);
                                    userCard.setTextBackground(drawable);
                                    userCard.setRightString("未完成");
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });


    }

    private void initToolbar() {

        titleName.setText("图片材料");
    }

    @OnClick({R.id.identity, R.id.bank, R.id.CreditBank, R.id.camp, R.id.userCard, R.id.layout_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.identity:
                UpImageIdentityActivity.launch(this);
                break;
            case R.id.bank:
                UpImageIdentityActivity.launch(this);
                break;
            case R.id.CreditBank:
                UpImageIdentityActivity.launch(this);
                break;
            case R.id.camp:
                UpImageIdentityActivity.launch(this);
                break;
            case R.id.userCard:
                UpImageIdentityActivity.launch(this);
                break;
            case R.id.layout_go:
                finish();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
