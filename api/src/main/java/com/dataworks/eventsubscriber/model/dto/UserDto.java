package com.dataworks.eventsubscriber.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
