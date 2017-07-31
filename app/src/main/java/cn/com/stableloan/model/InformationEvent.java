package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/3.
 */

public class InformationEvent implements Serializable{

    public final String message;


    public InformationEvent(String message) {
        this.message = message;
    }
}
