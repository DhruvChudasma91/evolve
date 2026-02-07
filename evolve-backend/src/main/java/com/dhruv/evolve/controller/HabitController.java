package com.dhruv.evolve.controller;

import com.dhruv.evolve.dto.HabitDTO;
import com.dhruv.evolve.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habits")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @PostMapping
    public ResponseEntity<HabitDTO> saveHabit(@RequestBody HabitDTO habitDTO) {

        HabitDTO savedHabit = habitService.saveHabit(habitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHabit);
    }

    @GetMapping
    public ResponseEntity<List<HabitDTO>> getHabits() {
        List<HabitDTO> habits = habitService.getHabitsForCurrentUser();
        return  ResponseEntity.ok(habits);
    }
}
