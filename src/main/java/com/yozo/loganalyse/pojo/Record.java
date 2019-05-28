package com.yozo.loganalyse.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author qinweiliang
 * @create 2019-05-27 20:15
 **/
@Data
public class Record {

    String userId;

    String app;

    String ip;

    Date time;
    //String time;

}
