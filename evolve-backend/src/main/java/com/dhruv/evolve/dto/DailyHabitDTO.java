package com.dhruv.evolve.dto;

import com.dhruv.evolve.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyHabitDTO {

    private Long id;
    private Long userId;
    private Long habitId;
    private LocalDate date;
    private Boolean completed;
    private Boolean processed;

}
