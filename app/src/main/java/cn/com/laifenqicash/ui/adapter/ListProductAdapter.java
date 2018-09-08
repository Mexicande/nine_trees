package cn.com.laifenqicash.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;

import java.util.List;

import cn.com.laifenqicash.R;
import cn.com.laifenqicash.model.Class_ListProductBean;
import cn.com.laifenqicash.model.home.Hot_New_Product;
import cn.com.laifenqicash.utils.LogUtils;
import cn.com.laifenqicash.view.GlideRoundTransform;
import cn.com.laifenqicash.view.countdownview.CountdownView;


/**
 * Created by apple on 2017/5/22.
 */

public class ListProductAdapter  extends BaseQuickAdapter<Hot_New_Product.DataBean,BaseViewHolder>{

    public ListProductAdapter(List<Hot_New_Product.DataBean> data) {
        super(R.layout.news_product_trem, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Hot_New_Product.DataBean item) {

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(mContext,5))
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(mContext).load(item.getProduct_logo())
                    .apply(options).into((ImageView) helper.getView(R.id.ic_product_logo));

        View news = helper.getView(R.id.iv_news);
        View hots = helper.getView(R.id.iv_hots);
        if(item.getActivity()==1){
                hots.setVisibility(View.VISIBLE);
            }else {
            hots.setVisibility(View.GONE);
        }
        if(item.getOnline()==1){
            news.setVisibility(View.VISIBLE);
            }else {
            news.setVisibility(View.GONE);
        }


        int interestAlgorithm = item.getInterest_algorithm();
        String maximumAmount = item.getMaximum_amount();
        if(maximumAmount.length()>4){
            String substring = maximumAmount.substring(0, maximumAmount.length() - 4);
            helper.setText(R.id.min_max_Special,item.getMinimum_amount()+"-"+substring+"万");
        }else {
            helper.setText(R.id.min_max_Special,item.getMinimum_amount()+"-"+maximumAmount);
        }
        if(interestAlgorithm==0){
            helper.setText(R.id.rate,"日利率: "+item.getMin_algorithm()+"%");
        }else {
            helper.setText(R.id.rate,"月利率: "+item.getMin_algorithm()+"%");

        }

        helper.setText(R.id.product_list_desc,item.getProduct_introduction())
                    .setText(R.id.tv_Pname,item.getPname())
                    .setText(R.id.tv_speed,"放款速度: "+item.getFastest_time());

    }

}
