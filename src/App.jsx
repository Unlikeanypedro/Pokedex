import { useState } from "react";
import data from "./data/pokedex-data.json";
import SearchBar from "./components/SearchBar";
import TypeFilter from "./components/TypeFilter";
import PokemonList from "./components/PokemonList";
import PokemonDetails from "./components/PokemonDetails";
import TrainerList from "./components/TrainerList";
import "./App.css";

function App() {
    const [search, setSearch] = useState("");
    const [type, setType] = useState("all");
    const [selected, setSelected] = useState(null);
    const [view, setView] = useState("pokedex"); // 'pokedex' | 'trainers'

    const term = search.trim().toLowerCase();

    const filtered = data.pokemon.filter((p) => {
        const matchSearch =
            term === "" ||
            p.name.toLowerCase().includes(term) ||
            String(p.id) === term;

        const matchType =
            type === "all" ||
            (type === "Typeless"
                ? p.types.length === 0
                : p.types.some((t) => t.name === type));

        return matchSearch && matchType;
    });

    return (
        <main className="app">
            <header>
                <h1>Pokédex</h1>
                <nav>
                    <button onClick={() => setView("pokedex")}>Pokémon</button>
                    <button onClick={() => setView("trainers")}>
                        Treinadores
                    </button>
                </nav>
            </header>

            {view === "pokedex" ? (
                <>
                    <div className="controls">
                        <SearchBar value={search} onChange={setSearch} />
                        <TypeFilter
                            types={data.types}
                            value={type}
                            onChange={setType}
                        />
                    </div>

                    {filtered.length === 0 ? (
                        <p className="empty">Nenhum Pokémon encontrado.</p>
                    ) : (
                        <PokemonList
                            pokemons={filtered}
                            onSelect={setSelected}
                        />
                    )}
                </>
            ) : (
                <TrainerList trainers={data.trainers} pokemons={data.pokemon} />
            )}

            {selected && (
                <PokemonDetails
                    pokemon={selected}
                    onClose={() => setSelected(null)}
                />
            )}
        </main>
    );
}

export default App;
