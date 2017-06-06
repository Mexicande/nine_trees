package cn.com.stableloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.bean.Product;
import cn.com.stableloan.bean.ProductListBean;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by apple on 2017/4/11.
 */

public class Recycler_Classify_Adapter extends BaseQuickAdapter<ProductListBean.ProductBean,BaseViewHolder> {



    public Recycler_Classify_Adapter(List<ProductListBean.ProductBean> data) {
        super(R.layout.product_trem, data);

    }

    private String str = "http://www.shoujiweidai.com";
    @Override
    protected void convert(BaseViewHolder helper, ProductListBean.ProductBean item) {

        //helper.setText(R.id.tv_ProductName,item.getPname());

        //Glide.with(mContext).load(str+item.getLogo()).crossFade().centerCrop().bitmapTransform(new CropCircleTransformation(mContext) ).placeholder(R.mipmap.logo).placeholder(R.mipmap.logo).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.head));

       // Glide.with(mContext).load(R.mipmap.new_product).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.biaoqian));
    }
}
