package cn.com.cashninetrees.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.cashninetrees.R;
import cn.com.cashninetrees.model.CreditBean;

/**
 * Created by apple on 2018/5/10.
 */

public class CreditAdapter extends BaseQuickAdapter<CreditBean,BaseViewHolder> {
    public CreditAdapter( List<CreditBean> data) {
        super(R.layout.vip_credit_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CreditBean item) {
        helper.setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_desc,item.getDesc());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mContext).load(item.getLogo())
                .apply(options).into((ImageView) helper.getView(R.id.credit_logo));

    }
}
