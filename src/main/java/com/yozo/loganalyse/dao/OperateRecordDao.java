package com.yozo.loganalyse.dao;

import com.yozo.loganalyse.pojo.OperateRecord;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OperateRecordDao {


    /**
     * 查询
     * @param operateRecord
     * @return
     */
    OperateRecord selectByComplexKeyAndDay(OperateRecord operateRecord);

    /**
     * 保存新记录
     * @param record
     */
    void insert(OperateRecord record);

    /**
     * 更新记录
     * @param record
     */
    void updateOperateRecord(OperateRecord record);

    /**
     *
     */
    List<OperateRecord> getRecordsByDate(Date date);
}
