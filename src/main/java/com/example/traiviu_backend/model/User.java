package com.example.traiviu_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 100)
    private String displayName;

    private String avatarUrl;

    @Builder.Default
    @Column(nullable = false, length = 10)
    private String role = "USER"; // USER | ADMIN

    @Builder.Default
    @Column(nullable = false)
    private boolean blocked = false;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    private Instant lastLoginAt;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ListEntity> lists;
}