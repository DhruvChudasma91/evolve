package com.dhruv.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitDTO {

    private Long id;
    private String title;
    private String description;
    private Boolean hasSections;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
