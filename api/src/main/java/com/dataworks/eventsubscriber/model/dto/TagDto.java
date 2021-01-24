package com.dataworks.eventsubscriber.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Component
@NoArgsConstructor
public class TagDto extends BaseDto {
    @NotNull
    private String name;
}
