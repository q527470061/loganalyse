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
     * 由日期组成的key,格式如下
     * 2019-5-31-F:\log\auth4.log
     *
     * 成因：
     *  1. 每日解析日志，路径和文件名相同即相同文件
     *  2. 每日日期不同
     *  3. 由于隔夜，key 本地日期-文件名生成策略，会出现错误解析
     *
     *  解决：
     *   取出日志第一条记录，解析出日志当前日期
     * @param path
     * @return
     */
    public static String generateKey(String path){
        return DateFormat.getDateInstance().format(new Date())+"-"+path;
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
        stringBuilder
                .append(dateStr)
                .append("-")
                .append(operateRecord.getUserId())
                .append("-")
                .append(operateRecord.getApp())
                .append("-")
                .append(operateRecord.getIp());

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
