package cn.com.stableloan.bean;

import java.io.Serializable;

import cn.com.stableloan.model.UserInfromBean;

/**
 * Created by apple on 2017/7/3.
 */

public class DescEvent implements Serializable{
    public final String collection;

    public DescEvent(String collection) {
        this.collection = collection;
    }

}
