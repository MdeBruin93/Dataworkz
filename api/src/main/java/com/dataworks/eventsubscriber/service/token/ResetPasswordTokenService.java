package com.dataworks.eventsubscriber.service.token;

import com.dataworks.eventsubscriber.enums.TokenType;
import com.dataworks.eventsubscriber.mapper.UserTokenMapper;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import com.dataworks.eventsubscriber.service.email.EmailProvider;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenService extends UserTokenService {
    @Getter
    @Setter
    private EmailProvider emailProvider;
    @Value("${spring.client.host}")
    private String host;

    public ResetPasswordTokenService(
            UserRepository userRepository,
            UserTokenRepository userTokenRepository,
            UserTokenMapper userTokenMapper,
            EmailProvider emailProvider
    ) {
        super(userRepository, userTokenRepository, userTokenMapper);
        this.setEmailProvider(emailProvider);
        setTokenType(TokenType.PasswordReset);
    }

    @Override
    public TokenDto generate() {
        var token = super.generate();
        var content = String.format("<a href=" + host + "/reset-password/%s>Reset your password</a>", token.getToken());

        emailProvider.setEmail(this.getEmail())
                .setSubject("Reset your password")
                .setContent(content)
                .send();

        return token;
    }
}
