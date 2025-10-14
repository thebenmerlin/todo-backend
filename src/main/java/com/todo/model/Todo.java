package com.todo.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "todos")
public class Todo {
    @Id
    private String id;

    private String title;
    private boolean completed;
    private Instant createdAt;

    public Todo() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = Instant.now();
    }

    public Todo(String title) {
        this();
        this.title = title;
        this.completed = false;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}