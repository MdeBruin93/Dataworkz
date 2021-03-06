package com.dataworks.eventsubscriber.service.token;

import com.dataworks.eventsubscriber.enums.TokenType;
import com.dataworks.eventsubscriber.mapper.UserTokenMapper;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import com.dataworks.eventsubscriber.service.email.EmailProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
        var tokenDto = super.generate();
        String safeUrlToken = null;
        try {
            safeUrlToken = URLEncoder.encode(tokenDto.getToken(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        var content = String.format("<a href=" + host + "/reset-password/%s>Reset your password</a>", safeUrlToken);

        emailProvider.setEmail(this.getEmail())
                .setSubject("Reset your password")
                .setContent(content)
                .send();

        return tokenDto;
    }
}
