package cn.com.cashninetrees.model.event;


/**
 * Created by apple on 2017/8/23.
 */

/**
 * 0.open  slide
 * 1.student
 * 2.company
 * 3.business
 * 4.free
 */
public class ProfessionalSelectEvent {

    public final int message;

    public ProfessionalSelectEvent(int message) {
        this.message = message;
    }
}
