package com.gj.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 郭嘉
 * @date 2022/9/20 - 16:53
 * 对data类型处理
 */
public class DataUtils {

    public static String formateDataTime(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
    public static String formateData(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
    public static String formateTime(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
