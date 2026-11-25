import { useEffect } from 'react'
import { getTypeColor } from '../utils/typeColors.js'

const statLabels = [
  ['hp', 'HP'],
  ['attack', 'Ataque'],
  ['defense', 'Defesa'],
  ['specialAttack', 'Ataque Especial'],
  ['specialDefense', 'Defesa Especial'],
  ['speed', 'Velocidade'],
]

export default function PokemonDetails({ pokemon, onClose }) {
  useEffect(() => {
    const handleKey = (event) => {
      if (event.key === 'Escape') onClose()
    }

    window.addEventListener('keydown', handleKey)
    document.body.style.overflow = 'hidden'

    return () => {
      window.removeEventListener('keydown', handleKey)
      document.body.style.overflow = ''
    }
  }, [onClose])

  return (
    <div className="modal-overlay" onClick={onClose}>
      <section className="details" onClick={(event) => event.stopPropagation()}>
        <div className="details-header">
          <img
            className="details-portrait"
            src={`https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.id}.png`}
            alt={pokemon.name}
          />
          <div className="details-title">
            <span className="pokemon-id">#{String(pokemon.id).padStart(3, '0')}</span>
            <h2>{pokemon.name}</h2>
          </div>
          <button className="close-button" onClick={onClose} aria-label="Fechar detalhes">
            ×
          </button>
        </div>

        <div className="details-types">
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

        <div className="stat-list">
          {statLabels.map(([key, label]) => (
            <div key={key} className="stat-row">
              <span className="stat-name">{label}</span>
              <div className="stat-track">
                <div
                  className="stat-fill"
                  style={{ width: `${Math.min(100, (pokemon.stats[key] / 180) * 100)}%` }}
                />
              </div>
              <strong>{pokemon.stats[key]}</strong>
            </div>
          ))}
        </div>
      </section>
    </div>
  )
}
