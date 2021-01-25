package com.dataworks.eventsubscriber.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Date date;
    @NotNull
    @Min(1)
    private int maxAmountOfAttendees;
    @NotNull
    @Min(0)
    private double euroAmount;
    @NotNull
    private String imageUrl;
    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private List<Question> questions;
    @ManyToMany()
    @JoinTable(
            name = "event_tags",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
    @ManyToOne
    @JsonIgnoreProperties("events")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    @ManyToMany
    @JoinTable(
            name = "wishlist_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "wishlist_id"))
    private List<Wishlist> wishlists;
    @ManyToMany
    @JoinTable(
            name = "participant",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> subscribedUsers;

}
