package com.Elaa.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends a validation email.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param body    The body content of the email.
     */
    public void sendValidationEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            System.out.println("Validation email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("Failed to send validation email to " + to + ": " + e.getMessage());
            throw new RuntimeException("Email sending failed: " + e.getMessage());
        }
    }
}
