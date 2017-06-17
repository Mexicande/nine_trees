package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.utils.SharedPreferencesUtil;

/**
 * 引导页
 */
public class GuideActivity extends AppCompatActivity {

    @Bind(R.id.banner_guide_background)
    BGABanner bannerGuideBackground;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, GuideActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        setListener();
        processLogic();
    }
    private void setListener() {

        bannerGuideBackground.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                MainActivity.launch(GuideActivity.this);
                SharedPreferencesUtil.putBoolean(GuideActivity.this, SharedPreferencesUtil.FIRST_OPEN, false);
                finish();
            }
        });
    }
    private void processLogic() {
        // 设置数据源
        bannerGuideBackground.setData(R.mipmap.lod_01, R.mipmap.lod_02, R.mipmap.lod_03);

    }
}
