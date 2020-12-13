package com.dataworks.eventsubscriber.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Component
@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordDto {
    @NotNull
    private String token;
    @NotNull
    private String newPassword;
    @NotNull
    private String repeatNewPassword;
}
