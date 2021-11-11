package com.adtimokhin.util.token;

import org.springframework.stereotype.Component;

/**
 * @author adtimokhin
 * 11.11.2021
 **/

@Component
public interface TokenGenerator {

    String generate();

}
