package cn.com.cashninetrees.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.cashninetrees.R;
import cn.com.cashninetrees.model.NoticeBean;

/**
 * Created by apple on 2017/6/9.
 */

public class NoticeAdapter extends BaseQuickAdapter<NoticeBean.AnnouncementsBean,BaseViewHolder> {



    public NoticeAdapter(List<NoticeBean.AnnouncementsBean> data) {
        super(R.layout.notice_item, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeBean.AnnouncementsBean item) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        helper.setText(R.id.tv_name,item.getName())
                .setText(R.id.last_time,item.getLast_time())
                .setText(R.id.tv_content,item.getContent());
        Glide.with(mContext).load(item.getImage()).apply(options)
                .into((ImageView) helper.getView(R.id.iv_image));

        // Glide.with(mContext).load(R.mipmap.new_product).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.biaoqian));
    }
}