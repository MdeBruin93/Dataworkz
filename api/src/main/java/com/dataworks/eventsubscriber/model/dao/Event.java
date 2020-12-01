package com.dataworks.eventsubscriber.model.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event extends BaseDao {
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Date date;
    @NotNull
    private int maxAmountOfAttendees;
    @NotNull
    private double euroAmount;
    @NotNull
    @ManyToOne
    private User user;
}
