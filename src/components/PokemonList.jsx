import PokemonCard from "./PokemonCard";

function PokemonList({ pokemons, onSelect }) {
    return (
        <div className="pokemon-list">
            {pokemons.map((p) => (
                <PokemonCard key={p.id} pokemon={p} onSelect={onSelect} />
            ))}
        </div>
    );
}
export default PokemonList;
