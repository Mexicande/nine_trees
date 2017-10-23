package cn.com.stableloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.clsaa_special.Class_Special;

/**
 * Created by apple on 2017/9/28.
 */

public class Special_FootAdapter extends BaseQuickAdapter<Class_Special.DataBean.MdseBean,BaseViewHolder> {
    public Special_FootAdapter(List<Class_Special.DataBean.MdseBean> data) {
        super(R.layout.foot_class_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Class_Special.DataBean.MdseBean item) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(mContext).load(item.getMdse_ad()).apply(options).into((ImageView) helper.getView(R.id.specila_foot));


    }
}
