package com.example.frontendandroid.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

// PUBLIC_INTERFACE
public class Note implements Serializable {
    /** Model for a single Note. */
    private String id;
    private String title;
    private String content;
    private long createdAt;
    private long updatedAt;

    public Note(String title, String content) {
        this.id = UUID.randomUUID().toString();
        this.title = title == null ? "" : title;
        this.content = content == null ? "" : content;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
    }
    public Note(String id, String title, String content, long createdAt, long updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = System.currentTimeMillis();
    }
    public String getContent() { return content; }
    public void setContent(String content) {
        this.content = content;
        this.updatedAt = System.currentTimeMillis();
    }
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
