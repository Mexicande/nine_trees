package cn.com.laifenqicash.bean;

import java.io.Serializable;

import cn.com.laifenqicash.model.CardBean;

/**
 * Created by apple on 2017/8/3.
 */

public class CameraEvent implements Serializable{
    public final CardBean.OutputsBean.OutputValueBean.DataValueBean value;

    public CameraEvent(CardBean.OutputsBean.OutputValueBean.DataValueBean value) {
        this.value = value;
    }
}
