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
                .hasSessions(habitDTO.getHasSessions() != null && habitDTO.getHasSessions())
                .totalSessions(habitDTO.getTotalSessions() != null && habitDTO.getTotalSessions() > 0 ? habitDTO.getTotalSessions() : 1)
                .active(habitDTO.getActive() != null? habitDTO.getActive() : true)
                .user(user)
                .build();
    }

    public HabitDTO toDTO(HabitEntity habitEntity) {
        return HabitDTO.builder()
                .id(habitEntity.getId())
                .title(habitEntity.getTitle())
                .description(habitEntity.getDescription())
                .hasSessions(habitEntity.getHasSessions())
                .totalSessions(habitEntity.getTotalSessions())
                .active(habitEntity.getActive())
                .createdAt(habitEntity.getCreatedAt())
                .build();

    }
}
