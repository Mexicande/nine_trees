package cn.com.stableloan.bean;

import java.io.Serializable;

import cn.com.stableloan.model.UserInfromBean;

/**
 * Created by apple on 2017/7/3.
 */

public class UserEvent implements Serializable{
    public final UserInfromBean userNick;

    public UserEvent(UserInfromBean message) {
        this.userNick = message;
    }

}
