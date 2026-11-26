package com.example.back.controller;

import com.example.back.dto.PokemonRequest;
import com.example.back.dto.PokemonResponse;
import com.example.back.service.PokemonService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints REST do CRUD de pokémons. É aqui que o front-end React "bate".
 *
 * | Método | Endpoint                | O que faz            | Resposta  |
 * | ------ | ----------------------- | -------------------- | --------- |
 * | GET    | /api/pokemons           | Lista todos          | 200       |
 * | GET    | /api/pokemons?name=pika | Busca pelo nome      | 200       |
 * | GET    | /api/pokemons/{id}      | Busca um pokémon     | 200 / 404 |
 * | POST   | /api/pokemons           | Cadastra             | 201 / 400 |
 * | PUT    | /api/pokemons/{id}      | Atualiza             | 200 / 404 |
 * | DELETE | /api/pokemons/{id}      | Exclui               | 204 / 404 |
 */
@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    // http://localhost:8080/api/pokemons
    // http://localhost:8080/api/pokemons?name=pikachu
    @GetMapping
    public List<PokemonResponse> listar(
        @RequestParam(required = false) String name
    ) {
        return pokemonService.listar(name);
    }

    // http://localhost:8080/api/pokemons/{id}
    @GetMapping("/{id}")
    public PokemonResponse buscarPorId(@PathVariable String id) {
        return pokemonService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<PokemonResponse> inserir(
        @RequestBody PokemonRequest pokemon
    ) {
        PokemonResponse criado = pokemonService.inserir(pokemon);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public PokemonResponse atualizar(
        @PathVariable String id,
        @RequestBody PokemonRequest pokemon
    ) {
        return pokemonService.atualizar(id, pokemon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        pokemonService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
