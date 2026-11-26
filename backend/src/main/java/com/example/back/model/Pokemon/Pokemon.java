package com.example.back.model.Pokemon;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento salvo na coleção "pokemons" do MongoDB.
 *
 * Cada Pokémon possui:
 * - Nome;
 * - Estatísticas básicas (HP, ataque, defesa, ataque especial,
 *   defesa especial e velocidade);
 * - Lista de IDs dos seus tipos.
 *
 * Exemplo de como fica no MongoDB Atlas:
 * {
 *   "_id": "25",
 *   "name": "Pikachu",
 *   "stats": {
 *     "hp": 35,
 *     "attack": 55,
 *     "defense": 30,
 *     "specialAttack": 50,
 *     "specialDefense": 40,
 *     "speed": 90
 *   },
 *   "types": [5]
 * }
 */

@Document(collection = "pokemons")
public class Pokemon {

    @Id
    private String id;

    private String name;

    private PokemonStats stats;

    private List<Integer> types;

    // Construtor vazio: usado pelo Spring/Jackson para montar o objeto a partir do JSON
    public Pokemon() {}

    // Construtor completo
    public Pokemon(String id, String name, PokemonStats stats, List<Integer> types) {
        this.id = id;
        this.name = name;
        this.stats = stats;
        this.types = types;
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

    public PokemonStats getStats() {
        return stats;
    }

    public void setStats(PokemonStats stats) {
        this.stats = stats;
    }

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }
}
