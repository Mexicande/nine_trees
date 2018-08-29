package cn.com.cashninetrees.ui.activity;

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
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
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

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.bean.IdentitySave;
import cn.com.cashninetrees.interfaceutils.IdentCallBack;
import cn.com.cashninetrees.interfaceutils.Identivity_interface;
import cn.com.cashninetrees.model.InformationEvent;
import cn.com.cashninetrees.model.event.ProfessionalSelectEvent;
import cn.com.cashninetrees.ui.fragment.dialogfragment.IdentitySaveFragment;
import cn.com.cashninetrees.ui.fragment.information.User_Bank_Fragment;
import cn.com.cashninetrees.ui.fragment.information.User_MeansFragment;
import cn.com.cashninetrees.ui.fragment.information.User_Professional_Fragment;
import cn.com.cashninetrees.utils.LogUtils;
import cn.com.cashninetrees.view.supertextview.SuperTextView;

import static org.greenrobot.eventbus.EventBus.getDefault;

/**
 * @author apple
 * 身份材料
 */
public class IdentityinformationActivity extends AutoLayoutActivity implements IdentitySaveFragment.SaveFragmentListener{
    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.layout_go)
    LinearLayout layoutGo;
    @Bind(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    private static final String[] CHANNELS = new String[]{"个人信息", "银行信息", "职业信息"};
    @Bind(R.id.slide_View)
    RelativeLayout slideView;
    @Bind(R.id.stv_supertext)
    SuperTextView stvSupertext;
    private boolean mean=false;
    private boolean bank=false;
    private boolean profess=false;
    private IdentitySaveFragment mSaveFragment;
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private FragmentManager mFragmentManager;
    private SlideUp slideUp;
    private static final int IMAGE_RESULT = 110;

    private static final int STUDENT = 2;
    private static final int COMPANY = 1;
    private static final int BUSINESS = 3;
    private static final int FREE = 4;
    private Fragment mCurrentFragment;
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    private  IdentitytListener listener;

    @Override
    public void exit(int type) {
        if(type==1){
            Intent intent = new Intent();
            intent.putExtra("ok", 1);
            setResult(IMAGE_RESULT, intent);
            finish();
        }else {
            if(bank){
                EventBus.getDefault().post(new InformationEvent("exitBank"));
            }
            if(mean){
                EventBus.getDefault().post(new InformationEvent("exitmean"));
            }
            if(profess){
                EventBus.getDefault().post(new InformationEvent("exitprofess"));
            }
            Intent intent = new Intent();
            intent.putExtra("ok", 1);
            setResult(IMAGE_RESULT, intent);
            finish();
        }
    }

    public  static interface IdentitytListener {
        //跳到h5页面
        boolean verifymean();
        boolean verifybank();
        boolean verifyprofress();
    }
    private Identivity_interface identivity_interface;


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
        getDefault().register(this);
        initToolbar();
        initMagicIndicator();
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
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
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(UIUtil.dip2px(context, 90));
                return indicator;
            }

        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(this, 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));

    }

    private void initToolbar() {
        titleName.setText("身份材料");
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
    public void updateEvent(ProfessionalSelectEvent msg) {
        if (msg.message == 0) {
            slideUp.show();
        }
    }
    @Subscribe
    public void getSave(IdentitySave msg) {
        mean=msg.mean;
        bank=msg.bank;
        profess=msg.profession;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDefault().unregister(this);

    }

    @Subscribe
    private String getFragmentName(int menuId) {
        switch (menuId) {
            case 1:
                stvSupertext.setCenterString("独创AWsafe数据加密，为您的信息安全保驾护航");
                stvSupertext.setCenterTvDrawableLeft(getResources().getDrawable(R.mipmap.iv_top_safe));
                return User_MeansFragment.class.getName();
            case 2:
                stvSupertext.setCenterString("自主研发AWeye监测，及时拦截非法入侵");
                stvSupertext.setCenterTvDrawableLeft(getResources().getDrawable(R.mipmap.iv_see));

                return User_Bank_Fragment.class.getName();
            case 3:
                stvSupertext.setCenterString("我们会严格保护您的隐私安全，请放心填写");
                stvSupertext.setCenterTvDrawableLeft(getResources().getDrawable(R.mipmap.iv_bursa));
                return User_Professional_Fragment.class.getName();
            default:
                return null;
        }
    }

    private void switchMenu(String fragmentName) {

        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentName);

        if (fragment != null) {
            if (fragment == mCurrentFragment) {
                return;
            }

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
            R.id.tv_Freelancer, R.id.bt_cancel, R.id.layout_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutGo:
                slideUp.hide();
                break;
            case R.id.tv_Company:
                getDefault().post(new ProfessionalSelectEvent(COMPANY));
                slideUp.hide();
                break;
            case R.id.tv_Business:
                getDefault().post(new ProfessionalSelectEvent(BUSINESS));
                slideUp.hide();
                break;
            case R.id.tv_Freelancer:
                getDefault().post(new ProfessionalSelectEvent(FREE));
                slideUp.hide();
                break;
            case R.id.bt_cancel:
                slideUp.hide();
                break;
            case R.id.layout_go:

                EventBus.getDefault().post(new InformationEvent("saveBank"));
                EventBus.getDefault().post(new InformationEvent("mean"));
                EventBus.getDefault().post(new InformationEvent("profess"));

                if(bank||mean||profess){
                    if(mSaveFragment==null){
                        mSaveFragment=new IdentitySaveFragment();
                    }
                    mSaveFragment.show(getSupportFragmentManager(),"LoginDialogFragment");
                }else{Intent intent = new Intent();
                intent.putExtra("ok", 1);
                setResult(IMAGE_RESULT, intent);
                finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (slideUp.isVisible()) {
                slideUp.hide();
                return false;
            } else {
                EventBus.getDefault().post(new InformationEvent("mean"));
                EventBus.getDefault().post(new InformationEvent("saveBank"));
                EventBus.getDefault().post(new InformationEvent("profess"));
                if(bank||mean||profess){
                    if(mSaveFragment==null){
                        mSaveFragment=new IdentitySaveFragment();
                    }
                    mSaveFragment.show(getSupportFragmentManager(),"LoginDialogFragment");
                    return false;
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("ok", 1);
                    setResult(IMAGE_RESULT, intent);
                    finish();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
