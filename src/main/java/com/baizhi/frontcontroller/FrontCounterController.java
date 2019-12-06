package com.baizhi.frontcontroller;

import com.baizhi.entity.Counter;
import com.baizhi.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  16:55
 */
@RestController
@RequestMapping("fCounter")
public class FrontCounterController {
    @Autowired
    private CounterService counterService;

    @RequestMapping("showCounter")
    public Map showCounter(String uid, String course_id) {
        Map map = new HashMap();
        try {
            List<Counter> counters = counterService.findByUidAndCId(uid, course_id);
            map.put("status", 200);
            map.put("counters", counters);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "加载失败");
            return map;
        }
    }

    @RequestMapping("save")
    public Map save(String uid, String name, String course_id) {
        Map map = new HashMap();
        try {
            Counter counter = new Counter();
            counter.setCounts(0).setCourse_id(course_id).setId(UUID.randomUUID().toString().replace("-", ""))
                    .setLast_date(new Date()).setName(name).setUser_id(uid);
            counterService.save(counter);
            List<Counter> counters = counterService.findByUidAndCId(uid, course_id);
            map.put("status", 200);
            map.put("counters", counters);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "操作失败");
            return map;
        }
    }

    @RequestMapping("update")
    public Map update(String uid, String id, Integer count, String course_id) {
        Map map = new HashMap();
        try {
            Counter counter = new Counter();
            counter.setCounts(count).setId(id);
            counterService.update(counter);
            List<Counter> counters = counterService.findByUidAndCId(uid, course_id);
            map.put("status", 200);
            map.put("counters", counters);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "操作失败");
            return map;
        }
    }

    @RequestMapping("delete")
    public Map delete(String uid, String id, String course_id) {
        Map map = new HashMap();
        try {
            counterService.delete(id);
            List<Counter> counters = counterService.findByUidAndCId(uid, course_id);
            map.put("status", 200);
            map.put("counters", counters);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "操作失败");
            return map;
        }
    }
}
