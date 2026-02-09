package com.dhruv.evolve.controller;

import com.dhruv.evolve.dto.UserScoreDTO;
import com.dhruv.evolve.service.UserScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
@RequiredArgsConstructor
public class UserScoreController {

    private final UserScoreService userScoreService;

    @GetMapping
    public ResponseEntity<UserScoreDTO> getCurrentUserScore() {
        return ResponseEntity.ok(userScoreService.getCurrentUserScore());
    }

    @PostMapping
    public ResponseEntity<String> processEndOfDay() {
        userScoreService.processEndOfDay();
        return ResponseEntity.ok("End-of-day scoring processed successfully");
    }
}
