package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/3.
 */

public class MessageEvent implements Serializable{
    public final String userNick;
    public final String phone;

    public MessageEvent(String nick,String phone) {
        this.userNick = nick;
        this.phone = phone;
    }

}
