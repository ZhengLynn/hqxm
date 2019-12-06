package com.baizhi.dao;

import com.baizhi.entity.Counter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  15:38
 */
public interface CounterDao extends Mapper<Counter> {
    List<Counter> findByUidAndCId(@Param("user_id") String uid, @Param("course_id") String id);
}
