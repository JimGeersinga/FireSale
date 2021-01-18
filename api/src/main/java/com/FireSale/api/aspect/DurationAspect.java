package com.FireSale.api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DurationAspect {

    @Around("@annotation(LogDuration)")
    public Object measureDuration(ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        final long executionTime = System.currentTimeMillis() - start;
        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        log.trace(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }
}
