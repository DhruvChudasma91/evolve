package com.dhruv.evolve.service;

import com.dhruv.evolve.dto.UserScoreDTO;
import com.dhruv.evolve.entity.DailyHabitEntryEntity;
import com.dhruv.evolve.entity.UserEntity;
import com.dhruv.evolve.entity.UserScoreEntity;
import com.dhruv.evolve.repository.DailyHabitRepository;
import com.dhruv.evolve.repository.UserScoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserScoreService {

    private final DailyHabitRepository dailyHabitRepository;
    private final UserService userService;
    private final UserScoreRepository userScoreRepository;

    @Transactional
    public void processEndOfDay() {

        LocalDate yesterday = LocalDate.now().minusDays(1);

        List<DailyHabitEntryEntity> entries =
                dailyHabitRepository.findByDateAndProcessedFalse(yesterday);

        for (DailyHabitEntryEntity entry : entries) {

            UserEntity user = entry.getUser();

            UserScoreEntity score = getOrCreateUserScore(user);

            // Get current points safely
            int discipline = score.getDisciplinePoints() != null
                    ? score.getDisciplinePoints()
                    : 0;

            int comfort = score.getComfortPoints() != null
                    ? score.getComfortPoints()
                    : 0;

            // Apply scoring logic
            if (Boolean.TRUE.equals(entry.getCompleted())) {
                discipline += 25;
            } else {
                comfort += 50;
            }

            // Update points
            score.setDisciplinePoints(discipline);
            score.setComfortPoints(comfort);

            // 🔥 Update ranks based on new points
            score.setUserRank(calculateDisciplineRank(discipline));
            score.setOpponentRank(calculateComfortRank(comfort));

            // Mark entry as processed
            entry.setProcessed(true);

            // Save both
            userScoreRepository.save(score);
            dailyHabitRepository.save(entry);
        }
    }


    public UserScoreDTO getCurrentUserScore() {

        UserEntity user = userService.getCurrentUser();

        UserScoreEntity score = getOrCreateUserScore(user);

        int discipline = score.getDisciplinePoints() != null ? score.getDisciplinePoints() : 0;
        int comfort = score.getComfortPoints() != null ? score.getComfortPoints() : 0;

        return UserScoreDTO.builder()
                .disciplinePoints(discipline)
                .comfortPoints(comfort)
                .disciplineRank(calculateDisciplineRank(discipline))
                .comfortRank(calculateComfortRank(comfort))
                .build();
    }

    private UserScoreEntity getOrCreateUserScore(UserEntity user) {

        return userScoreRepository.findByUserId(user.getId())
                .orElseGet(() -> {

                    UserScoreEntity newScore = UserScoreEntity.builder()
                            .user(user)
                            .disciplinePoints(0)
                            .comfortPoints(0)
                            .userRank(calculateDisciplineRank(0))
                            .opponentRank(calculateComfortRank(0))
                            .build();
                    return userScoreRepository.save(newScore);
                });
    }

    private String calculateDisciplineRank(int points) {

        if (points < 100) return "Beginner";
        if (points < 300) return "Consistent";
        if (points < 700) return "Focused";
        if (points < 1500) return "Elite";
        return "Unstoppable";
    }

    private String calculateComfortRank(int points) {

        if (points < 100) return "Weak";
        if (points < 300) return "Growing";
        if (points < 700) return "Dangerous";
        if (points < 1500) return "Dominating";
        return "Overpowering";
    }
}
