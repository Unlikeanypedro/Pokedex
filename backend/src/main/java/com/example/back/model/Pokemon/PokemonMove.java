package com.example.back.model.Pokemon;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento salvo na coleção "pokemon_moves" do MongoDB.
 *
 * Representa um movimento que um Pokémon pode aprender
 * em determinado jogo.
 *
 * Exemplo:
 *
 * {
 *   "_id": "25-85-fire-red",
 *   "pokemonId": "25",
 *   "moveId": "85",
 *   "gameId": "fire-red",
 *   "method": "TM"
 * }
 */

@Document(collection = "pokemon_moves")
public class PokemonMove {

    @Id
    private String id;

    private String pokemonId;
    private String moveId;
    private String gameId;

    // LEVEL_UP, TM, HM, TUTOR, EGG, EVENT...
    private String method;

    // Usado quando o movimento é aprendido por nível
    private Integer level;

    // Construtor vazio
    public PokemonMove() {}

    // Construtor completo
    public PokemonMove(
        String id,
        String pokemonId,
        String moveId,
        String gameId,
        String method,
        Integer level
    ) {
        this.id = id;
        this.pokemonId = pokemonId;
        this.moveId = moveId;
        this.gameId = gameId;
        this.method = method;
        this.level = level;
    }

    // Getters e Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(String pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getMoveId() {
        return moveId;
    }

    public void setMoveId(String moveId) {
        this.moveId = moveId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
