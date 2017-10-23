package cn.com.stableloan.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

/**
 * Created by apple on 2017/8/9.
 */

public class ColorFlipPagerTitleView extends AppCompatTextView implements IPagerTitleView {
    private int mNormalColor;
    private int mSelectedColor;
    private float mChangePercent = 0.45f;

    public int getmNormalColor() {
        return mNormalColor;
    }

    public void setmNormalColor(int mNormalColor) {
        this.mNormalColor = mNormalColor;
    }

    public int getmSelectedColor() {
        return mSelectedColor;
    }

    public void setmSelectedColor(int mSelectedColor) {
        this.mSelectedColor = mSelectedColor;
    }

    public float getmChangePercent() {
        return mChangePercent;
    }

    public void setmChangePercent(float mChangePercent) {
        this.mChangePercent = mChangePercent;
    }

    public ColorFlipPagerTitleView(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
        int padding = UIUtil.dip2px(context, 10);
        setPadding(padding, 0, padding, 0);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public void onSelected(int i, int i1) {

    }

    @Override
    public void onDeselected(int i, int i1) {

    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        if (leavePercent >= mChangePercent) {
            setTextColor(mNormalColor);
        } else {
            setTextColor(mSelectedColor);
        }
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if (enterPercent >= mChangePercent) {
            setTextColor(mSelectedColor);
        } else {
            setTextColor(mNormalColor);
        }
    }
}
