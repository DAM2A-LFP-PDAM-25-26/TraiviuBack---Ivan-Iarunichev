package com.example.traiviu_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "clan_members",
        uniqueConstraints = @UniqueConstraint(columnNames = {"clanId", "userId"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClanMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String clanId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String roleInClan; // OWNER, MEMBER
}
