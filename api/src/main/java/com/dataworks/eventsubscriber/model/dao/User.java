package com.dataworks.eventsubscriber.model.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

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
}
