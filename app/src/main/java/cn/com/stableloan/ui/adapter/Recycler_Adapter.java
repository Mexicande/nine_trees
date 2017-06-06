package cn.com.stableloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.bean.Product;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by apple on 2017/4/11.
 */

public class Recycler_Adapter extends BaseQuickAdapter<String,BaseViewHolder> {



    public Recycler_Adapter(List<String> data) {
        super(R.layout.recycler_item, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {


        Glide.with(mContext).load(item).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.head));

       // Glide.with(mContext).load(R.mipmap.new_product).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.biaoqian));
    }
}
