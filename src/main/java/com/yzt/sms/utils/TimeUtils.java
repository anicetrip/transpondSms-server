package com.yzt.sms.utils;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间处理
 *
 * @author yin.ZT
 * @date 2021/07/20 16:18
 **/
public class TimeUtils {

    public static Date timeStampToDate(Long timeStamp){
        SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        try{
            String d = format.format(timeStamp);
            return format.parse(d);
        } catch (ParseException e) {
            return null;
        }
    }

}
