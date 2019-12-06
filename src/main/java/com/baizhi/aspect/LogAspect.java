package com.baizhi.aspect;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.entity.Admin;
import com.baizhi.entity.CmfzLog;
import com.baizhi.service.CmfzLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author: DarkSunrise
 * @date: 2019/11/28  16:26
 */
@Aspect
@Configuration
public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CmfzLogService cmfzLogService;

    @Around("@annotation(com.baizhi.annotation.LogAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) {
        /**
         * who  谁
         * time  什么时间
         * action  做了什么
         * status  是否成功  
         */
        //测试 admin
        request.getSession().setAttribute("admin", new Admin("1", "admin", "admin"));

        Admin admin = (Admin) request.getSession().getAttribute("admin");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        String declaringTypeName = signature.getDeclaringTypeName();
        String methodName = signature.getMethod().getName();
        String value = annotation.value();
        CmfzLog cmfzLog = new CmfzLog();
        cmfzLog.setId(UUID.randomUUID().toString().replace("-", ""))
                .setName(admin.getUsername())
                .setTime(format)
                .setAction(declaringTypeName + "." + methodName + " ==> " + value);
        try {
            Object proceed = proceedingJoinPoint.proceed();
            String status = "success";
            //System.out.println(admin.getUsername() + " " + format + " " + declaringTypeName + "." + methodName + " " + value + " " + status);
            cmfzLog.setStatus(status);
            cmfzLogService.save(cmfzLog);
            return proceed;
        } catch (Throwable throwable) {
            String status = "error";
            //System.out.println(admin.getUsername() + " " + format + " " + declaringTypeName + "." + methodName + " " + value + " " + status);
            cmfzLog.setStatus(status);
            cmfzLogService.save(cmfzLog);
            throwable.printStackTrace();
            return null;
        }
    }
}
