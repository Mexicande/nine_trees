package cn.com.stableloan.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/3.
 */

public class ProcuctCollectionEvent implements Serializable {
    public final String msg;

    public ProcuctCollectionEvent(String message) {
        this.msg = message;
    }

}
