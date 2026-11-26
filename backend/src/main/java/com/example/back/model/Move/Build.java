package com.example.back.model.Move;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento salvo na coleção "pokemon_builds" do MongoDB.
 *
 * Representa uma build recomendada para um Pokémon
 * em determinado jogo.
 *
 * Exemplo:
 *
 * {
 *   "_id": "pikachu-fire-red-offensive",
 *   "pokemonId": "25",
 *   "gameId": "fire-red",
 *   "nome": "Ofensiva",
 *   "descricao": "Build focada em causar dano...",
 *   "moves": ["85", "24", "86", "91"]
 * }
 */

@Document(collection = "pokemon_builds")
public class Build {

    @Id
    private String id;

    private String pokemonId;

    private String gameId;

    private String nome;

    private String descricao;

    private String estilo;

    private List<String> moves;

    // Construtor vazio
    public Build() {}

    // Construtor completo
    public Build(
        String id,
        String pokemonId,
        String gameId,
        String nome,
        String descricao,
        String estilo,
        List<String> moves
    ) {
        this.id = id;
        this.pokemonId = pokemonId;
        this.gameId = gameId;
        this.nome = nome;
        this.descricao = descricao;
        this.estilo = estilo;
        this.moves = moves;
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

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public List<String> getMoves() {
        return moves;
    }

    public void setMoves(List<String> moves) {
        this.moves = moves;
    }
}
