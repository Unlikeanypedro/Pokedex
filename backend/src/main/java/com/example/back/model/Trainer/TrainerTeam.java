package com.example.back.model.Trainer;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento salvo na coleção "trainer_teams" do MongoDB.
 *
 * Representa o time de um treinador.
 *
 * Cada time possui:
 * - ID;
 * - ID do treinador;
 * - Lista de IDs dos Pokémon que fazem parte do time;
 *
 * Exemplo de como fica no MongoDB Atlas:
 *
 * {
 *   "_id": "1",
 *   "trainerId": "1",
 *   "pokemonIds": [
 *       "6",
 *       "25",
 *       "9",
 *       "65",
 *       "143",
 *       "149"
 *   ]
 * }
 */
@Document(collection = "trainer_teams")
public class TrainerTeam {

    @Id
    private String id;

    private String trainerId;

    private List<String> pokemonIds;

    public TrainerTeam() {}

    public TrainerTeam(String id, String trainerId, List<String> pokemonIds) {
        this.id = id;
        this.trainerId = trainerId;
        this.pokemonIds = pokemonIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public List<String> getPokemonIds() {
        return pokemonIds;
    }

    public void setPokemonIds(List<String> pokemonIds) {
        this.pokemonIds = pokemonIds;
    }
}
