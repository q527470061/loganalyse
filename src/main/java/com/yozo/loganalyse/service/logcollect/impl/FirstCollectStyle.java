package com.yozo.loganalyse.service.logcollect.impl;

import com.yozo.loganalyse.commons.cons.CommonConstant;
import com.yozo.loganalyse.commons.cons.SelfPattern;
import com.yozo.loganalyse.commons.util.CommonUtils;
import com.yozo.loganalyse.pojo.OperateRecord;
import com.yozo.loganalyse.service.cache.Cache;
import com.yozo.loganalyse.service.logcollect.LogCollect;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class FirstCollectStyle implements LogCollect {

    //日志正则表达式模板
    private String logPattern=SelfPattern.LOGPATTERN_SSO_OPERATE;
    @Autowired
    private Cache cache;

    @Override
    public void checkLog(String pattern, String logRow,List<OperateRecord> records) throws ParseException {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(logRow);
        if(m.find()){
            OperateRecord operateRecord=new OperateRecord();
            operateRecord.setUserId(m.group("userId"));
            operateRecord.setApp(m.group("app"));
            operateRecord.setIp(m.group("ip"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss.SSS", Locale.US);
            Date date=sdf.parse(m.group("time"));
            operateRecord.setLastAccessTime(date);

            records.add(operateRecord);
        }
    }

    @Override
    public void getCertainLineOfTxt(String filePath, int lineNumber,List<OperateRecord> records) {
        String txt = "";
        try (LineNumberReader reader=new LineNumberReader(new FileReader(new File(filePath)))) {
            int lines = 0;
            while (txt != null) {
                lines++;
                txt = reader.readLine();
                if (lines > lineNumber&&txt!=null) {
                    checkLog(logPattern,txt,records);
                }
            }
            // 记录读取行数
            int currentLine=reader.getLineNumber();
            String key=CommonUtils.generateKey(filePath);
            log.info("--------------------------读取文件：{}-------当前行数：{}-------------------------------",filePath,currentLine);
            cache.saveLine(key,currentLine,CommonConstant.LOGFileEXPIRETIME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        String str="F:\\log\\auth3.log";
        DateFormat df = DateFormat.getDateInstance();
        String key=df.format(new Date())+"-"+str;
        //File file=new File(str);
        System.out.println(1000*60*30);
    }

}
