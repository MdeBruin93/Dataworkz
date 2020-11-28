package com.dataworks.eventsubscriber.model.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class User extends BaseDao {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean emailVerified;
    private String role;
}
