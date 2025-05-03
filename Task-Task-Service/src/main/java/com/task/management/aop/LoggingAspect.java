package com.task.management.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.task.management.controller.*.*(..))")
    public void controllerMethods() {}

    @Pointcut("execution(* com.task.management.service.*.*(..))")
    public void serviceMethods() {}

    @Pointcut("execution(* com.task.management.repository.*.*(..))")
    public void repositoryMethods() {}

    @Pointcut("execution(* com.task.management..*(..))")
    public void allMethodsInApp() {}

    @Before("controllerMethods()")
    public void logControllerMethodEntry(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.info("[CONTROLLER] Method: {} | Args: {}",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @Before("serviceMethods()")
    public void logServiceMethodEntry(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.debug("[SERVICE] Executing: {}", joinPoint.getSignature().getName());
    }

    @Before("repositoryMethods()")
    public void logRepositoryMethodEntry(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.trace("[REPOSITORY] Accessing: {}", joinPoint.getSignature().getName());
    }

    @Around("allMethodsInApp()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        logger.debug("[PERFORMANCE] {} executed in {} ms", joinPoint.getSignature().getName(), duration);
        return result;
    }

    @AfterThrowing(pointcut = "allMethodsInApp()", throwing = "ex")
    public void logExceptions(JoinPoint joinPoint, Throwable ex) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.error("[ERROR] Method {} failed: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }
}
