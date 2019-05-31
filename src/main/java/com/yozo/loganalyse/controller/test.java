package com.yozo.loganalyse.controller;

import com.yozo.loganalyse.service.loganalyse.LogAnalyse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qinweiliang
 **/
@RestController
public class test {

    @Autowired
    private LogAnalyse logAnalyse;

    @RequestMapping("/test")
    public String test(){
        logAnalyse.analyseLog();
        return null;
    }
}
