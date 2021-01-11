package com.dataworks.eventsubscriber.model.dao;

import com.dataworks.eventsubscriber.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User extends BaseDao {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private boolean emailVerified;
    @NotNull
    private String role;
    @OneToMany(mappedBy = "user")
    private List<Event> events;
    @OneToMany(mappedBy = "user")
    private List<Wishlist> wishlists;
    @ManyToMany
    @JoinTable(
            name = "participant",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> subscribedEvents;
    @OneToMany(mappedBy = "user")
    private List<UserToken> tokens;

    public boolean isAdmin() {
        return this.getRole()
                .equals(Role.ROLE_ADMIN.toString());
    }
}
