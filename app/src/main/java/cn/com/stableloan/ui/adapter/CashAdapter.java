package cn.com.stableloan.ui.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.integarl.CashBean;
import cn.com.stableloan.view.supertextview.SuperTextView;

/**
 * Created by apple on 2017/6/9.
 */

public class CashAdapter extends BaseQuickAdapter<CashBean.DataBean.CashRecordBean,BaseViewHolder> {


    public CashAdapter(List<CashBean.DataBean.CashRecordBean> data) {
        super(R.layout.cash_item, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, CashBean.DataBean.CashRecordBean item) {
       SuperTextView superTextView=helper.getView(R.id.stv_cash);
        superTextView.setLeftString(item.getStatus())
                        .setLeftTextColor(Color.parseColor(item.getFont()))
                        .setRightString(item.getNumber())
                        .setLeftTextColor(Color.parseColor(item.getFont()))
                        .setCenterTopString(item.getRemark())
                        .setCenterBottomString(item.getCreate_at());
/*
        helper.setText(R.id.type,item.getRemark())
                .setText(R.id.create_at,item.getCreate_at())
        .setText(R.id.status,item.getStatus());

        TextView status = helper.getView(R.id.status);
        status.setText(item.getStatus());
        status.setTextColor(Color.parseColor(item.getFont()));

        TextView view = helper.getView(R.id.number);
            view.setText(item.getNumber());
        view.setTextColor(Color.parseColor(item.getFont()));*/

        // Glide.with(mContext).load(R.mipmap.new_product).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) helper.getView(R.id.biaoqian));
    }
}