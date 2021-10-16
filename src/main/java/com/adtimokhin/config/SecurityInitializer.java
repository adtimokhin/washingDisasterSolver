package com.adtimokhin.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author adtimokhin
 * 10.08.2021
 **/

@Order(2)
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    // Make sure that you use @Order annotation from  org.springframework.core.annotation.Order
}



