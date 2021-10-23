package com.adtimokhin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author adtimokhin
 * 18.10.2021
 **/

@Aspect
public class DebugAspect {
    @Pointcut("@annotation(DebugMethod)")
    public void debugExecution() {
    }


    @Around("debugExecution()")
    public void testExecution(ProceedingJoinPoint jp){

    }

}
