package com.user.management.aop;

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

    // Log controller layer
    @Before("execution(* com.user.management.controller.*.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.info("Method: {} - Args: {}", 
            joinPoint.getSignature().getName(), 
            Arrays.toString(joinPoint.getArgs()));
    }

    // Log service layer
    @Before("execution(* com.user.management.service.*.*(..))")
    public void logBeforeService(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.info("Method: {} - Args: {}", 
            joinPoint.getSignature().getName(), 
            Arrays.toString(joinPoint.getArgs()));
    }

    // Log repository layer
    @Before("execution(* com.user.management.repository.*.*(..))")
    public void logBeforeRepository(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.debug("Method: {} - Args: {}", 
            joinPoint.getSignature().getName(), 
            Arrays.toString(joinPoint.getArgs()));
    }

    // Log method execution time
    @Around("execution(* com.user.management..*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        logger.debug("Method {} executed in {} ms", 
            joinPoint.getSignature().getName(), 
            executionTime);

        return result;
    }
}