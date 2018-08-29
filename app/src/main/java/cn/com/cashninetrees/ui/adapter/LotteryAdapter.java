package cn.com.cashninetrees.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Arrays;
import java.util.List;

import cn.com.cashninetrees.R;
import cn.com.cashninetrees.model.Lottery;

/**
 * Created by apple on 2018/5/10.
 */

public class LotteryAdapter extends BaseQuickAdapter<Lottery.LotteryBean,BaseViewHolder> {
    public LotteryAdapter( List<Lottery.LotteryBean> data) {
        super(R.layout.lottery_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Lottery.LotteryBean item) {
        String bonuscode = item.getBonuscode();
        String[] split = bonuscode.split(",");
        List<String> list = Arrays.asList(split);
        helper.setText(R.id.tv_date,item.getPhase()+"期")
                .setText(R.id.tv_01,list.get(0))
                .setText(R.id.tv_02,list.get(1))
                .setText(R.id.tv_03,list.get(2))
                .setText(R.id.tv_04,list.get(3))
                .setText(R.id.tv_05,list.get(4))
                .setText(R.id.tv_06,list.get(5))
                .setText(R.id.tv_07,list.get(6));

    }
}
