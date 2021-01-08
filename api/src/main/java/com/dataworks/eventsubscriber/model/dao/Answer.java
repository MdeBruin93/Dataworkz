package com.dataworks.eventsubscriber.model.dao;

import javax.persistence.ManyToOne;

public class Answer extends BaseDao {
    private String text;
    @ManyToOne()
    private Event event;
}
