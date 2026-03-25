package com.example.traiviu_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String type = "DEFAULT"; // DEFAULT | CUSTOM

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @JsonIgnore
    @OneToMany(mappedBy = "list")
    private List<ListItem> items;
}