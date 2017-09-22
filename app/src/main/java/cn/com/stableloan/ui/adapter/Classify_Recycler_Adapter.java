package cn.com.stableloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.News_ClassBean;


/**
 * Created by apple on 2017/4/11.
 */

public class Classify_Recycler_Adapter extends BaseQuickAdapter<News_ClassBean.ClassBean,BaseViewHolder> {



    public Classify_Recycler_Adapter(List<News_ClassBean.ClassBean> data) {
        super(R.layout.classify_recycler_item, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, News_ClassBean.ClassBean item) {


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                ;


        // helper.setImageResource(R.id.head,R.mipmap.classify_04);
        Glide.with(mContext).load(item.getHome_image()).apply(options).into((ImageView)helper.getView(R.id.head));
       // Glide.with(mContext).load(R.mipmap.new_product).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.biaoqian));
    }
}
