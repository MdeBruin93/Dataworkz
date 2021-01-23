package com.dataworks.eventsubscriber.model.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category extends BaseDao {
    @NotNull
    private String name;
    @NotNull
    private String color;
    @NotNull
    private boolean deleted;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Event> events;
}
