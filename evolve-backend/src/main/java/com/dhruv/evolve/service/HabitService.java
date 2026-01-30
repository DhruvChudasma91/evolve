package com.dhruv.evolve.service;

import com.dhruv.evolve.dto.HabitDTO;
import com.dhruv.evolve.entity.HabitEntity;
import com.dhruv.evolve.entity.UserEntity;
import com.dhruv.evolve.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final UserService userService;
    private final HabitRepository habitRepository;

    public HabitDTO createHabit(HabitDTO habitDTO) {
        UserEntity user = userService.getCurrentUser();

        if(habitRepository.existsByTitleAndUserId(habitDTO.getTitle(), user.getId())) {
            throw new RuntimeException("Habit with this title already exists");
        }

        HabitEntity newHabit = toEntity(habitDTO, user);
        newHabit = habitRepository.save(newHabit);
        return toDTO(newHabit);
    }

    private HabitEntity toEntity(HabitDTO habitDTO, UserEntity user) {
        return HabitEntity.builder()
                .title(habitDTO.getTitle())
                .description(habitDTO.getDescription())
                .hasSections(habitDTO.getHasSections() != null ? habitDTO.getHasSections() : false)
                .active(true)
                .user(user)
                .build();
    }

    public HabitDTO toDTO(HabitEntity habitEntity) {
        return HabitDTO.builder()
                .id(habitEntity.getId())
                .title(habitEntity.getTitle())
                .description(habitEntity.getDescription())
                .hasSections(habitEntity.getHasSections())
                .active(habitEntity.getActive())
                .createdAt(habitEntity.getCreatedAt())
                .updatedAt(habitEntity.getUpdatedAt())
                .build();

    }
}
