package com.adtimokhin.aspect;

import com.adtimokhin.controller.DefaultExceptionHandlerControlAdvice;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author adtimokhin
 * 18.10.2021
 **/

@Aspect
public class DebugAspect {

    private final static Logger logger = LoggerFactory.getLogger(DebugAspect.class);


    @Pointcut("@annotation(DebugMethod)")
    public void debugExecution() {
    }


    @Around("debugExecution()")
    public void testExecution(ProceedingJoinPoint jp) throws Throwable {

        String methodName = jp.getSignature().getName();
        String arguments = Arrays.toString(jp.getArgs());

        logger.debug("About to execute method {} with parameters {}", methodName, arguments);

        jp.proceed();

        logger.debug("Finished executing method {}", methodName);
    }

}
