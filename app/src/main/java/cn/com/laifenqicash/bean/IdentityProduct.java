package cn.com.laifenqicash.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/1.
 */

public class IdentityProduct implements Serializable {

    public final int msg;
    public final int amount;



    public IdentityProduct(int message,int amount) {
        this.msg = message;
        this.amount = amount;
    }

}
