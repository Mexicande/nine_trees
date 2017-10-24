package cn.com.stableloan.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/3.
 */

public class UpdateProfessionEvent implements Serializable{
    public final int updateProfession;

    public UpdateProfessionEvent(int updateProfession) {
        this.updateProfession = updateProfession;
    }

}
