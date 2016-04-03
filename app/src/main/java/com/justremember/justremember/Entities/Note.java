package com.justremember.justremember.Entities;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by dimko_000 on 03.04.2016.
 */
public class Note {
    private long id;
    private User user;
    private String name;
    private String content;
    private long important;

    public Note(long id, User user, String name, String content, long important) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.content = content;
        this.important = important;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getImportant() {
        return important;
    }

    public void setImportant(long important) {
        this.important = important;
    }

}
