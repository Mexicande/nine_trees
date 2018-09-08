package cn.com.laifenqicash.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by apple on 2017/10/23.
 */

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
    private int space=0;
    private int dec=0;

    public RecyclerViewDecoration(int space,int left) {
        this.space = space;
        this.dec=left;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.set(0, 0, 10, 3);
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
      /*  if (parent.getChildLayoutPosition(view) %2==0) {
            outRect.left = 0;
        }*/
    }
}
