package com.visiplus.graines.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    
    /**
     * Pointcut pour toutes les méthodes du package service
     */
    @Pointcut("execution(* com.visiplus.graines.service..*(..))")
    public void serviceMethods() {}
    
    /**
     * Advice exécuté avant chaque méthode du service
     */
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        log.info(">>> Entrée dans {}.{} avec les paramètres: {}", 
                className, methodName, Arrays.toString(args));
    }
    
    /**
     * Advice exécuté après le retour normal d'une méthode du service
     */
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        log.info("<<< Sortie de {}.{} avec le résultat: {}", 
                className, methodName, result);
    }
    
    /**
     * Advice exécuté après qu'une exception soit levée dans une méthode du service
     */
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        log.error("!!! Exception dans {}.{}: {}", 
                className, methodName, exception.getMessage(), exception);
    }
    
    /**
     * Advice exécuté autour d'une méthode du service (mesure du temps d'exécution)
     */
    @Around("serviceMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            log.info("=== {}.{} exécuté en {} ms", className, methodName, executionTime);
            
            return result;
        } catch (Throwable throwable) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("=== {}.{} a échoué après {} ms", className, methodName, executionTime);
            throw throwable;
        }
    }
}
