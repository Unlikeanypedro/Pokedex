package com.example.back.controller;

import com.example.back.dto.TrainerResponse;
import com.example.back.service.TrainerService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** GET /api/trainers -> treinadores com insígnias e IDs do time. */
@RestController
@RequestMapping("/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    public List<TrainerResponse> listar() {
        return trainerService.listar();
    }
}
