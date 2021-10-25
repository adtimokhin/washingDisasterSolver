package com.adtimokhin.exceptions;

/**
 * @author adtimokhin
 * 08.10.2021
 **/

public class EmptyFieldException extends Exception {

    public EmptyFieldException(){}

    public EmptyFieldException(String msg){
        super(msg);
    }
}
