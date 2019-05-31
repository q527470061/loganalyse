package com.yozo.loganalyse.service.loganalyse.impl;

import com.yozo.loganalyse.commons.cons.CommonConfig;
import com.yozo.loganalyse.commons.cons.CommonConstant;
import com.yozo.loganalyse.commons.util.CommonUtils;
import com.yozo.loganalyse.dao.OperateRecordDao;
import com.yozo.loganalyse.mapper.OperateRecordMapper;
import com.yozo.loganalyse.pojo.OperateRecord;
import com.yozo.loganalyse.service.cache.Cache;
import com.yozo.loganalyse.service.loganalyse.LogAnalyse;
import com.yozo.loganalyse.service.logcollect.LogCollect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;


import java.util.*;

/**
 * @author qinweiliang
 */
@Slf4j
@Service
public class FirstAnalyseStyle implements LogAnalyse {

    @Autowired
    private OperateRecordDao operateRecordDao;
    @Autowired
    private LogCollect logCollect;
    @Autowired
    private Cache cache;
    @Autowired
    private OperateRecordMapper operateRecordMapper;

    @Value("${loganalyse.logs.path}")
    private String collectLogs;

    @Value("${loganalyse.cache.enable}")
    private String enableCache;

    @Override
    public void analyseLog() {
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();

        // 初始化日志容器
        List<OperateRecord> operateRecords=new ArrayList<>();

        //准备文件
        String[] logs=collectLogs.split(",");

        // 开始收集日志
        for (String filePath:logs){
            String key=CommonUtils.generateKey(filePath);
            int lineNumber=0;
            if(cache.existKey(key)){
                lineNumber=cache.readLastLine(key);
            }
            logCollect.getCertainLineOfTxt(filePath,lineNumber,operateRecords);
        }

        int recordSize=operateRecords.size();
        log.info("--------------------------------------------------一共日志记录数：{}-------------------------------------------",recordSize);

        if(recordSize<=0){
            return;
        }
        // 分析存取有效日志
        operateRecords.stream()
                .sorted(Comparator.comparing(OperateRecord::getLastAccessTime))
                .forEach((record -> writeRecord(record)));

        stopWatch.stop();
        log.info("----------------------------------------------------收集分析执行完成，用时{}----------------------------------------------",stopWatch.getTotalTimeSeconds());

        // 如果采用localcache和redis，需要同步到mysql
        if("true".equals(enableCache)){
            synchronousMysql(operateRecords.get(0).getLastAccessTime());
        }
    }


    /**
     * 读写记录到数据库
     * @param record
     */
    public void writeRecord(OperateRecord record){
        OperateRecord operateRecord=operateRecordDao.selectByComplexKeyAndDay(record);
        // 数据库不存在记录
        if (null==operateRecord){
            record.setOnlineTime(0L);
            operateRecordDao.insert(record);
            return;
        }

        // 数据库存在记录
        long timeDifference=record.getLastAccessTime().getTime()-operateRecord.getLastAccessTime().getTime();
        // 时间差小于30分
        if(timeDifference<=CommonConstant.ONLINEUNITTIME){
            operateRecord.setOnlineTime(operateRecord.getOnlineTime()+timeDifference);
        }
        operateRecord.setLastAccessTime(record.getLastAccessTime());
        operateRecordDao.updateOperateRecord(operateRecord);
    }

    // 同步缓存到mysql，按日期同步
    public void synchronousMysql(Date date){
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        List<OperateRecord> operateRecords=operateRecordDao.getRecordsByDate(date);
        if (CollectionUtils.isEmpty(operateRecords)){
            return;
        }
        System.out.println(operateRecords.size());
        try {
            operateRecordMapper.insertBatch(operateRecords);
        }catch (Exception e){
            log.error("com.yozo.loganalyse.service.loganalyse.impl.FirstAnalyseStyle.synchronousMysql,ERROR:{}",e);
        }
        stopWatch.stop();
        log.info("----------------------------------------------------同步执行完成，用时{}----------------------------------------------",stopWatch.getTotalTimeSeconds());

    }

}
