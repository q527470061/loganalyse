package com.yozo.loganalyse.service;



import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class test {

    //日志正则表达式模板
    @Value("logParttern")
    public String logPattern="(?<time>^\\d{4}-[a-zA-Z]{3,4}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{0,3})\\s(?<loglevel>[A-Z]{2,8})\\s\\s\\[(?<threadNo>[\\w\\d-]+)\\]\\s(?<class>(?:[a-zA-Z$_][a-zA-Z$_0-9]*\\.)*[a-zA-Z$_][a-zA-Z$_0-9]*)\\s-\\s(?<sessionId>[A-Z\\d]{32}),\\s(?<requestMethod>\\b[a-zA-Z]+\\b),\\suserId=(?<userId>\\d+),\\sapp=(?<app>[a-z|A-Z]+),\\sip=(?<ip>(?<![0-9])(?:(?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])[.](?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])[.](?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])[.](?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5]))(?![0-9])),\\s(?<stateCode>\\d+),\\sHost=(?<host>\\b(?:[0-9A-Za-z][0-9A-Za-z-]{0,62})(?:\\.(?:[0-9A-Za-z][0-9A-Za-z-]{0,62}))*(\\.?|\\b)),\\sReferer=(?<referer>\\b\\w+\\b),\\sUser-Agent=(?<userAgent>[^,\\s\\n\\t]+)\\s(?<environment>[^,\\s\\n\\t]+),\\suri=(?<uri>(?:/[A-Za-z0-9$.+!*'(){},~:;=@#%&_\\-]*)+)";

    @Test
    public void test(){
        String logRow="2019-May-23 08:37:05.398 INFO  [http-nio-8090-exec-14] c.y.a.c.UaaRequestLoggingFilter - D3D8C3BAAA443F535ED98E119813D0E1, GET, userId=95780, app=null, ip=119.187.150.179, 200, Host=auth.yozocloud.cn, Referer=null, User-Agent=Apache-HttpClient/4.4.1 (Java/1.8.0_172), uri=/api/account/avatar?userId=95780\n";
        checkLog(logPattern,logRow);
    }

    /**
     * 一条日志分析
     */
    public  void checkLog(String pattern,String logRow){
        log.info("日志分析");
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(logRow);
       if(m.find()){
           System.out.printf("time:\t%s\n",m.group("time"));
           System.out.printf("loglevel:\t%s\n",m.group("loglevel"));
           System.out.printf("threadNo:\t%s\n",m.group("threadNo"));
           System.out.printf("class:\t%s\n",m.group("class"));
           System.out.printf("sessionId:\t%s\n",m.group("sessionId"));
           System.out.printf("request_method:\t%s\n",m.group("requestMethod"));
           System.out.printf("userId:\t%s\n",m.group("userId"));

           System.out.printf("app:\t%s\n",m.group("app"));
           System.out.printf("ip:\t%s\n",m.group("ip"));
           System.out.printf("stateCode:\t%s\n",m.group("stateCode"));
           System.out.printf("host:\t%s\n",m.group("host"));

           System.out.printf("referer:\t%s\n",m.group("referer"));
           System.out.printf("userAgent:\t%s\n",m.group("userAgent"));
           System.out.printf("environment:\t%s\n",m.group("environment"));
           System.out.printf("uri:\t%s\n",m.group("uri"));
       }

    }
}
