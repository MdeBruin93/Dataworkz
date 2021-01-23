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
public class CategoryDto extends BaseDto {
    @NotNull
    private String name;
    @NotNull
    private String color;
    private boolean deleted;
}
