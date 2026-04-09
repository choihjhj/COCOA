package com.cocoa.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {

	@Around("@annotation(com.cocoa.aop.LogExecutionTime)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("AOP 시작: " + joinPoint.getSignature());
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();  // 실제 메서드 실행

        long end = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
        log.info("{} 실행 시간 = {} ms",methodName,end-start);

        return result;
    }

}
