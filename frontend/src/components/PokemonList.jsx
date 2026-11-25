import PokemonCard from './PokemonCard.jsx'

export default function PokemonList({ pokemon, onSelect }) {
  return (
    <section className="pokemon-grid">
      {pokemon.map((item) => (
        <PokemonCard
          key={item.id}
          pokemon={item}
          onSelect={onSelect}
        />
      ))}
    </section>
  )
}
