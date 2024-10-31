package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(length = 255)
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Gender {
        M, F, OTHER
    }

    // Constructors, getters, setters
}
