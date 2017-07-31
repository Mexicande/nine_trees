package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/3.
 */

public class MsgEvent implements Serializable{
    public final String msg;

    public MsgEvent(String message) {
        this.msg = message;
    }

}
