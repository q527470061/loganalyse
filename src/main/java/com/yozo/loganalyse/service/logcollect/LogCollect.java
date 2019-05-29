package com.yozo.loganalyse.service.logcollect;

import com.yozo.loganalyse.pojo.OperateRecord;

import java.text.ParseException;
import java.util.List;

public interface LogCollect {

    /**
     * 正则匹配日志记录（1条）
     * @param pattern
     * @param logRow
     */
    void checkLog(String pattern,String logRow,List<OperateRecord> records) throws ParseException;

    /**
     * 按照指定行数收集日志
     * @param filePath
     * @param lineNumber
     */
    void getCertainLineOfTxt(String filePath, int lineNumber,List<OperateRecord> records);
}
