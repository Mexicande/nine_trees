package cn.com.stableloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.Banner_HotBean;
import cn.com.stableloan.model.home.Seckill_Bean;


/**
 * Created by apple on 2017/4/11.
 */

public class Recycler_Adapter extends BaseQuickAdapter<Seckill_Bean.DataBean,BaseViewHolder> {



    public Recycler_Adapter(List<Seckill_Bean.DataBean> data) {
        super(R.layout.seckill_item_two, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, Seckill_Bean.DataBean item) {


        helper.setText(R.id.activity_desc,item.getActivity_desc())
                .setText(R.id.pname,item.getProduct_name())
                .setText(R.id.amount,"最高"+item.getAmount()+"元")
                .setText(R.id.headline,item.getHeadline());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                ;

        Glide.with(mContext).load(item.getProduct_logo()).apply(options).into((ImageView) helper.getView(R.id.product_logo));
    }
}
