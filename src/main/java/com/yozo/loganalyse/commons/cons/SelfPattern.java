package com.yozo.loganalyse.commons.cons;

public interface SelfPattern {

    String  LOGPATTERN_SSO_OPERATE="(?<time>^\\d{4}-[a-zA-Z]{3,4}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{0,3})\\s(?<loglevel>[A-Z]{2,8})\\s\\s\\[(?<threadNo>[\\w\\d-]+)\\]\\s(?<class>(?:[a-zA-Z$_][a-zA-Z$_0-9]*\\.)*[a-zA-Z$_][a-zA-Z$_0-9]*)\\s-\\s(?<sessionId>[A-Z\\d]{32}),\\s(?<requestMethod>\\b[a-zA-Z]+\\b),\\suserId=(?<userId>\\d+),\\sapp=(?<app>[a-z|A-Z]+),\\sip=(?<ip>(?<![0-9])(?:(?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])[.](?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])[.](?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])[.](?:[0-1]?[0-9]{1,2}|2[0-4][0-9]|25[0-5]))(?![0-9])),\\s(?<stateCode>\\d+),\\sHost=(?<host>\\b(?:[0-9A-Za-z][0-9A-Za-z-]{0,62})(?:\\.(?:[0-9A-Za-z][0-9A-Za-z-]{0,62}))*(\\.?|\\b)),\\sReferer=(?<referer>\\b\\w+\\b),\\sUser-Agent=(?<userAgent>[^,\\s\\n\\t]+)\\s(?<environment>[^,\\s\\n\\t]+),\\suri=(?<uri>(?:/[A-Za-z0-9$.+!*'(){},~:;=@#%&_\\-]*)+)";

}
