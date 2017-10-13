package cn.com.stableloan.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.Device;
import cn.com.stableloan.model.Product_DescBean;
import cn.com.stableloan.ui.activity.UserInformationActivity;
import cn.com.stableloan.view.supertextview.SuperTextView;

/**
 * Created by apple on 2017/10/13.
 */

public class DeviceAdapter extends BaseQuickAdapter<Device.DataBean._$0Bean,BaseViewHolder> {
    private  boolean flag;
    public DeviceAdapter( List<Device.DataBean._$0Bean> data) {
        super(R.layout.super_textview_item);
    }

    public  void  setVisiable(boolean open){
        flag=open;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, Device.DataBean._$0Bean item) {
        SuperTextView  view = helper.getView(R.id.sv_Device);
        view.setLeftTopString(item.getDevice());
        view.setLeftBottomString(item.getVersion_number());
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.button_succeed);
        view.setTextBackground(drawable);
        view.setRightString("删除");
        if(flag){
            view.getRightTextView().setVisibility(View.VISIBLE);
        }else {
            view.getRightTextView().setVisibility(View.GONE);
        }
    }
}
