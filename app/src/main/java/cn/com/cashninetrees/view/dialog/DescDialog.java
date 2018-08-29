package cn.com.cashninetrees.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import cn.com.cashninetrees.R;
import cn.com.cashninetrees.utils.SPUtil;
import cn.com.cashninetrees.view.RoundButton;
import cn.com.cashninetrees.view.SmoothCheckBox;

/**
 * Created by apple on 2017/5/25.
 */

public class DescDialog extends Dialog {
    private SmoothCheckBox checkbox;
    private RoundButton btKnow;
    private ImageView pLogo;

    private Button yes;//确定按钮
    private Button no;//取消按钮
    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String yesStr, noStr,imageStr;

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    public DescDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desc_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(true);
        //初始化界面数据
        initView();
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    private void initView() {
        btKnow = (RoundButton) findViewById(R.id.bt_know);
        checkbox = (SmoothCheckBox) findViewById(R.id.checkbox);
        pLogo= (ImageView) findViewById(R.id.p_Logo);
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        btKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    if (checkbox.isChecked()) {
                        SPUtil.putBoolean(getContext(), "dialog", true);
                    }
                    yesOnclickListener.onYesClick();
                }
            }
        });
    }
    private void initData() {
        //如果用户自定了title和message
       /* if (titleStr != null) {
            titleTv.setText(titleStr);
        }
        if (messageStr != null) {
            messageTv.setText(messageStr);
        }*/
        if(imageStr!=null){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    ;
            Glide.with(getContext()).load(imageStr).apply(options).into((pLogo));
        }


    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title,String url) {
        titleStr = title;
        imageStr=url;
      /*  if(url!=null){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    ;
            Glide.with(getContext()).load(url).apply(options).into((pLogo));
        }*/

    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }


    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
}
