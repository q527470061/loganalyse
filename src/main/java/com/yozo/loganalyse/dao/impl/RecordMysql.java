package com.yozo.loganalyse.dao.impl;

import com.yozo.loganalyse.dao.OperateRecordDao;
import com.yozo.loganalyse.mapper.OperateRecordMapper;
import com.yozo.loganalyse.pojo.OperateRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnExpression("'${loganalyse.dao.mysql}'.equals('true')")
public class RecordMysql implements OperateRecordDao {

    @Autowired
    private OperateRecordMapper recordMapper;

    @Override
    public OperateRecord selectByComplexKeyAndDay(OperateRecord operateRecord) {
        return recordMapper.selectByComplexKeyAndDay(operateRecord);
    }

    @Override
    public void insert(OperateRecord record) {
        try {
            recordMapper.insert(record);
        }catch (Exception e){
            log.error("com.yozo.loganalyse.dao.impl.RecordMysql.insert,Error:{}",e);
        }
    }

    @Override
    public void updateOperateRecord(OperateRecord record) {
        try{
            recordMapper.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            log.error("com.yozo.loganalyse.dao.impl.RecordMysql.updateOperateRecord,Error:{}",e);
        }
    }
}
