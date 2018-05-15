package cn.com.stableloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.VipBean;
import cn.com.stableloan.model.integarl.CashBean;

/**
 *
 * @author apple
 * @date 2018/5/9
 * vip中心
 */

public class PayVipAdapter extends BaseQuickAdapter<VipBean.ServiceBean,BaseViewHolder> {

    public PayVipAdapter( List<VipBean.ServiceBean> data) {
        super(R.layout.vip_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipBean.ServiceBean item) {
        helper.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_desc,item.getDesc());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mContext).load(item.getLogo())
                .apply(options).into((ImageView) helper.getView(R.id.iv_logo));

    }
}
