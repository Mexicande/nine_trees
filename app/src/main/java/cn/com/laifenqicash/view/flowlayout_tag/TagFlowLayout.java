package cn.com.laifenqicash.view.flowlayout_tag;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cn.com.laifenqicash.R;

/**
 * Created by zhy on 15/9/10.
 */
public class TagFlowLayout extends FlowLayout implements TagAdapter.OnDataChangedListener
{
    private TagAdapter mTagAdapter;
    private boolean mAutoSelectEffect = true;
    private int mSelectedMax = -1;//-1为不限制数量
    private static final String TAG = "TagFlowLayout";
    private MotionEvent mMotionEvent;

    private Set<Integer> mSelectedView = new HashSet<Integer>();


    public TagFlowLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        mAutoSelectEffect = ta.getBoolean(R.styleable.TagFlowLayout_auto_select_effect, true);
        mSelectedMax = ta.getInt(R.styleable.TagFlowLayout_max_select, -1);
        ta.recycle();

        if (mAutoSelectEffect)
        {
            setClickable(true);
        }
    }

    public TagFlowLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context)
    {
        this(context, null);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++)
        {
            TagView tagView = (TagView) getChildAt(i);
            if (tagView.getVisibility() == View.GONE) continue;
            if (tagView.getTagView().getVisibility() == View.GONE)
            {
                tagView.setVisibility(View.GONE);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface OnSelectListener
    {
        void onSelected(Set<Integer> selectPosSet);
    }

    private OnSelectListener mOnSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener)
    {
        mOnSelectListener = onSelectListener;
        if (mOnSelectListener != null) setClickable(true);
    }

    public interface OnTagClickListener
    {
        boolean onTagClick(View view, int position, FlowLayout parent);
    }

    private OnTagClickListener mOnTagClickListener;

    public void setOnTagClickListener(OnTagClickListener onTagClickListener)
    {
        mOnTagClickListener = onTagClickListener;
        if (onTagClickListener != null) setClickable(true);
    }


    public void setAdapter(TagAdapter adapter)
    {
        mTagAdapter = adapter;
        mTagAdapter.setOnDataChangedListener(this);
        mSelectedView.clear();
        changeAdapter();

    }

    private void changeAdapter()
    {
        removeAllViews();
        TagAdapter adapter = mTagAdapter;
        TagView tagViewContainer = null;
        HashSet preCheckedList = mTagAdapter.getPreCheckedList();
        for (int i = 0; i < adapter.getCount(); i++)
        {
            View tagView = adapter.getView(this, i, adapter.getItem(i));

            tagViewContainer = new TagView(getContext());
//            ViewGroup.MarginLayoutParams clp = (ViewGroup.MarginLayoutParams) tagView.getLayoutParams();
//            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(clp);
//            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            lp.topMargin = clp.topMargin;
//            lp.bottomMargin = clp.bottomMargin;
//            lp.leftMargin = clp.leftMargin;
//            lp.rightMargin = clp.rightMargin;
            tagView.setDuplicateParentStateEnabled(true);
            if (tagView.getLayoutParams() != null)
            {
                tagViewContainer.setLayoutParams(tagView.getLayoutParams());
            } else
            {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(dip2px(getContext(), 10),
                        dip2px(getContext(), 10),
                        dip2px(getContext(), 10),
                        dip2px(getContext(), 10));
                tagViewContainer.setLayoutParams(lp);
            }

            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            tagView.setLayoutParams(lp);
            tagViewContainer.addView(tagView);
            addView(tagViewContainer);


            if (preCheckedList.contains(i))
            {
                setChildChecked(i, tagViewContainer);

               // tagViewContainer.setChecked(true);
            }

            if (mTagAdapter.setSelected(i, adapter.getItem(i)))
            {
                setChildChecked(i, tagViewContainer);

              /*  mSelectedView.add(i);
                tagViewContainer.setChecked(true);*/
            }
            tagView.setClickable(false);
            final TagView finalTagViewContainer = tagViewContainer;
            final int position = i;
            tagViewContainer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    doSelect(finalTagViewContainer, position);
                    if (mOnTagClickListener != null) {
                        mOnTagClickListener.onTagClick(finalTagViewContainer, position,
                                TagFlowLayout.this);
                    }
                }
            });
        }
        mSelectedView.addAll(preCheckedList);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            mMotionEvent = MotionEvent.obtain(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick()
    {
        if (mMotionEvent == null) return super.performClick();

        int x = (int) mMotionEvent.getX();
        int y = (int) mMotionEvent.getY();
        mMotionEvent = null;

        TagView child = findChild(x, y);
        int pos = findPosByView(child);
        if (child != null)
        {
            doSelect(child, pos);
            if (mOnTagClickListener != null)
            {
                return mOnTagClickListener.onTagClick(child.getTagView(), pos, this);
            }
        }
        return true;
    }


    public void setMaxSelectCount(int count)
    {
        if (mSelectedView.size() > count)
        {
            Log.w(TAG, "you has already select more than " + count + " views , so it will be clear .");
            mSelectedView.clear();
        }
        mSelectedMax = count;
    }

    public Set<Integer> getSelectedList()
    {
        return new HashSet<Integer>(mSelectedView);
    }

    private void doSelect(TagView child, int position)
    {
        if (mAutoSelectEffect)
        {
            if (!child.isChecked())
            {
                //处理max_select=1的情况
                if (mSelectedMax == 1 && mSelectedView.size() == 1)
                {
                    Iterator<Integer> iterator = mSelectedView.iterator();
                    Integer preIndex = iterator.next();
                    TagView pre = (TagView) getChildAt(preIndex);
                    setChildUnChecked(preIndex, pre);
                    setChildChecked(position, child);
                    mSelectedView.remove(preIndex);
                    mSelectedView.add(position);
                } else
                {
                    if (mSelectedMax > 0 && mSelectedView.size() >= mSelectedMax){

                        return;
                    }
                    setChildChecked(position, child);

                    //child.setChecked(true);
                    mSelectedView.add(position);
                }
            } else
            {
               // child.setChecked(false);
                setChildUnChecked(position, child);

                mSelectedView.remove(position);
            }
            if (mOnSelectListener != null)
            {
                mOnSelectListener.onSelected(new HashSet<Integer>(mSelectedView));
            }
        }
    }

    public TagAdapter getAdapter()
    {
        return mTagAdapter;
    }


    private static final String KEY_CHOOSE_POS = "key_choose_pos";
    private static final String KEY_DEFAULT = "key_default";

    private void setChildChecked(int position, TagView view) {

               view.setChecked(true);
        mTagAdapter.onSelected(position, view.getTagView());
          }

   private void setChildUnChecked(int position, TagView view) {
           view.setChecked(false);
            mTagAdapter.unSelected(position, view.getTagView());
        }
    @Override
    protected Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DEFAULT, super.onSaveInstanceState());

        String selectPos = "";
        if (mSelectedView.size() > 0)
        {
            for (int key : mSelectedView)
            {
                selectPos += key + "|";
            }
            selectPos = selectPos.substring(0, selectPos.length() - 1);
        }
        bundle.putString(KEY_CHOOSE_POS, selectPos);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if (state instanceof Bundle)
        {
            Bundle bundle = (Bundle) state;
            String mSelectPos = bundle.getString(KEY_CHOOSE_POS);
            if (!TextUtils.isEmpty(mSelectPos))
            {
                String[] split = mSelectPos.split("\\|");
                for (String pos : split)
                {
                    int index = Integer.parseInt(pos);
                    mSelectedView.add(index);

                    TagView tagView = (TagView) getChildAt(index);
                    if (tagView != null){
                       // tagView.setChecked(true);
                        setChildChecked(index, tagView);


                    }
                }

            }
            super.onRestoreInstanceState(bundle.getParcelable(KEY_DEFAULT));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    private int findPosByView(View child)
    {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++)
        {
            View v = getChildAt(i);
            if (v == child) return i;
        }
        return -1;
    }

    private TagView findChild(int x, int y)
    {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++)
        {
            TagView v = (TagView) getChildAt(i);
            if (v.getVisibility() == View.GONE) continue;
            Rect outRect = new Rect();
            v.getHitRect(outRect);
            if (outRect.contains(x, y))
            {
                return v;
            }
        }
        return null;
    }

    @Override
    public void onChanged()
    {
        mSelectedView.clear();
        changeAdapter();
    }

    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}