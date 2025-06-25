package com.fiala.library_management_system.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.fiala.library_management_system.service.*.*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, "SERVICE");
    }


    @Around("execution(* com.fiala.library_management_system.controller.*.*(..))")
    public Object logResponseEntityMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, "REST_ENDPOINT");
    }

    @Around("execution(* com.fiala.library_management_system.dao.*.*(..))")
    public Object logRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, "REPOSITORY");
    }

    /**
     * Common method to handle logging for all pointcuts
     * @param joinPoint the method being intercepted
     * @param layer the application layer (SERVICE, CONTROLLER, etc.)
     * @return the result of the method execution
     * @throws Throwable if the method throws an exception
     */
    private Object logMethodExecution(ProceedingJoinPoint joinPoint, String layer) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String actualClassName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        Object[] args = joinPoint.getArgs();

        // Log method start
        log.info("[{}] Starting method: {}.{} with {} - Args: {}",
                layer, actualClassName, methodName, args.length, Arrays.toString(args));


        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            String returnValue = formatReturnValue(result);

            // Log successful completion
            log.info("[{}] Completed method: {}.{} in {}ms - Returned: {}",
                    layer, actualClassName, methodName, duration, returnValue);
            return result;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;

            // Log exception with full stack trace
            log.error("[{}] Exception in method: {}.{} after {}ms - {}",
                    layer, actualClassName, methodName, duration, e.getMessage(), e);

            // Re-throw the exception to maintain normal flow
            throw e;
        }
    }

     /* Safely format return values for logging */
    private String formatReturnValue(Object result) {
        if (result == null) {
            return "null";
        }

        String className = result.getClass().getSimpleName();

        // For collections, show size instead of full content
        if (result instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) result;
            return String.format("%s(size=%d)", className, collection.size());
        }

        // For Optional, show if present/empty
        if (result instanceof Optional<?>) {
            Optional<?> optional = (Optional<?>) result;
            return optional.isPresent() ?
                    String.format("Optional[%s]", formatReturnValue(optional.get())) :
                    "Optional.empty";
        }

        // For ResponseEntity, show status and body type
        if (result instanceof org.springframework.http.ResponseEntity<?>) {
            org.springframework.http.ResponseEntity<?> response =
                    (org.springframework.http.ResponseEntity<?>) result;
            return String.format("ResponseEntity[status=%s, body=%s]",
                    response.getStatusCode(),
                    response.getBody() != null ? response.getBody().getClass().getSimpleName() : "null");
        }

        // For large objects, don't log full content
        String resultStr = result.toString();
        if (resultStr.length() > 200) {
            return String.format("%s[length=%d, preview=%s...]",
                    className, resultStr.length(), resultStr.substring(0, 50));
        }

        // For simple types, log the actual value
        return resultStr;
    }

}