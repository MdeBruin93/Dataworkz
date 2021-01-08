package com.dataworks.eventsubscriber.model.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Answer extends BaseDao {
    private String text;
    @ManyToOne()
    private Question question;
    @ManyToOne()
    private User owner;
}
