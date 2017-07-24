package cn.com.stableloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.WelfareBean;

/**
 * Created by apple on 2017/7/18.
 */

public class WelfareAdapter extends BaseQuickAdapter<WelfareBean.DataBean,BaseViewHolder> {

    public WelfareAdapter(List<WelfareBean.DataBean> data) {
        super(R.layout.welfare_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WelfareBean.DataBean item) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.lottery_default)
                .placeholder(R.mipmap.lottery_default)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(mContext).load(item.getImage()).apply(options).into((ImageView) helper.getView(R.id.welfare));


    }
}
