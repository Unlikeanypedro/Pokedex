package com.example.back.repository;

import com.example.back.model.Trainer.TrainerTeam;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerTeamRepository extends MongoRepository<TrainerTeam, String> {
    List<TrainerTeam> findByTrainerId(String trainerId);
}
