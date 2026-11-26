import { requisicao } from "./httpClient";

/**
 * Acesso ao endpoint de treinadores.
 *
 * Cada treinador já vem com o time achatado no formato que o TrainerCard
 * espera: { id, name, badges, pokemonIds }.
 *
 * Ainda não está ligado na tela: o App continua lendo o JSON local.
 */
export function listarTreinadores() {
    return requisicao("/api/trainers");
}
