package org.gdpi.course.component;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author zhf
 */
@Component
@Aspect
@Slf4j
public class MapperAspect {
    @Pointcut("execution(* org.gdpi.course.mapper.*.*(..))")
    public void f() {}

    @Around("f()")
    public Object around(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            result = pjp.proceed(pjp.getArgs());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (result != null) {
            log.info(pjp.getSignature().getDeclaringTypeName()+"参数:"+pjp.getArgs()+"返回值:"+result.toString());
        }

        return result;
    }
}
