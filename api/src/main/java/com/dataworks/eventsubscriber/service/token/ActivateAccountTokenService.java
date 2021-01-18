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
public class ActivateAccountTokenService extends UserTokenService {
    @Getter
    @Setter
    private EmailProvider emailProvider;
    @Value("${spring.client.reset-password}")
    private String host;

    public ActivateAccountTokenService(
            UserRepository userRepository,
            UserTokenRepository userTokenRepository,
            UserTokenMapper userTokenMapper,
            EmailProvider emailProvider
    ) {
        super(userRepository, userTokenRepository, userTokenMapper);
        this.setEmailProvider(emailProvider);
        setTokenType(TokenType.EmailConfirmation);
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
        var content = String.format("<a href=" + host + "/%s>Verify your email</a>", safeUrlToken);

        emailProvider.setEmail(this.getEmail())
                .setSubject("Activate your account!")
                .setContent(content)
                .send();

        return tokenDto;
    }
}
