import { requisicao } from "./httpClient";

/**
 * Acesso aos endpoints de Pokémon da API Spring Boot.
 *
 * Os tipos já vêm resolvidos do back-end no formato [{ id, name }], então o
 * retorno pode ser usado direto pelos componentes existentes (PokemonCard,
 * PokemonDetails, PokemonList), que esperam `name`, `stats` e `types`.
 */

/** Lista todos os Pokémon (ordem alfabética). Com `name`, filtra no back-end. */
export function listarPokemons({ name, signal } = {}) {
    const query = name ? `?name=${encodeURIComponent(name)}` : "";
    return requisicao(`/api/pokemons${query}`, { signal });
}

/** Busca um Pokémon pelo id (_id do MongoDB). */
export function buscarPokemon(id, { signal } = {}) {
    return requisicao(`/api/pokemons/${encodeURIComponent(id)}`, { signal });
}

/**
 * Cadastra um Pokémon.
 * @param {{ name: string, stats: object, types: { id: string, name: string }[] }} pokemon
 */
export function criarPokemon(pokemon) {
    return requisicao("/api/pokemons", {
        method: "POST",
        body: JSON.stringify(pokemon),
    });
}

/** Atualiza todos os campos editáveis de um Pokémon. */
export function atualizarPokemon(id, pokemon) {
    return requisicao(`/api/pokemons/${encodeURIComponent(id)}`, {
        method: "PUT",
        body: JSON.stringify(pokemon),
    });
}

/** Exclui um Pokémon. */
export function excluirPokemon(id) {
    return requisicao(`/api/pokemons/${encodeURIComponent(id)}`, {
        method: "DELETE",
    });
}
