package com.dhruv.evolve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user_score")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private Integer disciplinePoints;

    private Integer comfortPoints;

    @Column(nullable = false)
    private String userRank;

    @Column(nullable = false)
    private String opponentRank;
}
