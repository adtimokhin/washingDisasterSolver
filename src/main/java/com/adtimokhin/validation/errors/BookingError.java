package com.adtimokhin.validation.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@AllArgsConstructor
public class BookingError {

    @Getter
    public String message;

}
