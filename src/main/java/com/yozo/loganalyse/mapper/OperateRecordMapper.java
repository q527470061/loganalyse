package com.yozo.loganalyse.mapper;

import com.yozo.loganalyse.pojo.OperateRecord;
import com.yozo.loganalyse.pojo.OperateRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperateRecordMapper {
    int countByExample(OperateRecordExample example);

    int deleteByExample(OperateRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OperateRecord record);

    int insertSelective(OperateRecord record);

    List<OperateRecord> selectByExample(OperateRecordExample example);

    OperateRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OperateRecord record, @Param("example") OperateRecordExample example);

    int updateByExample(@Param("record") OperateRecord record, @Param("example") OperateRecordExample example);

    int updateByPrimaryKeySelective(OperateRecord record);

    int updateByPrimaryKey(OperateRecord record);

    /**
    * @description 以key:date-userId-app-ip,查询record
    * @author qinweiliang
    * @param [operateRecord]
    * @return com.yozo.loganalyse.pojo.OperateRecord
    */
    OperateRecord selectByComplexKeyAndDay(OperateRecord operateRecord);
}