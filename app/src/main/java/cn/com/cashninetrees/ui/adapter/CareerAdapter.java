package cn.com.cashninetrees.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.cashninetrees.R;

/**
 * Created by apple on 2018/5/8.
 */

public class CareerAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public CareerAdapter(List<String> data) {
        super(R.layout.tv, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.select,item)
                .setBackgroundRes(R.id.select,R.drawable.tag_flow_bg);
    }
}
