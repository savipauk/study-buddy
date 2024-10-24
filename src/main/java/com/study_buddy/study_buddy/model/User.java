package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Ensure it's not a reserved keyword like "user"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String description;

    // Other fields, constructors, getters, setters
    public User() {}

    public User(String description) {
        this.description = description;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

