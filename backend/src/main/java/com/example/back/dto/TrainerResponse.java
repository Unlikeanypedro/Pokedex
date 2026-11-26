package com.example.back.dto;

import java.util.List;

/**
 * Treinador devolvido pela API, com o time já achatado (pokemonIds), no formato
 * que a interface da Atividade 03 espera.
 */
public record TrainerResponse(String id, String name, int badges, List<String> pokemonIds) {}
