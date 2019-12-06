package com.baizhi.frontcontroller;

import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  19:40
 */
@RestController
@RequestMapping("fGuru")
public class FrontGuruController {
    @Autowired
    private GuruService guruService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("showGuru")
    public Map showGuru(String uid) {
        Map map = new HashMap();
        try {
            List<Guru> gurus = guruService.findAll();
            map.put("status", 200);
            map.put("gurus", gurus);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "加载失败");
            return map;
        }
    }

    @RequestMapping("attention")
    public Map attention(String uid, String id) {
        Map map = new HashMap();
        try {
            BoundHashOperations attention = redisTemplate.boundHashOps("attention");
            Set<String> set = new HashSet<>();
            //判断该上师是否为键，若不为键，则创建一个
            if (!attention.hasKey(id)) {
                attention.put(id, "");
            } else { //若为键，则取出对应的值，并转为Set集合
                set.addAll((List<String>) attention.get(id));
            }
            //将当前用户id存入对应上师set中
            set.add(uid);
            attention.put(id, set);

            //拿到所有key
            Set keys = attention.keys();
            //循环判断每条数据是否包含当前用户
            List<Guru> gurus = new ArrayList<>();
            keys.forEach(key -> {
                Set<String> sets = new HashSet<>();
                sets.addAll((List<String>) attention.get(key));
                //若包含，则将该上师输出
                if (sets.contains(uid)) {
                    gurus.add(guruService.findById(key.toString()));
                }
            });
            map.put("status", 200);
            map.put("gurus", gurus);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "关注失败");
            return map;
        }
    }
}
