package com.phuckhanh.VideoApp.service;

import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class EmailService {

    @Value("${spring.mail.username}")
    String fromMail;

    @Autowired
    JavaMailSender javaMailSender;

    public String sendMail(String to, String subject, String body, String randomCode) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromMail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);

            String bodyWithCode = body + " <h1 style=\"color: red;\">" + randomCode + "<h1>";

            mimeMessageHelper.setText(bodyWithCode, true);

            javaMailSender.send(mimeMessage);

            return "mail send";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
