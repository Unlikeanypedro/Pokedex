package com.example.back.dto;

/**
 * Referência resumida de um tipo, no formato consumido pelo front-end.
 *
 * O MongoDB guarda apenas o ID do tipo dentro do Pokémon; este record é usado
 * para devolver (e receber) o tipo já com o nome, sem o front precisar fazer
 * uma segunda requisição.
 */
public record TypeRef(String id, String name) {}
