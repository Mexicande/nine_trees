package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
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
import cn.com.stableloan.model.MessageEvent;
import cn.com.stableloan.model.PicStatusEvent;
import cn.com.stableloan.ui.activity.integarl.UpImageIdentityActivity;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.cache.ACache;
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
    public void onPicSatus(PicStatusEvent event){
        if("update".equals(event.status)){
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
        ZhugeSDK.getInstance().track(this, "图片材料页",  eventObject);


        String token = (String) SPUtils.get(this, "token", "1");
        String signature = (String) SPUtils.get(this, "signature", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("signature", signature);
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
                            if (error_code==0) {
                                String data = object.getString("data");
                                JSONObject jsonObject1=new JSONObject(data);
                                    String step1 = jsonObject1.getString("step1");
                                        if("1".equals(step1)){
                                            identity.setRightString("已完成");
                                        }else {
                                            identity.setRightString("未完成");
                                        }
                                    String step2 = jsonObject1.getString("step2");
                                    if("1".equals(step2)){
                                        bank.setRightString("已完成");
                                        }else {
                                        bank.setRightString("未完成");
                                        }
                                    String step3 = jsonObject1.getString("step3");

                                    if("1".equals(step3)){
                                        CreditBank.setRightString("已完成");
                                        }else {
                                        CreditBank.setRightString("未完成");
                                        }
                                    String step4 = jsonObject1.getString("step4");

                                    if("1".equals(step4)){
                                        camp.setRightString("已完成");
                                        }else {
                                        camp.setRightString("未完成");
                                        }
                                    String step5 = jsonObject1.getString("step5");

                                    if("1".equals(step5)){
                                        userCard.setRightString("已完成");
                                        }else {
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

        titleName.setText("图片信息");
    }

    @OnClick({R.id.identity, R.id.bank, R.id.CreditBank, R.id.camp, R.id.userCard, R.id.layout_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.identity:
                UpImageIdentityActivity.launch(this);
               // startActivity(new Intent(this, IdentityUploadActivity.class));
                break;
            case R.id.bank:
                UpImageIdentityActivity.launch(this);

                //startActivity(new Intent(this, BankUploadActivity.class));

                break;
            case R.id.CreditBank:
                startActivity(new Intent(this, CreditBankUploadActivity.class));
                break;
            case R.id.camp:
                startActivity(new Intent(this, BusinessUploadActivity.class));
                break;
            case R.id.userCard:
                startActivity(new Intent(this, CardUploadActivity.class));
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
