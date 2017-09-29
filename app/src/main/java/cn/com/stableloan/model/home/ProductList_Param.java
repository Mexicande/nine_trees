package cn.com.stableloan.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/9/29.
 */

public class ProductList_Param implements Serializable {

    /**
     * identity : ["1"]
     * labels : ["1"]
     * amount : 500
     */

    private int amount;
    private List<String> identity;
    private List<String> labels;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<String> getIdentity() {
        return identity;
    }

    public void setIdentity(List<String> identity) {
        this.identity = identity;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
