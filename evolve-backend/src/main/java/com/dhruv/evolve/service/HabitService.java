package com.dhruv.evolve.service;

import com.dhruv.evolve.dto.HabitDTO;
import com.dhruv.evolve.entity.HabitEntity;
import com.dhruv.evolve.entity.UserEntity;
import com.dhruv.evolve.exception.ConflictException;
import com.dhruv.evolve.exception.ResourceNotFoundException;
import com.dhruv.evolve.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final UserService userService;
    private final HabitRepository habitRepository;

    public HabitDTO saveHabit(HabitDTO habitDTO) {
        UserEntity user = userService.getCurrentUser();

        if(habitRepository.existsByTitleAndUserId(habitDTO.getTitle(), user.getId())) {
            throw new ConflictException("Habit with this title already exists");
        }

        HabitEntity newHabit = toEntity(habitDTO, user);
        newHabit = habitRepository.save(newHabit);
        return toDTO(newHabit);
    }

    private HabitEntity toEntity(HabitDTO habitDTO, UserEntity user) {

        return HabitEntity.builder()
                .title(habitDTO.getTitle())
                .description(habitDTO.getDescription())
                .user(user)
                .build();
    }

    public HabitDTO toDTO(HabitEntity habitEntity) {
        return HabitDTO.builder()
                .id(habitEntity.getId())
                .title(habitEntity.getTitle())
                .description(habitEntity.getDescription())
                .userId(habitEntity.getUser() != null ? habitEntity.getUser().getId() : null)
                .build();
    }

    public List<HabitDTO> getHabitsForCurrentUser() {
        UserEntity user = userService.getCurrentUser();
        List<HabitEntity> habits = habitRepository.findByUserId(user.getId());

        return habits.stream().map(this::toDTO).toList();
    }

    public HabitDTO updateHabit(Long habitId, HabitDTO habitDTO) {

        UserEntity user = userService.getCurrentUser();

        HabitEntity existingHabit = habitRepository.findByIdAndUserId(habitId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found or not accessible"));

        existingHabit.setTitle(habitDTO.getTitle());
        existingHabit.setDescription(habitDTO.getDescription());
        existingHabit = habitRepository.save(existingHabit);

        return toDTO(existingHabit);
    }

    public void removeHabit(Long habitId) {

        UserEntity user = userService.getCurrentUser();
        HabitEntity existingHabit = habitRepository.findByIdAndUserId(habitId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found or not accessible"));

        habitRepository.delete(existingHabit);
    }
}
