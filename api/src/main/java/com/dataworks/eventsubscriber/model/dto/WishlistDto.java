package com.dataworks.eventsubscriber.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@NoArgsConstructor
@Getter
@Setter
public class WishlistDto extends BaseDto {
    @NotNull
    private String name;
    private List<EventDto> events;
}