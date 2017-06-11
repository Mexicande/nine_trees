package cn.com.stableloan.ui.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.bean.ProductListBean;
import cn.com.stableloan.model.News_ClassBean;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static cn.com.stableloan.bean.ProductListBean.*;

/**
 * Created by apple on 2017/5/22.
 */

public class ListProductAdapter  extends BaseQuickAdapter<News_ClassBean.ProductBean,BaseViewHolder>{

    public ListProductAdapter(List<News_ClassBean.ProductBean> data) {
        super(R.layout.news_product_trem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, News_ClassBean.ProductBean item) {

            Glide.with(mContext).load(item.getProduct_logo()).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .bitmapTransform(new CropCircleTransformation(mContext)).into((ImageView) helper.getView(R.id.ic_product_logo));

            SuperTextView view = (SuperTextView) helper.getView(R.id.label1);
            view.setTextColor(Color.parseColor("#"+"b39833"));
            view.setSolid(Color.parseColor("#"+"fef8de"));
            helper.setText(R.id.product_list_desc,item.getProduct_introduction())
                    .setText(R.id.tv_Pname,item.getPname());



    }

}
