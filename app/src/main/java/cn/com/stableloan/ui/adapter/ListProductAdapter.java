package cn.com.stableloan.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.bean.ProductListBean;
import cn.com.stableloan.utils.LogUtils;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static cn.com.stableloan.bean.ProductListBean.*;

/**
 * Created by apple on 2017/5/22.
 */

public class ListProductAdapter  extends BaseQuickAdapter<ProductListBean.ProductBean,BaseViewHolder>{

    public ListProductAdapter(List<ProductListBean.ProductBean> data) {
        super(R.layout.product_trem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {

            Glide.with(mContext).load(R.mipmap.logo).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .bitmapTransform(new CropCircleTransformation(mContext)).into((ImageView) helper.getView(R.id.ic_product_logo));

            helper.setText(R.id.product_list_name,item.getPname());
            helper.setText(R.id.product_list_desc,item.getProduct_introduction());
            helper.setText(R.id.rate,item.getMin_algorithm());
            helper.setText(R.id.tv_Limit,item.getMinimum_amount()+"~"+item.getMaximum_amount());
        if(mData.indexOf(item)>=3){
            helper.getView(R.id.top).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.top).setVisibility(View.VISIBLE);
        }


    }

}
