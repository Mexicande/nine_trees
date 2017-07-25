package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/20.
 */

public class DesBean implements Serializable{

    private String data;
    private String Deskey;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDeskey() {
        return Deskey;
    }

    public void setDeskey(String deskey) {
        Deskey = deskey;
    }



    @Override
    public String toString() {
        return "DesBean{" +
                "data='" + data + '\'' +
                ", Deskey='" + Deskey + '\'' +
                '}';
    }
}
