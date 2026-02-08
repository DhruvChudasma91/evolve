package com.dhruv.evolve.repository;

import com.dhruv.evolve.entity.DailyHabitEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyHabitRepository extends JpaRepository<DailyHabitEntryEntity, Long> {

    boolean existsByUserIdAndHabitIdAndDate(Long userId, Long habitId, LocalDate date);

    List<DailyHabitEntryEntity> findByUserIdAndDate(Long userId, LocalDate date);

    Optional<DailyHabitEntryEntity> findByIdAndUserId(Long id, Long userId);

    List<DailyHabitEntryEntity> findByDateAndProcessedFalse(LocalDate date);
}
