package com.dataworks.eventsubscriber.service;

import com.dataworks.eventsubscriber.exception.EmailSendFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class GmailEmailProvider implements EmailProvider {

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.subject}")
    private String subject;
    @Value("${spring.mail.content}")
    private String content;

    @Override
    public void send(String email, String token) throws EmailSendFailedException {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setPort(port);

            mailSender.setUsername(username);
            mailSender.setPassword(password);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(username);
            helper.setSubject(subject);
            helper.setTo(email);
            helper.setText(String.format(content, token), true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new EmailSendFailedException(e.getMessage());
        }
    }
}
