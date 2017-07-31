package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/3.
 */

public class PicStatusEvent implements Serializable{

    public final String status;


    public PicStatusEvent(String status) {
        this.status = status;
    }

}
