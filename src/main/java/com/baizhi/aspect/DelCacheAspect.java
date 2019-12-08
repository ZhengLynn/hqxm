package com.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: DarkSunrise
 * @date: 2019/12/6  17:26
 */
@Aspect
@Configuration
public class DelCacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @After("@annotation(com.baizhi.annotation.DelCacheAnnotation)")
    public void delCache(JoinPoint jp) {
        //获取当前类作为 外部KEY
        String className = jp.getTarget().getClass().getName();
        //删除当前KEY
        redisTemplate.delete(className);
    }
}
