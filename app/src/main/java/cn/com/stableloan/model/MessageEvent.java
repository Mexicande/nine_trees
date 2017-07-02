package cn.com.stableloan.model;

/**
 * Created by apple on 2017/7/3.
 */

public class MessageEvent {
    public final String userNick;

    public MessageEvent(String message) {
        this.userNick = message;
    }
}
