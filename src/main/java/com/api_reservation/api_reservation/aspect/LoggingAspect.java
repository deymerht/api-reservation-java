package com.api_reservation.api_reservation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.api_reservation.api_reservation.controller.*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        
        // ANTES de ejecutar el método
        String methodName = joinPoint.getSignature().getName();
        System.out.println("🚀 INICIANDO método: " + methodName);
        
        long startTime = System.currentTimeMillis();
        
        try {
            // EJECUTAR el método original
            Object result = joinPoint.proceed();
            
            // DESPUÉS de ejecutar el método (éxito)
            long endTime = System.currentTimeMillis();
            System.out.println("✅ COMPLETADO método: " + methodName + " en " + (endTime - startTime) + "ms");
            
            return result;
            
        } catch (Exception e) {
            // DESPUÉS de ejecutar el método (error)
            System.out.println("❌ ERROR en método: " + methodName + " - " + e.getMessage());
            throw e;
        }
    }
} 