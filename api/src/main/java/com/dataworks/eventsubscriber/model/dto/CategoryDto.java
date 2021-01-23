package com.dataworks.eventsubscriber.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Component
@NoArgsConstructor
public class CategoryDto extends BaseDto {
    @NotNull
    private String name;
    @NotNull
    private String color;
    private LocalDate endDate;
    private boolean deleted;
}
