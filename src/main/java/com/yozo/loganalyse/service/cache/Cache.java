package com.yozo.loganalyse.service.cache;

/**
 * 暂存临时数据
 *
 *
 */
public interface Cache {

    /**
     * 保存当前读取行数
     * @param key
     * @param line
     * @param second
     * @return
     */
    Boolean saveLine(String key,int line,int second);

    /**
     * 查询上次读取行数
     * @param key
     * @return
     */
    int readLastLine(String key);

    boolean existKey(String key);
}
