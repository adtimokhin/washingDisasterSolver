package com.adtimokhin.util.email;

import com.adtimokhin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author adtimokhin
 * 11.11.2021
 **/
@Component
public class SimpleEmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.username}") // apparently, we can access the .properties file from other parts of the application
    private String senderEmail;

    private static final String HOSTNAME = "localhost:8080";

    /**
     * @param recipient is {@link com.adtimokhin.model.User} to which we will send our verification letter
     **/
    @Async
    public void sendEmailVerificationEmail(User recipient) {
        MimeMailMessage message = new MimeMailMessage(javaMailSender.createMimeMessage());
        message.setFrom(senderEmail); // this is how we set the sender now
        message.setTo(recipient.getEmail()); // this is how you set recipients now
        message.setSubject("Email verification"); // this is going to be the subject of your email
        message.setText("Please, follow this link to verify your email: \n"
                + HOSTNAME + "/verify/" + recipient.getEmailToken());// this text will be in your email's body
        javaMailSender.send(message.getMimeMessage());

    }
}
