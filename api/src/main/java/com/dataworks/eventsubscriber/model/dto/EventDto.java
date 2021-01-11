package com.dataworks.eventsubscriber.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Component
@NoArgsConstructor
public class EventDto {
    @NotNull
    private int id;
    @NotNull
    private int categoryId;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Date date;
    @NotNull
    @Min(1)
    private int maxAmountOfAttendees;
    @NotNull
    @Min(0)
    private double euroAmount;
    @NotNull
    private String imageUrl;
    @JsonIgnoreProperties("events")
    private UserDto user;
}
