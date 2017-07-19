package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.fragment.LoginFragment;
import cn.com.stableloan.ui.fragment.MessageFragment;
import cn.com.stableloan.utils.SwitchMultiButton;

public class Login2Activity extends AppCompatActivity {


    public static SwitchMultiButton switchmultibutton;

    private static final String[] CHANNELS = new String[]{"短信快捷登录", "账号登录"};
    @Bind(R.id.back)
    ImageView back;

    private FragmentManager mFragmentManager;

    private Fragment mCurrentFragment;


    private boolean Flag = false;
    private final int Flag_User = 3000;
    private final int LOTTERY_CODE = 500;


    private String MessageCode = null;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, Login2Activity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_login2);
        ImmersionBar.with(this).statusBarColor(R.color.frame_color)
                .statusBarAlpha(0.8f)
                .fitsSystemWindows(true)
                .init();
        switchmultibutton = (SwitchMultiButton) findViewById(R.id.switchmultibutton);

        ButterKnife.bind(this);

        initView();
    }


    private void initView() {
        mCurrentFragment = new MessageFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.fragment, mCurrentFragment).commitAllowingStateLoss();

        /*changePhone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        changePhone.getPaint().setAntiAlias(true);*/
        assert switchmultibutton != null;
        switchmultibutton.setText(CHANNELS).setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                switchMenu(getFragmentName(position + 1));

            }
        });


    }

    private String getFragmentName(int menuId) {
        switch (menuId) {
            case 1:
                return MessageFragment.class.getName();
            case 2:
                return LoginFragment.class.getName();
            default:
                return null;
        }
    }

    private void switchMenu(String fragmentName) {

        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentName);

        if (fragment != null) {
            if (fragment == mCurrentFragment) return;

            mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        } else {
            fragment = Fragment.instantiate(this, fragmentName);
            mFragmentManager.beginTransaction().add(R.id.fragment, fragment, fragmentName).commitAllowingStateLoss();
        }

        if (mCurrentFragment != null) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).commitAllowingStateLoss();
        }
        mCurrentFragment = fragment;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //返回事件
            String from = getIntent().getStringExtra("from");
            if (from != null) {
                if (from.equals("user")) {
                    UserBean userBean = new UserBean();
                    setResult(Flag_User, new Intent().putExtra("user", userBean));
                    finish();
                } else if (from.equals("123")) {
                    setResult(LOTTERY_CODE, new Intent().putExtra("Loffery", "1"));
                    finish();
                } else if (from.equals("user1")) {
                    UserBean userBean = new UserBean();
                    setResult(4000, new Intent().putExtra("user", userBean));
                    // EventBus.getDefault().post(new InformationEvent("user1"));
                    finish();
                } else if (from.equals("user2")) {
                    EventBus.getDefault().post(new InformationEvent("user2"));
                    finish();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.layout)
    public void onViewClicked() {
        String from = getIntent().getStringExtra("from");
        if (from != null) {
            if (from.equals("user")) {
                UserBean userBean = new UserBean();
                setResult(Flag_User, new Intent().putExtra("user", userBean));
                finish();
            } else if (from.equals("123")) {
                setResult(LOTTERY_CODE, new Intent().putExtra("Loffery", "1"));
                finish();
            } else if (from.equals("user1")) {
                UserBean userBean = new UserBean();
                setResult(4000, new Intent().putExtra("user", userBean));
                //EventBus.getDefault().post(new InformationEvent("user1"));
                finish();
            } else if (from.equals("user2")) {
                EventBus.getDefault().post(new InformationEvent("user2"));
                finish();
            }
        }
    }
}