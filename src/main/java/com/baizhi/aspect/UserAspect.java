package com.baizhi.aspect;

import com.alibaba.fastjson.JSON;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/12/3  9:13
 */
@Aspect
@Configuration
public class UserAspect {
    @Autowired
    private UserService userService;

    /**
     * 定义一个后置通知
     * 当用户注册或修改信息时调用此功能
     * 此功能用来刷新注册趋势图、地理分布图
     */
    @After("@annotation(com.baizhi.annotation.StatAnnotation)")
    public void flushECharts(JoinPoint joinPoint) {
        //定义一个map 用来存储注册时间 和 地域分布
        Map m = new HashMap();
        Map map = userService.statTime();
        List list = userService.statCity();
        m.put("statTime", map);
        m.put("statCity", list);
        String jsonString = JSON.toJSONString(m);
        GoEasy goEasy = new GoEasy("https://rest-hangzhou.goeasy.io", "BC-d66f8040d8184462a7889036d02576b8");
        goEasy.publish("cmfz", jsonString);
    }
}
