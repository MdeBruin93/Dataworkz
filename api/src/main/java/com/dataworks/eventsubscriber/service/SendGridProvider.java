package com.dataworks.eventsubscriber.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class SendGridProvider implements EmailProvider {

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApi;

    @Override
    public void send(String email, String token) {
        SendGrid sg = new SendGrid(sendGridApi);
        Email from = new Email("schoolsendgrid@gmail.com");
        Email to = new Email(email);
        String subject = "Verify your email";
        System.out.println("token: " + token);
        var format = String.format("<a href=\"http://localhost:8080/usertokens/verifyuseremail/%s\">Verify your email</a>", token);
        Content content = new Content("text/html", format);

        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("SendGrid Api status code: " + response.getStatusCode());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            System.out.println(Arrays.toString(ioe.getStackTrace()));
        }
    }
}
