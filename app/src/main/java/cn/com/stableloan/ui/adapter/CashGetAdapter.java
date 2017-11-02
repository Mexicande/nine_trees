package cn.com.stableloan.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.integarl.CashBean;

/**
 * Created by apple on 2017/11/2.
 */

public class CashGetAdapter extends BaseQuickAdapter<CashBean.DataBean.CashRecordBean,BaseViewHolder> {

    public CashGetAdapter( List<CashBean.DataBean.CashRecordBean> data) {
        super(R.layout.getcash_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CashBean.DataBean.CashRecordBean item) {

    }
}
