package cn.com.laifenqicash.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.laifenqicash.R;
import cn.com.laifenqicash.model.integarl.InviteFriendList;

/**
 * Created by apple on 2017/8/25.
 * 邀请列表
 */

public class InviteListAdapter extends BaseQuickAdapter<InviteFriendList.DataBean.InviteLogBean,BaseViewHolder> {
    public InviteListAdapter(List<InviteFriendList.DataBean.InviteLogBean> data) {
        super(R.layout.invite_contacts_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InviteFriendList.DataBean.InviteLogBean item) {
        helper.setText(R.id.tv_InvitePhone,item.getInvitePhone())
                .setText(R.id.tv_Status,item.getStatus())
                .setText(R.id.tv_Money,item.getMoney());

    }
}
