package com.yozo.loganalyse.dao.impl;

import com.yozo.loganalyse.commons.util.CommonUtils;
import com.yozo.loganalyse.commons.util.LocalCache;
import com.yozo.loganalyse.dao.OperateRecordDao;
import com.yozo.loganalyse.pojo.OperateRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@ConditionalOnExpression("'${loganalyse.dao.localcache}'.equals('true')")
public class RecordLocalCache implements OperateRecordDao {


    @Override
    public OperateRecord selectByComplexKeyAndDay(OperateRecord operateRecord) {
        String key=CommonUtils.generateKey(operateRecord);
        try {
            return (OperateRecord) LocalCache.get(key);
        }catch (Exception e){
            log.error("com.yozo.loganalyse.dao.impl.RecordLocalCache.selectByComplexKeyAndDay,ERROR:{}",e);
            return null;
        }
    }

    /**
     * 失效时间，采用默认24小时失效
     * @param record
     */
    @Override
    public void insert(OperateRecord record) {
        String key=CommonUtils.generateKey(record);
        try {
            LocalCache.put(key,record);
        }catch (Exception e){
            log.error("com.yozo.loganalyse.dao.impl.RecordLocalCache.insert,ERROR:{}",e);
        }
    }

    @Override
    public void updateOperateRecord(OperateRecord record) {
        insert(record);
    }

    @Override
    public List<OperateRecord> getRecordsByDate(Date date) {
        return null;
    }
}
