package com.submission.management.aop;

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

    // =========================================================
    // 1. Pointcut Definitions
    // =========================================================
    private static final String CONTROLLER_POINTCUT = 
        "execution(* com.submission.management.controller.*.*(..))";
    
    private static final String SERVICE_POINTCUT = 
        "execution(* com.submission.management.service.*.*(..))";
    
    private static final String REPOSITORY_POINTCUT = 
        "execution(* com.submission.management.repository.*.*(..))";
    
    private static final String ALL_LAYERS_POINTCUT = 
        "execution(* com.submission.management..*(..))";

    // =========================================================
    // 2. Controller Layer Logging
    // =========================================================
    @Before(CONTROLLER_POINTCUT)
    public void logControllerMethodEntry(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.info("[CONTROLLER] Method: {} | Args: {}", 
            joinPoint.getSignature().getName(),
            Arrays.toString(joinPoint.getArgs()));
    }

    // =========================================================
    // 3. Service Layer Logging
    // =========================================================
    @Before(SERVICE_POINTCUT)
    public void logServiceMethodEntry(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.debug("[SERVICE] Executing: {}", 
            joinPoint.getSignature().getName());
    }

    // =========================================================
    // 4. Repository Layer Logging
    // =========================================================
    @Before(REPOSITORY_POINTCUT)
    public void logRepositoryMethodEntry(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.trace("[REPOSITORY] Accessing: {}", 
            joinPoint.getSignature().getName());
    }

    // =========================================================
    // 5. Execution Time Tracking
    // =========================================================
    @Around(ALL_LAYERS_POINTCUT)
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;
        logger.debug("[PERFORMANCE] {} executed in {} ms", 
            joinPoint.getSignature().getName(), 
            duration);
        return result;
    }

    // =========================================================
    // 6. Error Handling
    // =========================================================
    @AfterThrowing(pointcut = ALL_LAYERS_POINTCUT, throwing = "ex")
    public void logExceptions(JoinPoint joinPoint, Throwable ex) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.error("[ERROR] Method {} failed: {}", 
            joinPoint.getSignature().getName(), 
            ex.getMessage());
    }
}