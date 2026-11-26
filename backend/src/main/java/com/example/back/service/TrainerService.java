package com.example.back.service;

import com.example.back.dto.TrainerResponse;
import com.example.back.model.Trainer.Trainer;
import com.example.back.model.Trainer.TrainerTeam;
import com.example.back.repository.TrainerRepository;
import com.example.back.repository.TrainerTeamRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/** Junta o treinador com o seu time (coleção "trainer_teams"). */
@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerTeamRepository trainerTeamRepository;

    public TrainerService(
        TrainerRepository trainerRepository,
        TrainerTeamRepository trainerTeamRepository
    ) {
        this.trainerRepository = trainerRepository;
        this.trainerTeamRepository = trainerTeamRepository;
    }

    public List<TrainerResponse> listar() {
        // Um time por treinador: carrega todos e indexa por trainerId
        Map<String, List<String>> times = new HashMap<>();
        for (TrainerTeam time : trainerTeamRepository.findAll()) {
            times.put(time.getTrainerId(), time.getPokemonIds());
        }

        return trainerRepository
            .findAll(Sort.by("name"))
            .stream()
            .map(trainer ->
                new TrainerResponse(
                    trainer.getId(),
                    trainer.getName(),
                    trainer.getBadges(),
                    times.getOrDefault(trainer.getId(), List.of())
                )
            )
            .toList();
    }
}
