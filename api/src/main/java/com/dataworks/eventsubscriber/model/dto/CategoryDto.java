package com.dataworks.eventsubscriber.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Component
@NoArgsConstructor
public class CategoryDto extends BaseDto {
    @NotNull
    private String name;
    @NotNull
    private String color;

    @JsonIgnoreProperties("user")
    private List<EventDto> events;
}
