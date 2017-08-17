package cn.com.stableloan.ui.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
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
import cn.com.stableloan.model.integarl.CashBean;

/**
 * Created by apple on 2017/6/9.
 */

public class CashAdapter extends BaseQuickAdapter<CashBean.DataBean.CashRecordBean,BaseViewHolder> {



    public CashAdapter(List<CashBean.DataBean.CashRecordBean> data) {
        super(R.layout.cash_item, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, CashBean.DataBean.CashRecordBean item) {

        helper.setText(R.id.type,item.getType())
                .setText(R.id.create_at,item.getCreate_at());
        TextView view = helper.getView(R.id.number);
            view.setText(item.getNumber());
        view.setTextColor(Color.parseColor(item.getFont()));

        // Glide.with(mContext).load(R.mipmap.new_product).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.biaoqian));
    }
}