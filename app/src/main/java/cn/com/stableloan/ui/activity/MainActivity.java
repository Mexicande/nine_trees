package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.UpdateInfoBean;
import cn.com.stableloan.ui.adapter.MyViewPagerAdapter;
import cn.com.stableloan.ui.adapter.NoTouchViewPager;
import cn.com.stableloan.ui.fragment.HomeFragment;
import cn.com.stableloan.ui.fragment.LotteryFragment;
import cn.com.stableloan.ui.fragment.ProductFragment;
import cn.com.stableloan.ui.fragment.UserFragment;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.NormalItemView;
import ezy.boost.update.IUpdateParser;
import ezy.boost.update.UpdateInfo;
import ezy.boost.update.UpdateManager;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tab)
    PageBottomTabLayout tab;


    public static NavigationController navigationController;

    private FragmentManager mFragmentManager;

    private Fragment mCurrentFragment;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        CheckVsion();
        initView();
    }

    private void CheckVsion() {
        boolean contains = SPUtils.contains(this, "time");

        // String time1 = (String) SPUtils.get(this, "time", "1");

        SimpleDateFormat  formatter  =  new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(new Date());

        if(!contains){
            SPUtils.put(this,"time",time);
            VisionTest();

        }else {
            String time2 = (String) SPUtils.get(this, "time", "1");
            if(time2!=null&&!time2.equals(time)){
                VisionTest();
                SPUtils.put(this,"time",time);

            }
        }

    }

    private void VisionTest() {
     /*   SimpleDateFormat   formatter   =   new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(new Date());
        LogUtils.i("time",time);
        SPUtils.put(this,"time",time);*/

        String url="http://www.shoujiweidai.com/update/versions.json";

        UpdateManager.create(this).setUrl(url).setNotifyId(998).setParser(new IUpdateParser() {
            @Override
            public UpdateInfo parse(String source) throws Exception {
                LogUtils.i("source",source);
                UpdateInfo info = new UpdateInfo();
                Gson gson=new Gson();
                UpdateInfoBean infoBean = gson.fromJson(source, UpdateInfoBean.class);
                info.hasUpdate = true;
                info.updateContent =infoBean.getUpdateContent();
                info.versionCode = infoBean.getVersionCode();
                info.versionName = infoBean.getVersionName();
                info.url = infoBean.getUrl();
                info.md5 = infoBean.getMd5();
                info.size = infoBean.getSize();
                info.isForce = false;
                info.isIgnorable = false;
                info.isAutoInstall=true;
                info.isSilent = false;
                return info;
            }
        }).setManual(true).check();
    }

    private void initView() {
        ImmersionBar.with(this)
                .navigationBarColor(R.color.mask)
                .barAlpha(0.2f)
                .init();

        mCurrentFragment = new HomeFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.app_item, mCurrentFragment).commit();


        navigationController = tab.custom()
                .addItem(newItem(R.mipmap.ic_home_defaual, R.mipmap.ic_home_down,"首页"))
                .addItem(newItem(R.mipmap.ic_product_on, R.mipmap.ic_product_down,"产品"))
                .addItem(newItem(R.mipmap.ic_lottery_on, R.mipmap.ic_lottery_down,"彩票"))
                .addItem(newItem(R.mipmap.ic_my_defual, R.mipmap.ic_my_down,"我的"))
                .build();
/*        ArrayList<Fragment> list=new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new ProductFragment());
        list.add(new LotteryFragment());
        list.add(new UserFragment());*/

        navigationController.addTabItemSelectedListener(listener);


     /* pagerAdapter=new MyViewPagerAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);

        navigationController.setupWithViewPager(viewPager);
     */
    }

    OnTabItemSelectedListener listener = new OnTabItemSelectedListener() {
        @Override
        public void onSelected(int index, int old) {
            switchMenu(getFragmentName(index + 1));
        }

        @Override
        public void onRepeat(int index) {

        }
    };

    private String getFragmentName(int menuId) {
        switch (menuId) {
            case 1:
                return HomeFragment.class.getName();
            case 2:
                return ProductFragment.class.getName();
            case 3:
                return LotteryFragment.class.getName();
            case 4:
                return UserFragment.class.getName();
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

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable,checkedDrawable,text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFFffa900);
        return normalItemView;
    }
    private long mLastBackTime = 0;
    @Override
    public void onBackPressed() {
        // finish while click back key 2 times during 1s.
        if ((System.currentTimeMillis() - mLastBackTime) < 1000) {
            finish();
        } else {
            mLastBackTime = System.currentTimeMillis();
            ToastUtils.showToast(this,"再按一次退出");
        }

    }
}
