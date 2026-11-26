package com.example.back.controller;

import com.example.back.model.Pokemon.Pokemon;
import com.example.back.service.PokemonService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints REST do CRUD de pokemons. É aqui que o front-end React "bate".
 *
 * | Método | Endpoint                  | O que faz                      | Resposta  |
 * | ------ | ------------------------- | ------------------------------ | --------- |
 * | GET    | /api/pokemons              | Lista todos                    | 200       |
 * | GET    | /api/pokemons?nome=pikachu | Busca pelo nome                | 200       |
 * | GET    | /api/pokemons/{id}         | Busca um aluno                 | 200 / 404 |
 * | POST   | /api/pokemons              | Cadastra                       | 201 / 400 |
 * | PUT    | /api/pokemons/{id}         | Atualiza                       | 200 / 404 |
 * | DELETE | /api/pokemons/{id}         | Exclui                         | 204 / 404 |
 */
@RestController
@RequestMapping("/api/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    // http://localhost:8080/api/pokemons
    // http://localhost:8080/api/pokemons?nome=pikachu
    @GetMapping
    public List<Pokemon> listar(@RequestParam(required = false) String nome) {
        return pokemonService.listar(nome);
    }

    // http://localhost:8080/api/alunos/{id}
    @GetMapping("/{id}")
    public Pokemon buscarPorId(@PathVariable String id) {
        return pokemonService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Pokemon> inserir(@RequestBody Pokemon pokemon) {
        Pokemon criado = pokemonService.inserir(pokemon);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public Pokemon atualizar(
        @PathVariable String id,
        @RequestBody Pokemon pokemon
    ) {
        return pokemonService.atualizar(id, pokemon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        pokemonService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
