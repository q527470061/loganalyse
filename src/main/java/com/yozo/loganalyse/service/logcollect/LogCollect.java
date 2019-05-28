package com.yozo.loganalyse.service.logcollect;

import java.text.ParseException;
import java.util.List;

public interface LogCollect {

    /**
     * 正则匹配日志记录（1条）
     * @param pattern
     * @param logRow
     */
    void checkLog(String pattern,String logRow,List records) throws ParseException;

    /**
     * 按照指定行数收集日志
     * @param filePath
     * @param lineNumber
     */
    void getCertainLineOfTxt(String filePath, int lineNumber,List records);
}
