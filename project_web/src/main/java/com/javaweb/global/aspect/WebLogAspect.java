package com.javaweb.global.aspect;
/**
 * Created by YuKai Fan on 2019/4/25.
 */

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: project_parent
 * @description: 全局日志切面
 * @author: Yukai Fan
 * @create: 2019-04-25 20:53
 **/
@Aspect//使用aspectJ,来声明该类为切面类
@Component
public class WebLogAspect {
    //初始化logger
    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 包下定义的所有请求为切入点
     */
    @Pointcut("execution(public * com.javaweb.controller..*.*(..)) || execution(* com.javaweb.global.exception.*.*(..))")
    public void webLog() {}

    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        //开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //打印请求相关参数
        logger.info("==============================Start==============================");
        //打印请求url
        logger.info("URL          : {}", request.getRequestURL().toString());
        //打印请求url
        logger.info("HTTP Method  : {}", request.getMethod());
        //打印调用controller的全路径以及执行方法
        logger.info("Class Method : {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        //打印请求ip
        logger.info("IP           : {}", request.getRemoteAddr());
        //打印请求入参
        logger.info("Request Args : {}", JSON.toJSON(joinPoint.getArgs()));
    }

    /**
     * 在切入点之后织入
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() throws Throwable {
        logger.info("==============================End==============================");
        //空一行
        logger.info("");
    }

    /**
     * 环绕,(意思有待学习)
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();

        //打印出参
        logger.info("Response Args   : {}", JSON.toJSON(proceed));
        //执行耗时
        logger.info("Time-Consuming  : {} ms", System.currentTimeMillis() - startTime);
        return proceed;
    }
}
