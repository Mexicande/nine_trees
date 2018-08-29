package cn.com.cashninetrees.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mancj.slideup.SlideUp;

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
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.base.BaseActivity;
import cn.com.cashninetrees.bean.IntregarlEvent;
import cn.com.cashninetrees.bean.Login;
import cn.com.cashninetrees.bean.ShareEvent;
import cn.com.cashninetrees.ui.adapter.MyViewPagerAdapter;
import cn.com.cashninetrees.ui.fragment.integral.IntegarlExchangeFragment;
import cn.com.cashninetrees.ui.fragment.integral.IntegarlTaskFragment;
import cn.com.cashninetrees.utils.SPUtil;
import cn.com.cashninetrees.utils.ToastUtils;
import cn.com.cashninetrees.view.share.StateListener;
import cn.com.cashninetrees.view.share.TPManager;
import cn.com.cashninetrees.view.share.WXManager;
import cn.com.cashninetrees.view.share.WXShareContent;

/**
 * @author apple
 * 积分
 */
public class IntegralActivity extends BaseActivity {

    @Bind(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    private static final String[] CHANNELS = new String[]{"积分任务", "积分兑换"};
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.cash_slideView)
    RelativeLayout cashSlideView;
    @Bind(R.id.tv_Credits)
    TextView tvCredits;
    @Bind(R.id.bt_Offical)
    TextView btOffical;
    @Bind(R.id.tv_Liven)
    TextView tvLiven;
    private SlideUp slideUp;
    private static final int INVITE_CODE=200;

    private List<String> mDataList = Arrays.asList(CHANNELS);

    private MyViewPagerAdapter myViewPagerAdapter;
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    private String shareUrl = "";

    public static void launch(Context context) {
        context.startActivity(new Intent(context, IntegralActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initSlide();
        setListener();
        String from = getIntent().getStringExtra("from");

        titleName.setText("积分");
        ivBack.setVisibility(View.VISIBLE);
        initFragments();
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
        if (from != null && "cash".equals(from)) {
            magicIndicator.onPageSelected(1);
            viewPager.setCurrentItem(1);

        }
    }

    private void setListener() {
        TPManager.getInstance().initAppConfig(Urls.KEY.WEICHAT_APPID, null, null, null);
        wxManager = new WXManager(this);
        wxManager.setListener(wxStateListener);

    }

    StateListener<String> wxStateListener = new StateListener<String>() {
        @Override
        public void onComplete(String s) {
            ToastUtils.showToast(IntegralActivity.this, s);
        }

        @Override
        public void onError(String err) {
            ToastUtils.showToast(IntegralActivity.this, err);
        }

        @Override
        public void onCancel() {
            ToastUtils.showToast(IntegralActivity.this, "取消");
        }
    };

    private void initSlide() {
        slideUp = new SlideUp.Builder(cashSlideView)
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


    private void initFragments() {
        List<Fragment> list = new ArrayList<>();
        list.add(new IntegarlTaskFragment());
        list.add(new IntegarlExchangeFragment());
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(myViewPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(i));
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#fb5a5b"));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(i);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);
                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setColors(Color.parseColor("#fb5a5b"));
                indicator.setLineWidth(UIUtil.dip2px(context, 100));

                return indicator;
            }

        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(this, 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));


        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });
    }

    @Subscribe
    public void onMessageEvent(IntregarlEvent event) {
        if (event != null) {
            tvCredits.setText(String.valueOf(event.credit));
            if (event.offica != null) {
                btOffical.setText(event.offica);
            }
            tvLiven.setText(String.valueOf(event.topCredits));
        }
    }


    private WXManager wxManager;

    private void shareWechat(int scence) {
        WXShareContent contentWX = new WXShareContent();
        contentWX.setScene(scence)
                .setType(WXShareContent.share_type.WebPage)
                .setWeb_url(shareUrl)
                .setTitle("安稳钱包")
                .setDescription("this is a config")
                .setImage_url("http://orizavg5s.bkt.clouddn.com/logo.png");
        wxManager.share(contentWX);
    }

    @Override
    public void onBackPressed() {
        if (slideUp.isVisible()) {
            slideUp.hide();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.iv_back, R.id.layoutGo, R.id.layout_wx, R.id.layout_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.layoutGo:
                slideUp.hide();
                break;
            case R.id.layout_wx:
                shareWechat(WXShareContent.WXSession);
                break;
            case R.id.layout_friend:
                shareWechat(WXShareContent.WXTimeline);
                break;
            default:
                break;
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }    }


}
