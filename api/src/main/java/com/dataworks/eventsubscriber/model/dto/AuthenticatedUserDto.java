package com.dataworks.eventsubscriber.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@Getter
@Setter
public class AuthenticatedUserDto extends UserDto {
    @NotNull
    private String password;
}
