package cn.com.laifenqicash.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/11.
 */

public class IntregarlEvent implements Serializable{
    public final String offica;
    public final int credit;
    public final int topCredits;


    public IntregarlEvent(String offical,int credits,int topCredits) {
        this.offica = offical;
        this.credit = credits;
        this.topCredits=topCredits;
    }

}

