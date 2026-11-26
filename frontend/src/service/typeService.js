import { requisicao } from "./httpClient";

/**
 * Lista os tipos cadastrados ({ id, name }), usados no filtro e no formulário.
 *
 * Ainda não está ligado na tela: o App continua lendo o JSON local.
 */
export function listarTipos() {
    return requisicao("/api/types");
}
