package cn.com.stableloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.bean.Picture;
import cn.com.stableloan.bean.Product;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by apple on 2017/4/11.
 */

public class Classify_Recycler_Adapter extends BaseQuickAdapter<String,BaseViewHolder> {



    public Classify_Recycler_Adapter(List<String> data) {
        super(R.layout.classify_recycler_item, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

       // helper.setImageResource(R.id.head,R.mipmap.classify_04);
        Glide.with(mContext).load(item).crossFade().diskCacheStrategy(DiskCacheStrategy.NONE).into((ImageView) helper.getView(R.id.head));
       // Glide.with(mContext).load(R.mipmap.new_product).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.biaoqian));
    }
}
