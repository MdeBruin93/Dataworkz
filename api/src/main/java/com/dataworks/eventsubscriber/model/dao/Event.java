package com.dataworks.eventsubscriber.model.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
    //@todo add annotation
    private Date date;
    @NotNull
    @Min(1)
    private int maxAmountOfAttendees;
    @NotNull
    @Min(0)
    private double euroAmount;
    @NotNull
    private String imageUrl;
    @ManyToOne
    private User user;
    @ManyToMany
    @JoinTable(
            name = "participant",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> subscribedUsers;
}
