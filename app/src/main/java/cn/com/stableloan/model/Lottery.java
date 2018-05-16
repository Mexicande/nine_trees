package cn.com.stableloan.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author apple
 * @date 2018/5/10
 * 双色球
 */

public class Lottery implements Serializable{

    /**
     * lottery : [{"bonuscode":"02,05,16,19,30,31,13","phase":"18042770"},{"bonuscode":"02,05,16,19,30,31,13","phase":"18042770"},{"bonuscode":"02,05,16,19,30,31,13","phase":"18042770"},{"bonuscode":"02,05,16,19,30,31,13","phase":"18042770"},{"bonuscode":"02,05,16,19,30,31,13","phase":"18042770"}]
     * link : http://test.m.anwenqianbao.com/#/home
     * status : 1
     */

    private String link;
    private int status;
    private List<LotteryBean> lottery;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<LotteryBean> getLottery() {
        return lottery;
    }

    public void setLottery(List<LotteryBean> lottery) {
        this.lottery = lottery;
    }

    public static class LotteryBean implements Serializable{
        /**
         * bonuscode : 02,05,16,19,30,31,13
         * phase : 18042770
         */

        private String bonuscode;
        private String phase;

        public String getBonuscode() {
            return bonuscode;
        }

        public void setBonuscode(String bonuscode) {
            this.bonuscode = bonuscode;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }
    }
}
