package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mancj.slideup.SlideUp;
import com.zhy.autolayout.AutoLayoutActivity;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.event.ProfessionalSelectEvent;
import cn.com.stableloan.ui.fragment.information.User_Bank_Fragment;
import cn.com.stableloan.ui.fragment.information.User_MeansFragment;
import cn.com.stableloan.ui.fragment.information.User_Professional_Fragment;
import cn.com.stableloan.utils.SPUtils;
import okhttp3.Call;
import okhttp3.Response;

public class IdentityinformationActivity extends AutoLayoutActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.layout_go)
    LinearLayout layoutGo;
    @Bind(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    private static final String[] CHANNELS = new String[]{"个人信息", "银行信息", "职业信息"};
    @Bind(R.id.slide_View)
    RelativeLayout slideView;
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private FragmentManager mFragmentManager;
    private SlideUp slideUp;
    private static final int STUDENT = 2;
    private static final int COMPANY = 1;
    private static final int BUSINESS = 3;
    private static final int FREE = 4;
    private Fragment mCurrentFragment;
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    public static void launch(Context context) {
        context.startActivity(new Intent(context, IdentityinformationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_identityinformation);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();
        EventBus.getDefault().register(this);
        initToolbar();
        initMagicIndicator();
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
       /* AppApplication.addDestoryActivity(this,"integarl");
        String integarl = getIntent().getStringExtra("integarl");
        if(integarl!=null){
            getDate();
        }else {

        }*/
    }

    private void getDate() {
        Map<String, String> parms = new HashMap<>();
        String token = (String) SPUtils.get(this, "token", "1");
        String signature = (String) SPUtils.get(this, "signature", "1");
        parms.put("token", token);
        parms.put("signature", signature);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_URL + Urls.Identity.GetIdentity)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String isSuccess = json.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                String status = json.getString("status");
                                if ("1".equals(status)) {

                                    initToolbar();
                                    initMagicIndicator();
                                    mFragmentContainerHelper.attachMagicIndicator(magicIndicator);

                                } else {
                                    Intent intent = new Intent(IdentityinformationActivity.this, Verify_PasswordActivity.class)
                                            .putExtra("from", "integarl");
                                    startActivityForResult(intent, 200);
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


    private void initMagicIndicator() {
        mCurrentFragment = new User_MeansFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.app_item, mCurrentFragment).commit();

        final CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(i));
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#fb5a5b"));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragmentContainerHelper.handlePageSelected(i);
                        switchMenu(getFragmentName(i + 1));
                    }
                });
                return simplePagerTitleView;

            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#fb5a5b"));
                return indicator;
            }

        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(this, 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);

    }

    private void initToolbar() {
        titleName.setText("身份信息");
        slideUp = new SlideUp.Builder(slideView)
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
    @Subscribe
    public  void updateEvent(ProfessionalSelectEvent msg){
        if(msg.message==0){
            slideUp.show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
    @Subscribe


    private String getFragmentName(int menuId) {
        switch (menuId) {
            case 1:
                return User_MeansFragment.class.getName();
            case 2:
                return User_Bank_Fragment.class.getName();
            case 3:
                return User_Professional_Fragment.class.getName();
            default:
                return null;
        }
    }

    private void switchMenu(String fragmentName) {

        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentName);

        if (fragment != null) {
            if (fragment == mCurrentFragment) return;

            mFragmentManager.beginTransaction().show(fragment).commit();
        } else {
            fragment = Fragment.instantiate(this, fragmentName);
            mFragmentManager.beginTransaction().add(R.id.app_item, fragment, fragmentName).commit();
        }

        if (mCurrentFragment != null) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).commit();
        }
        mCurrentFragment = fragment;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == 100) {
                initToolbar();
                initMagicIndicator();
                mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
            }
        }
    }

    @OnClick({R.id.layoutGo, R.id.tv_Company, R.id.tv_Business,
            R.id.tv_Student, R.id.tv_Freelancer, R.id.bt_cancel,R.id.layout_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutGo:
                break;
            case R.id.tv_Company:
                EventBus.getDefault().post(new ProfessionalSelectEvent(COMPANY));
                slideUp.hide();
                break;
            case R.id.tv_Business:
                EventBus.getDefault().post(new ProfessionalSelectEvent(BUSINESS));
                slideUp.hide();
                break;
            case R.id.tv_Student:
                EventBus.getDefault().post(new ProfessionalSelectEvent(STUDENT));
                slideUp.hide();
                break;
            case R.id.tv_Freelancer:
                EventBus.getDefault().post(new ProfessionalSelectEvent(FREE));
                slideUp.hide();
                break;
            case R.id.bt_cancel:
                slideUp.hide();
                break;
            case R.id.layout_go:
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(slideUp.isVisible()){
            slideUp.hide();
        }else {
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }
}
