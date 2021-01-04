package com.dataworks.eventsubscriber.model.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Wishlist extends BaseDao {
    private String name;
    @ManyToOne()
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "wishlist_event",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;
}