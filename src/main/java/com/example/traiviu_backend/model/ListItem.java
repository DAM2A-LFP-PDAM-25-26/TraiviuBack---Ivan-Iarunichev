package com.example.traiviu_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "list_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    @JsonIgnore
    private ListEntity list;

    @Column(nullable = false)
    private String externalApiId;

    @Column(nullable = false)
    private String title;

    private String year;

    private String posterUrl;

    @Column(nullable = false)
    private Instant addedAt;
}