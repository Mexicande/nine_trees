package cn.com.stableloan.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/1.
 */

public class IdentitySave implements Serializable {

    public final boolean mean;
    public final boolean bank;
    public final boolean profession;



    public IdentitySave(boolean mean, boolean bank,boolean profession) {
        this.mean = mean;
        this.bank = bank;
        this.profession = profession;
    }

}
