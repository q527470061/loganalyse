package com.yozo.loganalyse.service;



import com.yozo.loganalyse.pojo.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class test {

    static List<Record> records=new ArrayList<>();

    //日志正则表达式模板
    @Value("logParttern")
    public static String logPattern="(?<time>^\\d{4}-[a-zA-Z]{3,4}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{0,3})\\s(?<loglevel>[A-Z]{2,8})\\s\\s\\[(?<threadNo>[\\w\\d-]+)\\]\\s(?<class>(?:[a-zA-Z$_][a-zA-Z$_0-9]*\\.)*[a-zA-Z$_][a-zA-Z$_0-9]*)\\s-\\s(?<sessionId>[A-Z\\d]{32}),\\s(?<requestMethod>\\b[a-zA-Z]+\\b),\\suserId=(?<userId>\\d+),\\sapp=(?<app>[a-z|A-Z]+),\\sip=(?<ip>(?<![0-9])(?:(?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])[.](?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])[.](?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])[.](?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5]))(?![0-9])),\\s(?<stateCode>\\d+),\\sHost=(?<host>\\b(?:[0-9A-Za-z][0-9A-Za-z-]{0,62})(?:\\.(?:[0-9A-Za-z][0-9A-Za-z-]{0,62}))*(\\.?|\\b)),\\sReferer=(?<referer>\\b\\w+\\b),\\sUser-Agent=(?<userAgent>[^,\\s\\n\\t]+)\\s(?<environment>[^,\\s\\n\\t]+),\\suri=(?<uri>(?:/[A-Za-z0-9$.+!*'(){},~:;=@#%&_\\-]*)+)";

    public static void main(String[] args) {
        getCertainLineOfTxt("F:\\日志分析\\auth3.log" , 0);
        log.error(records+"");
        log.info(records.size()+"");
        getCertainLineOfTxt("F:\\日志分析\\auth4.log" , 0);
        log.error(records+"");
        log.info(records.size()+"");
    }

    /**
     * 一条日志分析
     */
    public static void checkLog(String pattern,String logRow) throws ParseException {
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

    public static void getCertainLineOfTxt(String filePath, int lineNumber) {
        FileReader fr = null;
        LineNumberReader reader = null;
        String txt = "";

        try {
            File file = new File(filePath);
            fr = new FileReader(file);
            reader = new LineNumberReader(fr);

            int lines = 0;

            while (txt != null) {
                lines++;

                txt = reader.readLine();

                if (lines > lineNumber&&txt!=null) {

                    checkLog(logPattern,txt);
                }
            }
            log.info("当前行数："+reader.getLineNumber());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
