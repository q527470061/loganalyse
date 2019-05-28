package com.yozo.loganalyse.service.logcollect.impl;

import com.yozo.loganalyse.commons.cons.SelfPattern;
import com.yozo.loganalyse.pojo.Record;
import com.yozo.loganalyse.service.logcollect.LogCollect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class FirstCollectStyle implements LogCollect {

    //日志正则表达式模板
    private String logPattern=SelfPattern.LOGPATTERN_SSO_OPERATE;

    @Override
    public void checkLog(String pattern, String logRow,List records) throws ParseException {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(logRow);
        if(m.find()){
            Record record=new Record();
            record.setApp(m.group("app"));
            record.setIp(m.group("ip"));
            record.setUserId(m.group("userId"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss.SSS", Locale.US);
            record.setTime(sdf.parse(m.group("time")));
            records.add(record);
        }
    }

    @Override
    public void getCertainLineOfTxt(String filePath, int lineNumber,List records) {
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
            log.info("当前行数："+reader.getLineNumber());
            // 记录当前读取行数

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}