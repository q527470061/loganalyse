package com.yozo.loganalyse.commons.util;

import com.yozo.loganalyse.pojo.OperateRecord;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 一些键值对的生成等
 */
public class CommonUtils {

    /**
     * 由日期组成的key
     * @param dateKey
     * @return
     */
    public static String generateKey(String dateKey){
        return DateFormat.getDateInstance().format(new Date())+"-"+dateKey;
    }

    /**
     * 生成operateRecord的专有key
     * key：date-userId-app-ip
     * @return
     */
    public static String generateKey(OperateRecord operateRecord){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String dateStr=sdf.format(operateRecord.getLastAccessTime());
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(dateStr).append("-").append(operateRecord.getUserId()).append("-").append(operateRecord.getApp()).append("-").append(operateRecord.getIp());
        return stringBuilder.toString();
    }



    public void  test(){
        OperateRecord operateRecord=new OperateRecord();
        operateRecord.setUserId("w234ewe");
        operateRecord.setLastAccessTime(new Date());
        operateRecord.setIp("12.123.211.23");
        operateRecord.setApp("yocloud");
        System.out.println(generateKey(operateRecord));
        System.out.println((String) null);
    }
}
