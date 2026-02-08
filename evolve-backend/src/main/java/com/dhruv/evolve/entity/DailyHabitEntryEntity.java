package com.dhruv.evolve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(
        name = "tbl_daily_habits",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "habit_id", "date"})}
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyHabitEntryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private HabitEntity habit;

    @Column(nullable = false)
    LocalDate date;

    @Column(nullable = false)
    Boolean completed;

    @Column(nullable = false)
    Boolean processed;

    @PrePersist
    public void prePersist() {
        if (this.completed == null) {
            this.completed = false;
        }
        if (this.processed == null) {
            this.processed = false;
        }
    }
}
