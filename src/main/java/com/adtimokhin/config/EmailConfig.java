package com.adtimokhin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author adtimokhin
 * 17.08.2021
 **/

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.protocol}")
    private String protocol;

    @Value("${mail.smtp.auth}")
    private String auth;

    @Value("${mail.smtp.ssl.enable}")
    private String sslEnable;


    @Bean
    public JavaMailSender getJavaMailSender(){

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host); // setting smtp host
        javaMailSender.setUsername(username); // setting username
        javaMailSender.setPassword(password); // setting password for the email account
        javaMailSender.setPort(port);// setting port
        javaMailSender.setProtocol(protocol); // setting the protocol

        Properties properties = javaMailSender.getJavaMailProperties(); // some properties have to be set by Properties object

        properties.setProperty("mail.smtp.auth" , auth);// this property tells that we have configured
                                        // the authentication details so that connection can be established

        properties.setProperty("mail.smtp.ssl.enable" , sslEnable); // telling JavaMail to use SSL


        return javaMailSender;

    }


}
