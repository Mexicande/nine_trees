package cn.com.stableloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.bean.Product;
import cn.com.stableloan.model.Banner_HotBean;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by apple on 2017/4/11.
 */

public class Recycler_Adapter extends BaseQuickAdapter<Banner_HotBean.RecommendsBean,BaseViewHolder> {



    public Recycler_Adapter(List<Banner_HotBean.RecommendsBean> data) {
        super(R.layout.recycler_item, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, Banner_HotBean.RecommendsBean item) {


        Glide.with(mContext).load(item.getPictrue()).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.head));

       // Glide.with(mContext).load(R.mipmap.new_product).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.biaoqian));
    }
}
