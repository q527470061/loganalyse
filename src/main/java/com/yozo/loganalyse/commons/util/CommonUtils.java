package com.yozo.loganalyse.commons.util;

import java.text.DateFormat;
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
}
