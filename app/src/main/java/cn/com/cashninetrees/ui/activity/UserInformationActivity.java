package cn.com.cashninetrees.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.api.ApiService;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.bean.Login;
import cn.com.cashninetrees.interfaceutils.OnRequestDataListener;
import cn.com.cashninetrees.model.integarl.StatusBean;
import cn.com.cashninetrees.ui.activity.integarl.UpImageIdentityActivity;
import cn.com.cashninetrees.utils.SPUtil;
import cn.com.cashninetrees.utils.ToastUtils;
import cn.com.cashninetrees.view.supertextview.SuperTextView;

/**
 * 申请材料
 * @author apple
 */
public class UserInformationActivity extends Activity{
    private ImmersionBar immersionBar;
    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.User_information)
    SuperTextView UserInformation;
 /*   @Bind(R.id.User_Authorization)
    SuperTextView UserAuthorization;*/
    @Bind(R.id.User_Pic)
    SuperTextView UserPic;
    private List<View> views;
    private String token;
    private static  final  int IMAGE_REQUEST=100;
    private static  final  int IMAGE_RESULT=110;
    /**
     * #17326b
     * @param context
     */

    public static void launch(Context context) {
        context.startActivity(new Intent(context, UserInformationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        ButterKnife.bind(this);
              immersionBar = ImmersionBar.with(this);
              immersionBar.statusBarColor(R.color.information_title1)
                .fitsSystemWindows(true)
                .statusBarAlpha(0.3f)
                .init();
        initToolbar();
        getStatus();
    }

    private void initToolbar() {
        ivBack.setVisibility(View.VISIBLE);
        titleName.setText("申请材料");
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);

        views = new ArrayList<>();
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.user_infomation_top1,localImageSize,ImageView.ScaleType.FIT_XY));
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.user_information_top_bg,localImageSize,ImageView.ScaleType.FIT_XY));
        BGABanner viewById = (BGABanner) findViewById(R.id.informationBgabanner);
        viewById.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(UserInformationActivity.this)
                        .load(Integer.parseInt(model))
                        .apply(options)
                        .into(itemView);
            }
        });

        viewById.setData(Arrays.asList(String.valueOf(R.drawable.user_infomation_top1),String.valueOf(R.drawable.user_information_top_bg)),null);

    viewById.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==0){
            immersionBar.statusBarColor(R.color.information_title1).init();
        }else if(position==1){
            immersionBar.statusBarColor(R.color.information_title).init();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});

    }

    /**
     * 资料完成状态
     */
    private void getStatus() {

        token = SPUtil.getString(this, Urls.lock.TOKEN);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(Urls.lock.TOKEN, token);
            ApiService.GET_SERVICE(Urls.user.USER_STATUS, jsonObject, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject s) {
                    Gson gson = new Gson();
                    StatusBean statusBean = gson.fromJson(s.toString(), StatusBean.class);
                    if (statusBean.getError_code() == 0) {
                        StatusBean.DataBean data = statusBean.getData();
                        if ("1".equals(data.getStep1())) {
                            Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_succeed);
                            UserInformation.setTextBackground(drawable);
                            UserInformation.setRightString("已完成");
                        } else {
                            Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_fail);
                            UserInformation.setTextBackground(drawable);
                            UserInformation.setRightString("未完成");
                        }
                      /*  if ("1".equals(data.getStep2())) {
                            Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_succeed);
                            UserAuthorization.setTextBackground(drawable);
                            UserAuthorization.setRightString("已完成");
                        } else {
                            Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_fail);
                            UserAuthorization.setTextBackground(drawable);
                            UserAuthorization.setRightString("未完成");
                        }*/
                        if ("1".equals(data.getStep3())) {
                            Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_succeed);
                            UserPic.setTextBackground(drawable);
                            UserPic.setRightString("已完成");
                        } else {
                            Drawable drawable = ContextCompat.getDrawable(UserInformationActivity.this, R.drawable.button_fail);
                            UserPic.setTextBackground(drawable);
                            UserPic.setRightString("未完成");
                        }

                    } else{
                        ToastUtils.showToast(UserInformationActivity.this, statusBean.getError_message());
                    }
                }

                @Override
                public void requestFailure(int code, String msg) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.iv_back, R.id.User_information,  R.id.User_Pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.User_information:
                Intent identityIntent=new Intent(this, IdentityinformationActivity.class);
                startActivityForResult(identityIntent,IMAGE_REQUEST);
                break;
           /* case R.id.User_Authorization:
                Intent autointent=new Intent(this,CertificationActivity.class);
                startActivityForResult(autointent,IMAGE_REQUEST);
                break;*/
            case R.id.User_Pic:
                Intent intent=new Intent(this,UpImageIdentityActivity.class);
                startActivityForResult(intent,IMAGE_REQUEST);
                break;
             default:
                 break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST){
            switch (resultCode){
                case IMAGE_RESULT:
                    getStatus();
                    break;
                 default:
                     break;

            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 刷新数据
     * @param event
     */
    @Subscribe
    public void onMessageEvent(Login event) {
        token= SPUtil.getString(this, Urls.lock.TOKEN);
        getStatus();
    }

}
