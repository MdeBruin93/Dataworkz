package com.dataworks.eventsubscriber.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
