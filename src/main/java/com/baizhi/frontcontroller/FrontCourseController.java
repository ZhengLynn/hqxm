package com.baizhi.frontcontroller;

import com.baizhi.entity.Course;
import com.baizhi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  16:08
 */
@RestController
@RequestMapping("fCourse")
public class FrontCourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("showCourse")
    public Map showCourse(String uid) {
        Map map = new HashMap();
        try {
            List<Course> courses = courseService.findAll(uid);
            map.put("status", 200);
            map.put("courses", courses);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "加载失败");
            return map;
        }
    }

    @RequestMapping("save")
    public Map save(String uid, String title) {
        Map map = new HashMap();
        try {
            Course course = new Course();
            course.setId(UUID.randomUUID().toString().replace("-", ""))
                    .setUser_id(uid)
                    .setTitle(title)
                    .setCategory(1)
                    .setCreate_date(new Date());
            courseService.save(course);
            List<Course> courses = courseService.findAll(uid);
            map.put("status", 200);
            map.put("courses", courses);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "操作失败");
            return map;
        }
    }

    @RequestMapping("delete")
    public Map delete(String uid, String id) {
        Map map = new HashMap();
        try {
            courseService.delete(id);
            List<Course> courses = courseService.findAll(uid);
            map.put("status", 200);
            map.put("courses", courses);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "操作失败");
            return map;
        }
    }
}
