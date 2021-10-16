package com.adtimokhin.validation.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author adtimokhin
 * 16.10.2021
 **/

@AllArgsConstructor
public class UserError {

    @Getter
    public String message;

}
