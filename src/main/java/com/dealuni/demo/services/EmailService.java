package com.dealuni.demo.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String toEmail, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verificare cont - uniDeal");

        String text = "Salut!\n\nTe rugăm să verifici contul tău folosind acest cod:\n\n" + verificationCode + "\n\nMulțumim,\nEchipa uniDeal";

        message.setText(text);
        message.setFrom("unidealapp@gmail.com");  // aceeași ca în properties

        mailSender.send(message);
    }
}