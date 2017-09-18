package cn.com.stableloan.ui.activity.integarl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mancj.slideup.SlideUp;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.bean.ImageDataBean;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import okhttp3.Call;
import okhttp3.Response;

public class UpImageIdentityActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.tv_IdentificationCard)
    TextView tvIdentificationCard;
    @Bind(R.id.tv_BusinessCard)
    TextView tvBusinessCard;
    @Bind(R.id.tv_JobCard)
    TextView tvJobCard;
    @Bind(R.id.tv_DebitCard)
    TextView tvDebitCard;
    @Bind(R.id.tv_CreditCard)
    TextView tvCreditCard;
    @Bind(R.id.iv_IdentityUp)
    ImageView ivIdentityUp;
    @Bind(R.id.iv_IdentityDown)
    ImageView ivIdentityDown;
    @Bind(R.id.iv_IdentityHead)
    ImageView ivIdentityHead;
    @Bind(R.id.iv_business)
    ImageView ivBusiness;
    @Bind(R.id.iv_job)
    ImageView ivJob;
    @Bind(R.id.iv_debit)
    ImageView ivDebit;
    @Bind(R.id.iv_credit)
    ImageView ivCredit;
    @Bind(R.id.slide_image)
    RelativeLayout slideImage;
    private String token="";
    private SlideUp ImageSlideUp;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, UpImageIdentityActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_identity);
        ButterKnife.bind(this);
        initToolbar();
        getImageDate();
    }

    private void getImageDate() {

        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        ivBusiness.measure(w, h);
        int height =ivBusiness.getMeasuredHeight();
        int width =ivBusiness.getMeasuredWidth();
        LogUtils.i("ivBusiness===","\n"+height+","+width);


        /*token = (String) SPUtils.get(this, "token", "1");
        String signature = (String) SPUtils.get(this, "signature", "1");
        Map<String, String> parms1 = new HashMap<>();
        parms1.put("token", token);
        parms1.put("signature", signature);
        JSONObject json = new JSONObject(parms1);
        OkGo.<String>post(Urls.NEW_URL + Urls.Pictrue.Get_Pictrue)
                .tag(this)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if(s!=null){
                            Gson gson=new Gson();
                            ImageDataBean imageDataBean = gson.fromJson(s, ImageDataBean.class);
                            if(imageDataBean.getError_code()==0){

                            }
                        }
                    }
                });*/

    }

    private void initToolbar() {
        ivBack.setVisibility(View.VISIBLE);
        titleName.setText("信用证明");
        tvSave.setVisibility(View.VISIBLE);

        setTextViewColor(tvIdentificationCard, tvIdentificationCard.getText().toString());
        setTextViewColor(tvBusinessCard, tvBusinessCard.getText().toString());
        setTextViewColor(tvJobCard, tvJobCard.getText().toString());
        setTextViewColor(tvDebitCard, tvDebitCard.getText().toString());
        setTextViewColor(tvCreditCard, tvCreditCard.getText().toString());

        ImageSlideUp=new SlideUp.Builder(slideImage)
                .withListeners(new SlideUp.Listener.Slide() {
                    @Override
                    public void onSlide(float percent) {
                    }
                })
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
    }

    private void setTextViewColor(TextView view, String s) {
        SpannableString spanString = new SpannableString(s);
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.product_limit));
        spanString.setSpan(span, 0, 7, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new AbsoluteSizeSpan(15), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(spanString);
    }

    @OnClick({R.id.take_identity, R.id.take_business, R.id.take_job, R.id.take_debit,
            R.id.take_credit, R.id.image_Visiable, R.id.tv_SlideUp, R.id.tv_SlideDown,
            R.id.tv_SlideHead, R.id.bt_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.take_identity:
                ImageSlideUp.show();
                break;
            case R.id.take_business:

                break;
            case R.id.take_job:

                break;
            case R.id.take_debit:

                break;
            case R.id.take_credit:

                break;
            case R.id.image_Visiable:
                ImageSlideUp.hide();
                break;
            case R.id.tv_SlideUp:
                ImageSlideUp.hide();
                break;
            case R.id.tv_SlideDown:
                ImageSlideUp.hide();

                break;
            case R.id.tv_SlideHead:
                ImageSlideUp.hide();

                break;
            case R.id.bt_cancel:
                ImageSlideUp.hide();

                break;

        }
    }


}
