package com.baizhi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: DarkSunrise
 * @date: 2019/12/6  16:32
 */
@Aspect
@Configuration
public class AddCacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.baizhi.annotation.AddCacheAnnotation)")
    public Object addCache(ProceedingJoinPoint pjp) {
        //类名 作为redis中hash的外部KEY
        String className = pjp.getTarget().getClass().getName();
        //方法名+参数类型+参数值 作为内部key
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            methodName = methodName + arg.getClass().getName() + arg;
        }

        //查询当前key是否存在
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(className);
        if (boundHashOperations.hasKey(methodName)) {
            return boundHashOperations.get(methodName);
        } else {
            try {
                Object proceed = pjp.proceed();
                boundHashOperations.put(methodName, proceed);
                return proceed;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
        }
    }
}
