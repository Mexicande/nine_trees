package cn.com.stableloan.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/1.
 */

public class IdentityProduct implements Serializable {

    public final int msg;

    public IdentityProduct(int message) {
        this.msg = message;
    }

}
