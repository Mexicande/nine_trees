package cn.com.cashninetrees.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Random;

import cn.com.cashninetrees.R;
import cn.com.cashninetrees.bean.cash.CashActivityBean;
import cn.com.cashninetrees.model.integarl.CashBean;

/**
 * Created by apple on 2017/11/2.
 */

public class CashGetAdapter extends BaseQuickAdapter<CashActivityBean.DataBean.ActivityBean,BaseViewHolder> {

    public CashGetAdapter( List<CashActivityBean.DataBean.ActivityBean> data) {
        super(R.layout.getcash_item, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, CashActivityBean.DataBean.ActivityBean item) {

        int [] bg=new int[] {R.mipmap.iv_activity_top,R.mipmap.invite_cash,R.mipmap.integar_cash};
        int i = new Random().nextInt(2) ;
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        TextView tv=helper.getView(R.id.tv_ActivityTop);
        tv.setBackgroundResource(bg[i]);
        tv.setText(item.getTitle());

        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView)helper.getView(R.id.iv_integar_bg));
        helper.setText(R.id.desc,item.getDesc());

    }
}
