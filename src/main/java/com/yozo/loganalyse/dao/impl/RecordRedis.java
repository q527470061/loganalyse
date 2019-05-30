package com.yozo.loganalyse.dao.impl;

import com.yozo.loganalyse.commons.cons.CommonConstant;
import com.yozo.loganalyse.commons.util.CommonUtils;
import com.yozo.loganalyse.commons.util.JsonUtils;
import com.yozo.loganalyse.commons.util.RedisUtil;
import com.yozo.loganalyse.dao.OperateRecordDao;
import com.yozo.loganalyse.pojo.OperateRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@ConditionalOnExpression("'${loganalyse.dao.redis}'.equals('true')")
public class RecordRedis implements OperateRecordDao {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public OperateRecord selectByComplexKeyAndDay(OperateRecord operateRecord) {
        String key=CommonUtils.generateKey(operateRecord);
        String jsonData=null;
        try {
            jsonData=(String) redisUtil.get(key);
        }catch (Exception e){
            log.error("com.yozo.loganalyse.dao.impl.RecordRedis.selectByComplexKeyAndDay,ERROR:{}",e);
        }
        if(StringUtils.isEmpty(jsonData)){
            return null;
        }

        return JsonUtils.jsonToPojo(jsonData,OperateRecord.class);
    }

    /**
     * 新存的设置有效期
     * @param record
     */
    @Override
    public void insert(OperateRecord record) {
        String key=CommonUtils.generateKey(record);
        try{
            redisUtil.set(key,JsonUtils.objectToJson(record),CommonConstant.REDIS_LOG_EXPIRE_TIME);
        }catch (Exception e){
            log.error("com.yozo.loganalyse.dao.impl.RecordRedis.insert,ERROR:{}",e);
        }
    }

    @Override
    public void updateOperateRecord(OperateRecord record) {
        String key=CommonUtils.generateKey(record);
        try {
            redisUtil.set(key,JsonUtils.objectToJson(record));
        }catch (Exception e){
            log.error("com.yozo.loganalyse.dao.impl.RecordRedis.updateOperateRecord,ERROR:{}",e);
        }
    }




}
