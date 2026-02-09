package com.dhruv.evolve.service;

import com.dhruv.evolve.dto.DailyHabitDTO;
import com.dhruv.evolve.entity.DailyHabitEntryEntity;
import com.dhruv.evolve.entity.HabitEntity;
import com.dhruv.evolve.entity.UserEntity;
import com.dhruv.evolve.exception.ConflictException;
import com.dhruv.evolve.exception.ResourceNotFoundException;
import com.dhruv.evolve.repository.DailyHabitRepository;
import com.dhruv.evolve.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyHabitService {

    private final UserService userService;
    private final DailyHabitRepository dailyHabitRepository;
    private final HabitRepository habitRepository;


    public void addHabitToToday(Long habitId) {

        UserEntity user = userService.getCurrentUser();

        HabitEntity habit = habitRepository.findByIdAndUserId(habitId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found"));

        LocalDate today = LocalDate.now();

        if(dailyHabitRepository.existsByUserIdAndHabitIdAndDate(user.getId(), habitId, today)) {
            throw new ConflictException("Habit already added for today");
        }

        DailyHabitEntryEntity entry = DailyHabitEntryEntity.builder()
                .user(user)
                .habit(habit)
                .date(today)
                .completed(false)
                .processed(false)
                .build();

        dailyHabitRepository.save(entry);
    }

    public List<DailyHabitDTO> getTodayHabits() {

        UserEntity user = userService.getCurrentUser();
        List<DailyHabitEntryEntity> habits = dailyHabitRepository.findByUserIdAndDate(user.getId(), LocalDate.now());

        return habits.stream().map(this::toDTO).toList();
    }

    public void markCompleted(Long entryId) {
        UserEntity user = userService.getCurrentUser();

        DailyHabitEntryEntity entry = dailyHabitRepository.findByIdAndUserId(entryId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Daily habit not found"));

        entry.setCompleted(true);

        dailyHabitRepository.save(entry);
    }



    public DailyHabitDTO toDTO(DailyHabitEntryEntity entryEntity) {
        return DailyHabitDTO.builder()
                .id(entryEntity.getId())
                .userId(entryEntity.getUser().getId())
                .habitId(entryEntity.getHabit().getId())
                .date(entryEntity.getDate())
                .completed(entryEntity.getCompleted())
                .processed(entryEntity.getProcessed())
                .build();
    }

}
