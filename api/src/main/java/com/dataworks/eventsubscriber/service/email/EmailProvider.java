package com.dataworks.eventsubscriber.service.email;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@Accessors(chain = true)
public abstract class EmailProvider {
    private String email;
    private String subject;
    private String content;

    public abstract void send();
}
