package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhuge.analysis.stat.ZhugeSDK;
import com.zhy.autolayout.AutoLayoutActivity;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.base.BasePhotoActivity;
import cn.com.stableloan.model.Identity;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.fragment.BankInformationFragment;
import cn.com.stableloan.ui.fragment.ProfessionalInformationFragment;
import cn.com.stableloan.ui.fragment.UserInformationFragment;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
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
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private FragmentManager mFragmentManager;

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
                                    Intent intent = new Intent(IdentityinformationActivity.this,Verify_PasswordActivity.class)
                                            .putExtra("from","integarl");
                                    startActivityForResult(intent,200);
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


    private void initMagicIndicator() {
        mCurrentFragment = new UserInformationFragment();
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
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#FFAE2D"));
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
                indicator.setColors(Color.parseColor("#FFAE2D"));
                return indicator;
            }

        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(1);
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);

    }

    private void initToolbar() {

        titleName.setText("身份信息");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private String getFragmentName(int menuId) {
        switch (menuId) {
            case 1:
                return UserInformationFragment.class.getName();
            case 2:
                return BankInformationFragment.class.getName();
            case 3:
                return ProfessionalInformationFragment.class.getName();
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


    @OnClick(R.id.layout_go)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==200){
            if(resultCode==100){
                initToolbar();
                initMagicIndicator();
                mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
            }
        }
    }
}
