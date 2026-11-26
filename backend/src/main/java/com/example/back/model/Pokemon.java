package com.example.back.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento salvo na coleção "pokemons" do MongoDB.
 *
 * Exemplo de como fica no Atlas:
 * { "_id": 1, "nome": "Pikachu", "tamanho": "70cm", "type": "Elétrico" }
 */
@Document(collection = "pokemons")
public class Pokemon {

    @Id
    private String id;

    private String nome;
    private int health;
    private int attack;
    private int defense;
    private int special_Attack;
    private int special_Defense;
    private int speed;

    // Construtor vazio: usado pelo Spring/Jackson para montar o objeto a partir do JSON
    public Pokemon() {}

    public Pokemon(
        String id,
        String nome,
        int health,
        int attack,
        int defense,
        int special_Attack,
        int special_Defense,
        int speed
    ) {
        this.id = id;
        this.nome = nome;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.special_Attack = special_Attack;
        this.special_Defense = special_Defense;
        this.speed = speed;
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

    public int getHeath() {
        return this.health;
    }

    public void setHeath(int health) {
        this.health = health;
    }

    public int getAttack() {
        return this.attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return this.defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpecialAttack() {
        return this.special_Attack;
    }

    public void setSpecialAttack(int special_Attack) {
        this.special_Attack = special_Attack;
    }

    public int getSpecialDefense() {
        return this.special_Defense;
    }

    public void setSpecialDefense(int special_Defense) {
        this.special_Defense = special_Defense;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
