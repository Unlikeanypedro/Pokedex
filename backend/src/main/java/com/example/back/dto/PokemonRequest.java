package com.example.back.dto;

import com.example.back.model.Pokemon.PokemonStats;
import java.util.List;

/**
 * Corpo aceito no POST e no PUT de /api/pokemons.
 *
 * Os tipos chegam como objetos ({ "id": "5", "name": "Electric" }), o mesmo
 * formato que a API devolve — assim o front reenvia exatamente o que recebeu.
 */
public record PokemonRequest(String name, PokemonStats stats, List<TypeRef> types) {}
