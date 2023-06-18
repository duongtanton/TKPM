package com.tkpm.studentsmanagement.service;

import java.io.IOException;

import jakarta.mail.MessagingException;

public interface IEmailService {
    public void sendEmail(String to, String subject, String url, String directory) throws MessagingException, IOException;
}
