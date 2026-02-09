package com.dhruv.evolve.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserScoreDTO {

    private Integer disciplinePoints;
    private Integer comfortPoints;
    private String disciplineRank;
    private String comfortRank;
}
