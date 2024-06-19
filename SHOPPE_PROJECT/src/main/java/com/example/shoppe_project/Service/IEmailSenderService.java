package com.example.shoppe_project.Service;

public interface IEmailSenderService {
    void sendSinpleMessage(String to, String subject, String text);
}
