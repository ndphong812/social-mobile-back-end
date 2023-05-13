package com.backend.social.mail;

public interface EmailSender {


    void send(String toEmail, String subject, String content);
}
