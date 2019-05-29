package com.yozo.loganalyse.service.loganalyse.impl;

import com.yozo.loganalyse.mapper.OperateRecordMapper;
import com.yozo.loganalyse.pojo.OperateRecord;
import com.yozo.loganalyse.service.loganalyse.LogAnalyse;
import com.yozo.loganalyse.service.logcollect.LogCollect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FirstAnalyseStyle implements LogAnalyse {

    @Autowired
    private OperateRecordMapper operateRecordMapper;
    @Autowired
    private LogCollect logCollect;

    @Override
    public void analyseLog() {
        // 初始化日志容器
        List<OperateRecord> operateRecords=new ArrayList<>();

        // 开始收集日志
        logCollect.getCertainLineOfTxt("F:\\日志分析\\auth3.log",0,operateRecords);

        log.error("--------------------------------------------------一共日志记录数：{}",operateRecords.size());

        // 分析存取有效日志
        operateRecords.stream()
                .sorted(Comparator.comparing(OperateRecord::getLastAccessTime))
                .forEach(record -> writeRecord(record));
    }

    public void writeRecord(OperateRecord record){
        OperateRecord operateRecord=operateRecordMapper.selectByComplexKeyAndDay(record);
        // 数据库不存在记录
        if (null==operateRecord){
            record.setOnlineTime(0L);
            try{
                operateRecordMapper.insert(record);
            }catch (Exception e){
                log.error("com.yozo.loganalyse.service.loganalyse.impl.FirstAnalyseStyle.writeRecord--ERROR:{}",e);
            }
            return;
        }

        // 数据库存在记录
        long timeDifference=record.getLastAccessTime().getTime()-operateRecord.getLastAccessTime().getTime();
        // 时间差小于30分
        if(timeDifference<=1000*60*30){
            operateRecord.setOnlineTime(operateRecord.getOnlineTime()+timeDifference);
        }
        operateRecord.setLastAccessTime(record.getLastAccessTime());
        try{
            operateRecordMapper.updateByPrimaryKeySelective(operateRecord);
        }catch (Exception e){
            log.error("com.yozo.loganalyse.service.loganalyse.impl.FirstAnalyseStyle.writeRecord--ERROR:{}",e);
        }
    }
}
