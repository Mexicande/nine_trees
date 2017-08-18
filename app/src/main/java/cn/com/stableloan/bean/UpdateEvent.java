package cn.com.stableloan.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/3.
 */

public class UpdateEvent implements Serializable{
    public final String msg;

    public UpdateEvent(String phone) {
        this.msg = phone;
    }

}
