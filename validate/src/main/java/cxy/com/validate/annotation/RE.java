package cxy.com.validate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by CXY on 2016/11/7.
 */
@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.FIELD)
public @interface RE {
    String only_Chinese="^[\\u4e00-\\u9fa5]{0,}$";
    String only_number="^[0-9]*$";
    String number_letter_underline="^[\\w]{6,16}$";
    String number_letter_nick="^[\\w]{3,16}$";
    String email="\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

    String phone="^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$";

    String re()  ;
    String msg()  ;
}
