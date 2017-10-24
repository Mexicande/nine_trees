package cn.com.stableloan.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/3.
 */

public class CashEvent implements Serializable{
    public final int cash;

    public CashEvent(int cash) {
        this.cash = cash;
    }

}
