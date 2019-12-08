package com.baizhi.service.imp;

import com.baizhi.annotation.AddCacheAnnotation;
import com.baizhi.annotation.DelCacheAnnotation;
import com.baizhi.dao.CourseDao;
import com.baizhi.entity.Course;
import com.baizhi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  16:00
 */
@Service
@Transactional
public class CourseServiceImp implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCacheAnnotation
    public List<Course> findAll(String uid) {
        return courseDao.findByUid(uid);
    }

    @Override
    @DelCacheAnnotation
    public void save(Course course) {
        courseDao.insertSelective(course);
    }

    @Override
    @DelCacheAnnotation
    public void delete(String id) {
        courseDao.deleteByPrimaryKey(id);
    }

}
