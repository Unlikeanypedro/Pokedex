export default function TrainerCard({ trainer, pokemonById, onSelectPokemon }) {
  return (
    <article className="trainer-card">
      <div className="trainer-header">
        <h3>{trainer.name}</h3>
        <span className="badge-count">{trainer.badges} insígnias</span>
      </div>

      <div className="trainer-team">
        {trainer.pokemonIds.map((id, index) => {
          const pokemon = pokemonById.get(id)

          return pokemon ? (
            <button
              key={`${trainer.id}-${id}-${index}`}
              className="team-chip"
              onClick={() => onSelectPokemon(pokemon)}
            >
              <img
                src={`https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.id}.png`}
                alt={pokemon.name}
                loading="lazy"
              />
              #{String(pokemon.id).padStart(3, '0')} {pokemon.name}
            </button>
          ) : null
        })}
      </div>
    </article>
  )
}
