package cn.com.laifenqicash.view.countdownview;

import android.content.Context;
import android.view.View;

/**
 * Created by suining on 2016/6/23 0023.
 */
public  class CountDownUtil implements CountDownInterface{
    //整体时间戳
    private Long time;
    //假的毫秒时间戳
    private Long millisecond;
    //假的毫秒显示view
    private View millisecondView;
    //整体时间戳view
    private View timeView;
    private Context context;
    private View millLowView;
    private  CoutDownMillisSecond coutDownMillisSecond;
    private   CoutDownMillisSecondSlow coutDownMillisSecondSlow;
   private CallTimeFinshBack timeFinshBack;
    public CountDownUtil(Context context, Long time, Long millisecond, View timeView, View millLowView , View millisecondView) {
        this.context=context;
        this.time = time;
        if(millisecond==null){
            this.millisecond=99L;
        }else{
            this.millisecond = millisecond;
        }
        this.timeView=timeView;
        this.millisecondView=millisecondView;
        this.millLowView=millLowView;
    }




    //设置毫秒倒计时
    @Override
    public void setMillisecond() {
        coutDownMillisSecond =new CoutDownMillisSecond(99,1,millisecondView);
        coutDownMillisSecond.start();
        coutDownMillisSecondSlow  =new CoutDownMillisSecondSlow(1000,100,millLowView);
        coutDownMillisSecondSlow.start();
    }



    public void finshTime(CallTimeFinshBack timeFinshBack){
        this.timeFinshBack=timeFinshBack;
    }



    public interface CallTimeFinshBack{
        void finshTime();
    }

    public void cancelTime(){
        coutDownMillisSecond.cancel();
        coutDownMillisSecondSlow.cancel();
    }

    public void cancelMillisecondTime(){
        coutDownMillisSecond.cancel();
        coutDownMillisSecondSlow.cancel();
    }

}
