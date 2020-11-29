package com.dataworks.eventsubscriber.service;

import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.mapper.UserTokenMapper;
import com.dataworks.eventsubscriber.model.dao.TokenType;
import com.dataworks.eventsubscriber.model.dao.UserToken;
import com.dataworks.eventsubscriber.model.dto.UserTokenDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@RequiredArgsConstructor
public class UserTokenService implements TokenService {

    UserRepository userRepository;
    UserTokenRepository userTokenRepository;
    UserTokenMapper userTokenMapper;

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApi;

    @Override
    public UserTokenDto createEmailTokenForUser(String email) {
        var user = userRepository.findByEmail(email).get();

        var token = email + "-split-" + UUID.randomUUID();
        var userToken = new UserToken();
        userToken.setUserId(user.getId());
        userToken.setToken(token);
        userToken.setType(TokenType.EmailConfirmation);
        userTokenRepository.save(userToken);
        sendSendGridEmail(email, Arrays.toString(Base64.getEncoder().encode(token.getBytes())));
        return userTokenMapper.mapToDestination(userToken);
    }

    @Override
    public boolean verifyEmailTokenForUser(String token) {
        var tmp = Arrays.toString(Base64.getDecoder().decode(token.getBytes()));
//        var splitted = token.split("\\-split-");
//        var user = userRepository.findByEmail(splitted[0]);
//
//        if (user == null) {
//            throw new UserNotFoundException(splitted[0]);
//        }
//        var foundToken = userTokenRepository.findByToken(splitted[1], TokenType.EmailConfirmation);
//        if (foundToken == null) {
//             throw new UserTokenNotFoundException();
//        }
        return true;
    }

    private void sendSendGridEmail(String email, String token) {

        SendGrid sg = new SendGrid(sendGridApi);
        Email from = new Email("noreply@eventsubscriber.com");
        Email to = new Email(email);
        String subject = "Verify your email";
        var format = String.format("<a href=\"http://localhost:8080/usertokens/verifyuseremail/%s\">Verify your email</a>", token);
        Content content = new Content("text/html", format);

        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            System.out.println(Arrays.toString(ioe.getStackTrace()));
        }
    }
}
