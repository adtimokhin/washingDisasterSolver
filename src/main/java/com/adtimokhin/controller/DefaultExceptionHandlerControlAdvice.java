package com.adtimokhin.controller;

import com.adtimokhin.exceptions.NoSuchUserFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

/**
 * @author adtimokhin
 * 13.08.2021
 **/

@ControllerAdvice
public class DefaultExceptionHandlerControlAdvice {

    private final static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandlerControlAdvice.class);

    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public String noHandlerFoundExceptionHandler() {
        return "errors/404";
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(value = NoSuchUserFoundException.class)
    public String noSuchUserFoundExceptionHandler(Exception e) {
        logger.error("Internal server error it was caused by " + e.getMessage() + ".\nFull stack trace:" + Arrays.toString(e.getStackTrace()));
        // todo: add functionality for sending user to register.
        return "errors/500";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(value = Exception.class)
    public String sampleExceptionHandler(Exception e) {
        logger.error("Internal server error it was caused by " + e.getMessage() + ".\nFull stack trace:" + Arrays.toString(e.getStackTrace()));
        return "errors/500";
    }


}
