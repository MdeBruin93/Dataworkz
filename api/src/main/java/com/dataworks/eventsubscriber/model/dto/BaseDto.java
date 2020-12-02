package com.dataworks.eventsubscriber.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Component
@Getter
@Setter
public class BaseDto {
    @NotNull
    private int id;
    @Version
    @Getter
    private Integer version;

    @CreationTimestamp
    @Getter
    private LocalDateTime created;

    @UpdateTimestamp
    @Getter
    private LocalDateTime lastModified;
}
