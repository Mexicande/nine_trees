package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

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
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
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

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ImageActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initToolbar();
        getPicStatus();

    }
    @Subscribe
    public void onPicSatus(PicStatusEvent event){
        getPicStatus();
    }
    private void getPicStatus() {

        String token = (String) SPUtils.get(this, "token", "1");
        String signature = (String) SPUtils.get(this, "signature", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("signature", signature);
        JSONObject jsonObject = new JSONObject(parms);

        OkGo.<String>post(Urls.NEW_URL + Urls.STATUS.GetPictrueStatus)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            String isSuccess = object.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                String status = object.getString("status");
                                if ("1".equals(status)) {

                                    String step1 = object.getString("step1");
                                        if("1".equals(step1)){
                                            identity.setRightString("已完成");
                                        }else {
                                            identity.setRightString("未完成");
                                        }
                                    String step2 = object.getString("step2");

                                    if("1".equals(step2)){
                                        bank.setRightString("已完成");
                                        }else {
                                        bank.setRightString("未完成");
                                        }
                                    String step3 = object.getString("step3");

                                    if("1".equals(step3)){
                                        CreditBank.setRightString("已完成");
                                        }else {
                                        CreditBank.setRightString("未完成");
                                        }
                                    String step4 = object.getString("step4");

                                    if("1".equals(step4)){
                                        camp.setRightString("已完成");
                                        }else {
                                        camp.setRightString("未完成");
                                        }
                                    String step5 = object.getString("step5");

                                    if("1".equals(step5)){
                                        userCard.setRightString("已完成");
                                        }else {
                                        userCard.setRightString("未完成");
                                        }

                                }else {
                                    Intent intent = new Intent(ImageActivity.this, Verify_PasswordActivity.class).putExtra("from","PicStatus");
                                    startActivity(intent);
                                    finish();
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
                startActivity(new Intent(this, IdentityUploadActivity.class));
                break;
            case R.id.bank:
                startActivity(new Intent(this, BankUploadActivity.class));

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
