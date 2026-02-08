package com.dhruv.evolve.controller;

import com.dhruv.evolve.dto.DailyHabitDTO;
import com.dhruv.evolve.service.DailyHabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/daily-habits")
@RequiredArgsConstructor
public class DailyHabitController {

    private final DailyHabitService dailyHabitService;
    @PostMapping("/{entryId}")
    public ResponseEntity<String> addHabitToToday(@PathVariable Long entryId) {
        dailyHabitService.addHabitToToday(entryId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Habit added to today's list");
    }
    @GetMapping
    public ResponseEntity<List<DailyHabitDTO>> getTodayHabits() {
        List<DailyHabitDTO> todayHabits = dailyHabitService.getTodayHabits();
        return ResponseEntity.ok(todayHabits);
    }
    @PutMapping("/{entryId}/complete")
    public ResponseEntity<String> markCompleted(@PathVariable Long entryId) {
        dailyHabitService.markCompleted(entryId);
        return ResponseEntity.ok("Habit marked as completed");
    }

}
