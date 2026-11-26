import { useEffect, useRef } from 'react'
import { getTypeColor } from '../utils/typeColors.js'

const statLabels = [
  ['hp', 'HP'],
  ['attack', 'Ataque'],
  ['defense', 'Defesa'],
  ['specialAttack', 'Ataque Especial'],
  ['specialDefense', 'Defesa Especial'],
  ['speed', 'Velocidade'],
]

export default function PokemonDetails({ pokemon, erro, onClose }) {
  const closeButton = useRef(null)

  useEffect(() => {
    const previousFocus = document.activeElement
    const handleKey = (event) => {
      if (event.key === 'Escape') onClose()
    }

    closeButton.current.focus()
    window.addEventListener('keydown', handleKey)
    document.body.style.overflow = 'hidden'

    return () => {
      window.removeEventListener('keydown', handleKey)
      document.body.style.overflow = ''
      previousFocus?.focus()
    }
  }, [onClose])

  return (
    <div className="modal-overlay" onClick={onClose}>
      <section
        className="details"
        role="dialog"
        aria-modal="true"
        aria-labelledby="pokemon-details-title"
        onClick={(event) => event.stopPropagation()}
      >
        <div className="details-header">
          {pokemon && (
            <img
              className="details-portrait"
              src={`https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.id}.png`}
              alt={pokemon.name}
            />
          )}
          <div className="details-title">
            {pokemon && (
              <span className="pokemon-id">#{String(pokemon.id).padStart(3, '0')}</span>
            )}
            <h2 id="pokemon-details-title">{pokemon?.name ?? 'Detalhes do Pokémon'}</h2>
          </div>
          <button ref={closeButton} className="close-button" onClick={onClose} aria-label="Fechar detalhes">
            ×
          </button>
        </div>

        {erro && <p role="alert">{erro}</p>}
        {!pokemon && !erro && <p role="status">Carregando detalhes…</p>}
        {pokemon && (
          <>
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
          </>
        )}
      </section>
    </div>
  )
}
