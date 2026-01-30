package com.dhruv.evolve.controller;

import com.dhruv.evolve.dto.HabitDTO;
import com.dhruv.evolve.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/habits")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @PostMapping
    public ResponseEntity<HabitDTO> createHabit(@RequestBody HabitDTO habitDTO) {

        HabitDTO savedHabit = habitService.createHabit(habitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHabit);
    }
}
