package com.dataworks.eventsubscriber.model.dao;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

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
