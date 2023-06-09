package com.tkpm.studentsmanagement.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tkpm.studentsmanagement.service.IEmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmailService  implements IEmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String to, String subject, String url, String directory) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        // Load HTML template
        ClassPathResource templateResource = new ClassPathResource(directory);
        String template = new String(templateResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // Replace placeholders with dynamic values
        String content = template.replace("{{url}}", url);

        // Set email properties
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true); // Set content as HTML

        // Send the email
        javaMailSender.send(message);
    }
}
