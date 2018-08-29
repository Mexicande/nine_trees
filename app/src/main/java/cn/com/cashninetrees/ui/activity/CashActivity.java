package cn.com.cashninetrees.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import cn.com.cashninetrees.base.BaseActivity;
import cn.com.cashninetrees.bean.CashEvent;
import cn.com.cashninetrees.model.integarl.CashBean;
import cn.com.cashninetrees.ui.activity.integarl.RuleDescActivity;
import cn.com.cashninetrees.ui.adapter.CashAdapter;
import cn.com.cashninetrees.ui.adapter.MyViewPagerAdapter;
import cn.com.cashninetrees.ui.fragment.cash.DetailCash_Fragment;
import cn.com.cashninetrees.ui.fragment.cash.GetCash_Fragment;

/**
 * 我的现金
 * @author apple
 */
public class CashActivity extends BaseActivity {

    @Bind(R.id.total)
    TextView total;
    @Bind(R.id.cash_Indicator)
    MagicIndicator cashIndicator;
    @Bind(R.id.cash_ViewPager)
    ViewPager cashViewPager;

    private CashAdapter cashAdapter;

    private CashBean cashBean;
    private static int ACTION_NEWS = 1;

    private static final int REQUEST_CODE = 1;
    private static final int RESULT_CODE = 200;
    private static final String[] CHANNELS = new String[]{"现金明细", "获取现金"};

    private List<String> mDataList = Arrays.asList(CHANNELS);

    private MyViewPagerAdapter myViewPagerAdapter;
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    public static void launch(Context context) {
        context.startActivity(new Intent(context, CashActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mFragmentContainerHelper.attachMagicIndicator(cashIndicator);
        initFragments();
    }

    private void initFragments() {
        List<Fragment> list = new ArrayList<>();
        list.add(new DetailCash_Fragment());
        list.add(new GetCash_Fragment());
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), list);
        cashViewPager.setAdapter(myViewPagerAdapter);
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
                        cashViewPager.setCurrentItem(i);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);
                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setColors(Color.parseColor("#FC4445"));
                indicator.setLineWidth(UIUtil.dip2px(context, 100));

                return indicator;
            }

        });
        cashIndicator.setNavigator(commonNavigator);
        // must after setNavigator
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(this, 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));

        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(cashIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        cashViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });
    }

    @OnClick({R.id.cash_back, R.id.tv_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cash_back:
                Intent intent=new Intent();
                setResult(100,intent);
                finish();
                break;
            case R.id.tv_rule:
                RuleDescActivity.launch(this);
                break;
            default:
                break;

        }
    }

    @Subscribe
    public void updateEvent(CashEvent msg) {
        if (msg != null) {
            if (msg.cash!=null) {
                total.setText(msg.cash);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            setResult(100,intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
