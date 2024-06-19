package com.example.shoppe_project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService implements IEmailSenderService {

    @Autowired
    private JavaMailSender emailSender ;

    @Override
    public void sendSinpleMessage(String to, String subject, String text) {
        // Logic other
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        // Logic other
    }
}
