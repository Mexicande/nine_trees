package cn.com.stableloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.bean.cash.CashActivityBean;
import cn.com.stableloan.model.integarl.CashBean;

/**
 * Created by apple on 2017/11/2.
 */

public class CashGetAdapter extends BaseQuickAdapter<CashActivityBean.DataBean.ActivityBean,BaseViewHolder> {

    public CashGetAdapter( List<CashActivityBean.DataBean.ActivityBean> data) {
        super(R.layout.getcash_item, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, CashActivityBean.DataBean.ActivityBean item) {

        RequestOptions options = new RequestOptions()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView)helper.getView(R.id.iv_integar_bg));
        helper.setText(R.id.desc,item.getDesc());

    }
}
