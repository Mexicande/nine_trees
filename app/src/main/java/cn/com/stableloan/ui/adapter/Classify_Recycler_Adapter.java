package cn.com.stableloan.ui.adapter;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.home.SpecialClassBean;


/**
 *
 * @author apple
 * @date 2017/4/11
 * 分类
 */

public class Classify_Recycler_Adapter extends BaseQuickAdapter<SpecialClassBean.DataBean,BaseViewHolder> {



    public Classify_Recycler_Adapter(List<SpecialClassBean.DataBean> data) {
        super(R.layout.classify_recycler_item, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, SpecialClassBean.DataBean item) {


        RequestOptions options = new RequestOptions()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        helper.setText(R.id.project_name, item.getProject_name());
        Glide.with(mContext).load(item.getProject_logo()).apply(options).into((ImageView)helper.getView(R.id.project_logo));

    }
}
