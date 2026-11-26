import { requisicao } from "./httpClient";

/**
 * Lista os tipos cadastrados ({ id, name }), usados no filtro e no formulário.
 */
export function listarTipos({ signal } = {}) {
    return requisicao("/api/types", { signal });
}
