package cn.com.stableloan.ui.adapter;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.integarl.IntegarlBean;

/**
 * Created by apple on 2017/8/11.
 */

public class Integarl_taskAdapter extends BaseQuickAdapter<IntegarlBean.DataBean.CodeBean,BaseViewHolder> {

    public Integarl_taskAdapter(List<IntegarlBean.DataBean.CodeBean> data) {
        super(R.layout.integarl_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegarlBean.DataBean.CodeBean item) {
        helper.setText(R.id.title,item.getTitle())
                .setText(R.id.desc,item.getDesc())
                .setText(R.id.code,item.getCode());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                ;

        //helper.setText(R.id.status,item.getStatus());
        SuperTextView view = (SuperTextView) helper.getView(R.id.st_TextView);
        view.setText(item.getStatus());
        view.setSolid(Color.parseColor("#"+item.getBg()));
        Glide.with(mContext).load(item.getImg())
                .apply(options).into((ImageView) helper.getView(R.id.img));

    }
}
