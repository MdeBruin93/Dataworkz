package com.dataworks.eventsubscriber.service.token;

import com.dataworks.eventsubscriber.enums.TokenType;
import com.dataworks.eventsubscriber.mapper.UserTokenMapper;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenService extends UserTokenService {
    public ResetPasswordTokenService(
            UserRepository userRepository,
            UserTokenRepository userTokenRepository,
            UserTokenMapper userTokenMapper
    ) {
        super(userRepository, userTokenRepository, userTokenMapper);
        setTokenType(TokenType.PasswordReset);
    }

    @Override
    public TokenDto generate() {
        var token = super.generate();

        //mail logic

        return token;
    }
}
