package cn.com.cashninetrees.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhy.autolayout.AutoLayoutActivity;


/**
 * Created by apple on 2017/8/2.
 */

public  abstract class BasePhotoActivity extends AutoLayoutActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        preInitData();
    }
    protected abstract int getContentViewResId();

    protected abstract void preInitData();

}
