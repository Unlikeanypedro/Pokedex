import { requisicao } from "./httpClient";

/**
 * Acesso ao endpoint de treinadores.
 *
 * Cada treinador já vem com o time achatado no formato que o TrainerCard
 * espera: { id, name, badges, pokemonIds }.
 */
export function listarTreinadores({ signal } = {}) {
    return requisicao("/api/trainers", { signal });
}
