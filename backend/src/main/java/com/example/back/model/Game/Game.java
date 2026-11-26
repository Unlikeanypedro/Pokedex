package com.example.back.model.Game;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento salvo na coleção "games" do MongoDB.
 *
 * Representa um jogo ou formato da franquia Pokémon.
 *
 * Cada jogo possui:
 * - ID;
 * - Nome;
 * - Geração;
 * - Plataforma;
 * - Tipo de mídia.
 *
 * Exemplo de como fica no MongoDB Atlas:
 *
 * {
 *   "_id": "fire-red",
 *   "nome": "Pokémon FireRed",
 *   "geracao": 3,
 *   "plataforma": "Game Boy Advance",
 *   "tipo": "GAME"
 * }
 */
@Document(collection = "games")
public class Game {

    @Id
    private String id;

    private String nome;

    private int geracao;

    private String plataforma;

    private String tipo;

    public Game() {}

    public Game(String id, String nome, int geracao, String plataforma, String tipo) {
        this.id = id;
        this.nome = nome;
        this.geracao = geracao;
        this.plataforma = plataforma;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getGeracao() {
        return geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
