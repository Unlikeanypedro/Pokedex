package com.example.back.model.Move;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento salvo na coleção "moves" do MongoDB.
 *
 * Cada movimento possui:
 * - ID;
 * - Nome;
 * - Tipo do movimento;
 * - Categoria;
 * - Poder;
 * - Precisão;
 * - Quantidade de PP;
 * - Efeito adicional.
 *
 * Exemplo de como fica no MongoDB Atlas:
 *
 * {
 *   "_id": "85",
 *   "nome": "Thunderbolt",
 *   "type": 5,
 *   "category": "SPECIAL",
 *   "power": 90,
 *   "accuracy": 100,
 *   "pp": 15,
 *   "effect": "Pode causar paralisia"
 * }
 */

@Document(collection = "moves")
public class Move {

    @Id
    private String id;

    private String nome;

    // ID do tipo do movimento
    private int type;

    // PHYSICAL, SPECIAL ou STATUS
    private String category;

    private int power;

    private int accuracy;

    private int pp;

    private String effect;

    // Construtor vazio: usado pelo Spring/Jackson
    public Move() {}

    // Construtor completo
    public Move(
        String id,
        String nome,
        int type,
        String category,
        int power,
        int accuracy,
        int pp,
        String effect
    ) {
        this.id = id;
        this.nome = nome;
        this.type = type;
        this.category = category;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
        this.effect = effect;
    }

    // Getters e Setters

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
