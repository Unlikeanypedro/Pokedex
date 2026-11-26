package com.example.back.dto;

import com.example.back.model.Pokemon.PokemonStats;
import java.util.List;

/**
 * Pokémon devolvido pela API, com os tipos já resolvidos ({id, name}).
 */
public record PokemonResponse(String id, String name, PokemonStats stats, List<TypeRef> types) {}
