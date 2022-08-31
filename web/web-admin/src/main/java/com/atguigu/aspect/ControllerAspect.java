package com.atguigu.aspect;

import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import org.apache.dubbo.config.annotation.Reference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author luoyin
 * @Date 15:49 2022/8/29
 **/
@Aspect
@Component
public class ControllerAspect {

    private static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
    @Reference
    AdminService adminService;

    @Around("execution(* *..*Controller.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        //用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if(userName == null || "anonymousUser".equals(userName)){
            return pjp.proceed();
        }
        long startTime = System.currentTimeMillis();
        Admin admin = adminService.getByUserName(userName);
        Long userId = admin.getId();

        //请求信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();
        //类名 方法名 参数
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if(i < args.length - 1){
                params.append("param"+ (i+1) + ":"+args[i]+",");
            }else {
                params.append("param"+ (i+1) + ":"+args[i]);
            }
        }
        Object proceed = pjp.proceed();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        String log = "用户编号 : " + userId +
                " -- 用户名 : " + userName +
                " -- 请求路径 : " + url +
                " -- 请求方式 : " + method +
                " -- ip地址 : " + ip +
                " -- 类名 : " + className +
                " -- 方法名 : " + methodName +
                " -- 参数 : " + (params.toString().equals("") ? "无参数" : params) +
                " -- 运行时长 : " + time + "毫秒";
        logger.debug(log);
        return proceed;
    }

}
