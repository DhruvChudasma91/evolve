package com.dhruv.evolve.repository;

import com.dhruv.evolve.entity.UserScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScoreEntity, Long> {

    Optional<UserScoreEntity> findByUserId(Long userId);
}
