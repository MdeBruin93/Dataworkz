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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserTokenService implements TokenService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final UserTokenMapper userTokenMapper;

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApi;

    @Override
    public UserTokenDto createEmailTokenForUser(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        var token = UUID.randomUUID().toString();
        var encryptedToken = new String(Base64.getEncoder().encode((email + ":" + token).getBytes()));
        var userToken = new UserToken();
        userToken.setUserId(user.getId());
        userToken.setToken(token);
        userToken.setType(TokenType.EmailConfirmation);
        userTokenRepository.save(userToken);
        sendSendGridEmail(email, encryptedToken);
        return userTokenMapper.mapToDestination(userToken);
    }

    @Override
    public boolean verifyEmailTokenForUser(String token) {
        var decrypted = new String(Base64.getDecoder().decode(token.getBytes()));
        var splitted = decrypted.split("\\:");
        var user = userRepository.findByEmail(splitted[0]).orElseThrow(() -> new UserNotFoundException(splitted[0]));
        userTokenRepository.findByToken(splitted[1]).orElseThrow(UserTokenNotFoundException::new);

        user.setEmailVerified(true);
        userRepository.save(user);
        return true;
    }

    private void sendSendGridEmail(String email, String token) {

        SendGrid sg = new SendGrid(sendGridApi);
        Email from = new Email("0942185@hr.nl");
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
            System.out.println(response.getBody());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            System.out.println(Arrays.toString(ioe.getStackTrace()));
        }
    }
}
