package cn.com.stableloan.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.integarl.CashBean;

/**
 * Created by apple on 2018/5/9.
 */

public class PayVipAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public PayVipAdapter( List<String> data) {
        super(R.layout.vip_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
