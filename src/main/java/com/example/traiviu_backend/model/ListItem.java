package com.example.traiviu_backend.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "list_items",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_list_items_unique_movie",
                columnNames = {"list_id", "tmdb_id", "media_type"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListItem {

    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "list_id")
    private ListEntity list;

    @Column(nullable = false)
    private Integer tmdbId;

    @Column(nullable = false, length = 10)
    private String mediaType; // movie | tv

    @Column(nullable = false)
    private Instant addedAt = Instant.now();

    private Instant watchedAt;
}