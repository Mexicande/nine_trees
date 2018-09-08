package cn.com.laifenqicash.bean;

import java.io.Serializable;

/**
 *
 * @author apple
 * @date 2017/7/3
 * 微信支付回调
 */

public class PayEvent implements Serializable{
    public final int type;

    public PayEvent(int cash) {
        this.type = cash;
    }

}
