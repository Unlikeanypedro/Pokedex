import { getTypeColor } from '../utils/typeColors.js'

export default function PokemonCard({ pokemon, onSelect }) {
  const stats = pokemon.stats

  return (
    <button className="pokemon-card" onClick={() => onSelect(pokemon)}>
      <div className="card-top">
        <img
          className="pokemon-image"
          src={`https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.id}.png`}
          alt={pokemon.name}
          loading="lazy"
        />
        <div className="card-info">
          <span className="pokemon-id">#{String(pokemon.id).padStart(3, '0')}</span>
          <h3>{pokemon.name}</h3>
        </div>
      </div>

      <div className="type-row">
        {pokemon.types.map((type) => (
          <span
            key={type.id}
            className="type-chip"
            style={{ background: getTypeColor(type.name) }}
          >
            {type.name}
          </span>
        ))}
      </div>

      <div className="stat-grid">
        <div className="stat">
          <span className="stat-label">HP</span>
          <span className="stat-value">{stats.hp}</span>
        </div>
        <div className="stat">
          <span className="stat-label">Ataque</span>
          <span className="stat-value">{stats.attack}</span>
        </div>
        <div className="stat">
          <span className="stat-label">Defesa</span>
          <span className="stat-value">{stats.defense}</span>
        </div>
        <div className="stat">
          <span className="stat-label">Atq. Esp.</span>
          <span className="stat-value">{stats.specialAttack}</span>
        </div>
        <div className="stat">
          <span className="stat-label">Def. Esp.</span>
          <span className="stat-value">{stats.specialDefense}</span>
        </div>
        <div className="stat">
          <span className="stat-label">Velocidade</span>
          <span className="stat-value">{stats.speed}</span>
        </div>
      </div>
    </button>
  )
}
