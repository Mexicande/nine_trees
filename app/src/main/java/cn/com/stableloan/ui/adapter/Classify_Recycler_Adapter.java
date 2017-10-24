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
 * Created by apple on 2017/4/11.
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
                ;
        helper.setText(R.id.project_name, item.getProject_name())
                .setText(R.id.title, item.getTitle())
                .setText(R.id.preface,item.getPreface())
                .setText(R.id.total_amount,"共"+item.getProduct_amount()+"款产品");




      /*  TextView view = helper.getView(R.id.product_amount);
       view.setText("总额度"+item.getProduct_amount()+"元");
        String s = view.getText().toString();
        SpannableString spanString = new SpannableString(s);

        ForegroundColorSpan span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimary));
        spanString.setSpan(span, 3, s.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new AbsoluteSizeSpan(14, true), 3, s.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(spanString);*/


        Glide.with(mContext).load(item.getProject_logo()).apply(options).into((ImageView)helper.getView(R.id.project_logo));

       // Glide.with(mContext).load(R.mipmap.new_product).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.biaoqian));
    }
}
