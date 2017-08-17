package cn.com.stableloan.bean;

import java.io.Serializable;

import cn.com.stableloan.model.UserInfromBean;

/**
 * Created by apple on 2017/8/11.
 */

public class IntregarlEvent implements Serializable{
    public final String offica;
    public final int credit;

    public IntregarlEvent(String offical,int credits) {
        this.offica = offical;
        this.credit = credits;
    }

}

