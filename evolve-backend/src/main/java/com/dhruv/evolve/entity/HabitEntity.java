package com.dhruv.evolve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "tbl_habits",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "title"})}
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean hasSessions;

    @Column(nullable = false)
    private Integer totalSessions;

    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @PrePersist
    public void prePersist() {

        if(this.hasSessions == null) {
            this.hasSessions = false;
        }

        if(this.totalSessions == null || this.totalSessions <= 0) {
            this.totalSessions = 1;
        }

        if(this.hasSessions && this.totalSessions == 1) {
            this.hasSessions = false;
        }

        if (this.totalSessions > 1) {
            this.hasSessions = true;
        }

        if(this.active == null) {
            this.active = true;
        }
    }
}
