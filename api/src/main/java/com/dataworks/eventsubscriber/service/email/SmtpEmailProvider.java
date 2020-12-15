package com.dataworks.eventsubscriber.service.email;


import com.dataworks.eventsubscriber.exception.EmailSendFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class SmtpEmailProvider extends EmailProvider {
    private final JavaMailSenderImpl mailSender;
    @Value("${spring.mail.smtp}")
    private String smtp;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Override
    public void send() {
        //throw exception if email is not set
        //throw exception when subject is not set
        //throw exception when content is not set

        try {
            mailSender.setHost(smtp);
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
            helper.setSubject(this.getSubject());
            helper.setTo(this.getEmail());
            helper.setText(this.getContent(), true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new EmailSendFailedException(e.getMessage());
        }
    }
}
