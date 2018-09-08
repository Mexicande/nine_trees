package cn.com.laifenqicash.utils;

/**
 * Created by apple on 2017/10/13.
 */

public class WaitTimeUtils {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}

