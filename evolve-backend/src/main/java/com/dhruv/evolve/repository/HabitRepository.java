package com.dhruv.evolve.repository;

import com.dhruv.evolve.entity.HabitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<HabitEntity, Long> {

    List<HabitEntity> findByUserId(Long userId);

    Optional<HabitEntity> findByIdAndUserId(Long id, Long userId);

    Boolean existsByTitleAndUserId(String title, Long userId);
}
