package com.example.back.exception;

/**
 * Lançada quando o id informado não existe na coleção "pokemons".
 * O GlobalExceptionHandler transforma esta exceção em HTTP 404 (Not Found).
 */
public class PokemonNaoEncontradoException extends RuntimeException {

    public PokemonNaoEncontradoException(String id) {
        super("Pokémon com id " + id + " não encontrado.");
    }
}
