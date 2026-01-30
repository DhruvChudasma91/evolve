package com.dhruv.evolve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Boolean hasSections;

    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @PrePersist
    public void prePersist() {
        if (this.active == null) {
            this.active = true;
        }
        if (this.hasSections == null) {
            this.hasSections = false;
        }
    }


}
