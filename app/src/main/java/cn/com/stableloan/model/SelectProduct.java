package cn.com.stableloan.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by apple on 2017/6/13.
 */

public class SelectProduct implements Serializable{

    private int var[] ;
    private String status;

    public int[] getVar() {
        return var;
    }

    public void setVar(int[] var) {
        this.var = var;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SelectProduct() {
    }

    public SelectProduct(int[] var, String status) {
        this.var = var;
        this.status = status;
    }

    @Override
    public String toString() {
        return "SelectProduct{" +
                "var=" + Arrays.toString(var) +
                ", status='" + status + '\'' +
                '}';
    }
}
