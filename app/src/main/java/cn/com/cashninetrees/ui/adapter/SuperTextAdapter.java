package cn.com.cashninetrees.ui.adapter;

import android.graphics.Color;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

import cn.com.cashninetrees.R;
import cn.com.cashninetrees.model.Product_DescBean;

/**
 *
 * @author apple
 * @date 2017/6/15
 *
 */

public class SuperTextAdapter extends BaseQuickAdapter<Product_DescBean.DataBean.LabelsBean,BaseViewHolder> {

    public SuperTextAdapter(List<Product_DescBean.DataBean.LabelsBean> data) {
        super(R.layout.super_text_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,Product_DescBean.DataBean.LabelsBean item) {
        SuperTextView view4 =helper.getView(R.id.labels);
        view4.setStrokeColor(Color.parseColor(item.getFont()));
        view4.setTextColor(Color.parseColor(item.getFont()));
        view4.setText(item.getName());
    }
}
