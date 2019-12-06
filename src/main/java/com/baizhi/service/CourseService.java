package com.baizhi.service;

import com.baizhi.entity.Course;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  15:39
 */
public interface CourseService {
    List<Course> findAll(String uid);

    void save(Course course);

    void delete(String id);
}
