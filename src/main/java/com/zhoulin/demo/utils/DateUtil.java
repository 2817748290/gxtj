package com.zhoulin.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 15:24 2018/3/7
 */
public class DateUtil {
    public static Date stringToDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date_util = null; //转换为util.date
        try {
            date_util = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date_util);
        return date_util;
//        java.sql.Date date_sql = new java.sql.Date(date_util.getTime());//转换为sql.date
//        System.out.println(date_util);
//        System.out.println(date_sql);
//        String date = sdf.format(date_sql);
//        System.out.println(date);
//        date = sdf.format(date_util);
//        System.out.println(date);
    }
    public static String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = sdf.format(date);
        return result;
    }


}
