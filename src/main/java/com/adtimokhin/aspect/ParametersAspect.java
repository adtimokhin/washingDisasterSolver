package com.adtimokhin.aspect;

import com.adtimokhin.exceptions.EmptyFieldException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author adtimokhin
 * 25.10.2021
 **/

@Aspect
public class ParametersAspect {

    private final static Logger logger = LoggerFactory.getLogger(ParametersAspect.class);


    @Pointcut("@annotation(NotEmptyArguments)")
    public void checkArguments() {
    }

    @Before("checkArguments()")
    public void testExecution(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getSignature().toString();
        List<Integer> nulls = new ArrayList<>();
        List<Integer> empties = new ArrayList<>();


        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                nulls.add(i);
                continue;
            }
            Object argument = args[i];
            if (argument.getClass().equals(String.class)) {
                String arg = (String) argument;
                if (arg.isEmpty()) {
                    empties.add(i);
                }
            }
        }

        if (nulls.size() != 0 || empties.size() != 0) {

            StringBuilder nullSB = new StringBuilder();
            StringBuilder emptySB = new StringBuilder();

            for (int i :
                    nulls) {
                nullSB.append(i);
                nullSB.append(", ");
            }

            for (int i :
                    empties) {
                emptySB.append(i);
                emptySB.append(", ");
            }

            logger.error("Class {} was invoked with insufficient arguments. " +
                    "Parameters at the following positions were null: {} " +
                    "and the following were Strings without any data in them: {} ",className, nullSB.toString(), emptySB.toString());

            throw new EmptyFieldException(className);
        }
    }


}
