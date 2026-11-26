import { useEffect, useRef, useState } from "react";
import { statLabels } from "../utils/pokemonStats.js";

export default function PokemonForm({ pokemon, tipos, onSave, onClose }) {
    const dialog = useRef(null);
    const [salvando, setSalvando] = useState(false);
    const [erro, setErro] = useState("");
    const tiposIniciais = new Set(pokemon?.types.map(({ id }) => id));

    useEffect(() => {
        const modal = dialog.current;
        modal.showModal();
        return () => modal.close();
    }, []);

    async function enviar(event) {
        event.preventDefault();
        if (salvando) return;

        const campos = new FormData(event.currentTarget);
        const name = campos.get("name").trim();
        const ids = new Set(campos.getAll("types"));
        const types = tipos.filter(({ id }) => ids.has(id));

        if (!name || types.length === 0) {
            setErro("Informe um nome e selecione pelo menos um tipo.");
            return;
        }

        const stats = Object.fromEntries(
            statLabels.map(([campo]) => [campo, Number(campos.get(campo))]),
        );

        setErro("");
        setSalvando(true);
        try {
            await onSave({ name, stats, types });
        } catch (falha) {
            setErro(falha.message);
            setSalvando(false);
        }
    }

    return (
        <dialog
            ref={dialog}
            className="details pokemon-form-dialog"
            aria-labelledby="pokemon-form-title"
            onCancel={(event) => {
                event.preventDefault();
                if (!salvando) onClose();
            }}
        >
            <h2 id="pokemon-form-title">
                {pokemon ? "Editar Pokémon" : "Cadastrar Pokémon"}
            </h2>
            <form onSubmit={enviar}>
                <fieldset className="pokemon-form-fields" disabled={salvando}>
                    <label>
                        Nome
                        <input
                            name="name"
                            defaultValue={pokemon?.name}
                            required
                            autoFocus
                        />
                    </label>

                    <fieldset className="pokemon-form-types">
                        <legend>Tipos (selecione pelo menos um)</legend>
                        <div className="pokemon-type-options">
                            {tipos.map(({ id, name }) => (
                                <label key={id}>
                                    <input
                                        type="checkbox"
                                        name="types"
                                        value={id}
                                        defaultChecked={tiposIniciais.has(id)}
                                    />
                                    {name}
                                </label>
                            ))}
                        </div>
                    </fieldset>

                    <div className="pokemon-form-stats">
                        {statLabels.map(([campo, rotulo]) => (
                            <label key={campo}>
                                {rotulo}
                                <input
                                    name={campo}
                                    type="number"
                                    min="0"
                                    max="255"
                                    step="1"
                                    required
                                    defaultValue={pokemon?.stats[campo]}
                                />
                            </label>
                        ))}
                    </div>
                </fieldset>

                {erro && (
                    <p className="pokemon-form-error" role="alert">
                        {erro}
                    </p>
                )}
                {salvando && <p role="status">Salvando Pokémon…</p>}
                <div className="pokemon-actions">
                    <button
                        type="button"
                        className="pokemon-action"
                        disabled={salvando}
                        onClick={onClose}
                    >
                        Cancelar
                    </button>
                    <button
                        type="submit"
                        className="pokemon-action primary"
                        disabled={salvando}
                    >
                        Salvar Pokémon
                    </button>
                </div>
            </form>
        </dialog>
    );
}
