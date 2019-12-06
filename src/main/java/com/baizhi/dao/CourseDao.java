package com.baizhi.dao;

import com.baizhi.entity.Course;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  15:15
 */
public interface CourseDao extends Mapper<Course> {
    List<Course> findByUid(String uid);
}
