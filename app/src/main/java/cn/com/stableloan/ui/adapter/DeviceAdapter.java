package cn.com.stableloan.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.model.Device;
import cn.com.stableloan.view.supertextview.SuperTextView;

/**
 * Created by apple on 2017/10/13.
 */

public class DeviceAdapter extends BaseQuickAdapter<Device.DataBean.ListBean,BaseViewHolder> {
    private  boolean flag;
    public DeviceAdapter( List<Device.DataBean.ListBean> data) {
        super(R.layout.super_textview_item);
    }

    public  void  setVisiable(boolean open){
        flag=open;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, Device.DataBean.ListBean item) {
        helper.setText(R.id.tv_device,item.getDevice())
                .setText(R.id.tv_version_number,item.getVersion_number())
                .addOnClickListener(R.id.bt_delete);
        Button delete = helper.getView(R.id.bt_delete);
        if(flag){
            delete.setVisibility(View.VISIBLE);
        }else {
            delete.setVisibility(View.GONE);
        }
/*

        SuperTextView  view = helper.getView(R.id.sv_Device);
        view.setLeftTopString(item.getDevice());
        view.setLeftBottomString(item.getVersion_number());
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.button_succeed);
        view.setRightString("删除");


        view.setRightTvClickListener(new SuperTextView.OnRightTvClickListener() {
            @Override
            public void onClickListener() {

            }
        });*/
    }
}
