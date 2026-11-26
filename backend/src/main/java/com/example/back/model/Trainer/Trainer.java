package com.example.back.model.Trainer;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento salvo na coleção "trainers" do MongoDB.
 *
 * Cada treinador possui:
 * - ID;
 * - Nome;
 * - Quantidade de insígnias.
 *
 * O time do treinador fica na coleção "trainer_teams" (TrainerTeam).
 *
 * Exemplo de como fica no MongoDB Atlas:
 *
 * {
 *   "_id": "1",
 *   "name": "Ash",
 *   "badges": 9
 * }
 */

@Document(collection = "trainers")
public class Trainer {

    @Id
    private String id;

    private String name;

    private int badges;

    // Construtor vazio: usado pelo Spring/Jackson
    public Trainer() {}

    // Construtor completo
    public Trainer(String id, String name, int badges) {
        this.id = id;
        this.name = name;
        this.badges = badges;
    }

    // Getters e Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBadges() {
        return badges;
    }

    public void setBadges(int badges) {
        this.badges = badges;
    }
}
