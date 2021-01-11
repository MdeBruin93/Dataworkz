package com.dataworks.eventsubscriber.model.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Question extends BaseDao {
    private String text;
    @ManyToOne()
    private Event event;
    @ManyToOne()
    private User owner;
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Answer> answers;
}
