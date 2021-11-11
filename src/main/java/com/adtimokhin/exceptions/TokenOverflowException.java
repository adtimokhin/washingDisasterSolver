package com.adtimokhin.exceptions;

/**
 * @author adtimokhin
 * 11.11.2021
 **/

public class TokenOverflowException extends RuntimeException {

    public TokenOverflowException(String message) {
        super(message);
    }
}
