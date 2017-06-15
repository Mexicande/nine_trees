package cn.com.stableloan.ui.adapter;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.Class_ListProductBean;
import cn.com.stableloan.model.News_ClassBean;
import cn.com.stableloan.model.Product_DescBean;
import cn.com.stableloan.model.Product_Detail;

/**
 * Created by apple on 2017/6/15.
 */

public class SuperTextAdapter extends BaseQuickAdapter<Class_ListProductBean.ProductBean.LabelsBean,BaseViewHolder> {

    public SuperTextAdapter(List<Class_ListProductBean.ProductBean.LabelsBean> data) {
        super(R.layout.super_text_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Class_ListProductBean.ProductBean.LabelsBean item) {
        SuperTextView view4 =(SuperTextView)helper.getView(R.id.labels);

        view4.setStrokeColor(Color.parseColor(item.getFont()));
        view4.setSolid(Color.parseColor(item.getBackground()));
        view4.setTextColor(Color.parseColor(item.getFont()));
        view4.setText(item.getName());



    }
}
