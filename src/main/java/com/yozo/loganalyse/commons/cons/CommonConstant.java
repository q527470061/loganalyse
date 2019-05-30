package com.yozo.loganalyse.commons.cons;

public interface CommonConstant {

    // 读取日志文件行数存24小时
    int LOGFileEXPIRETIME=86400000;

    //读取用户在线统计时间戳
    int ONLINEUNITTIME=1800000;

    //redis存取临时日志时间--目前24小时
    int REDIS_LOG_EXPIRE_TIME=86400000;
}
