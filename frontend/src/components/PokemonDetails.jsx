import { useEffect, useRef, useState } from 'react'
import { statLabels } from '../utils/pokemonStats.js'
import { getTypeColor } from '../utils/typeColors.js'
import PokemonSprite from './PokemonSprite.jsx'

export default function PokemonDetails({ pokemon, erro, mensagem, onClose, onEdit, onDelete }) {
  const closeButton = useRef(null)
  const [excluindo, setExcluindo] = useState(false)
  const [erroExclusao, setErroExclusao] = useState('')

  useEffect(() => {
    const previousFocus = document.activeElement
    closeButton.current.focus()
    document.body.style.overflow = 'hidden'

    return () => {
      document.body.style.overflow = ''
      previousFocus?.focus()
    }
  }, [])

  useEffect(() => {
    const handleKey = (event) => {
      if (event.key === 'Escape' && !excluindo) onClose()
    }
    window.addEventListener('keydown', handleKey)
    return () => window.removeEventListener('keydown', handleKey)
  }, [excluindo, onClose])

  async function confirmarExclusao() {
    if (excluindo || !window.confirm(`Excluir ${pokemon.name} da Pokédex? Essa ação não pode ser desfeita.`)) {
      return
    }

    setErroExclusao('')
    setExcluindo(true)
    try {
      await onDelete(pokemon)
    } catch (falha) {
      setErroExclusao(falha.message)
      setExcluindo(false)
    }
  }

  return (
    <div className="modal-overlay" onClick={excluindo ? undefined : onClose}>
      <section
        className="details"
        role="dialog"
        aria-modal="true"
        aria-labelledby="pokemon-details-title"
        onClick={(event) => event.stopPropagation()}
      >
        <div className="details-header">
          {pokemon && (
            <PokemonSprite id={pokemon.id} className="details-portrait" />
          )}
          <div className="details-title">
            {pokemon && (
              <span className="pokemon-id">#{String(pokemon.id).padStart(3, '0')}</span>
            )}
            <h2 id="pokemon-details-title">{pokemon?.name ?? 'Detalhes do Pokémon'}</h2>
          </div>
          <button
            ref={closeButton}
            className="close-button"
            onClick={onClose}
            disabled={excluindo}
            aria-label="Fechar detalhes"
          >
            ×
          </button>
        </div>

        {erro && <p role="alert">{erro}</p>}
        {!pokemon && !erro && <p role="status">Carregando detalhes…</p>}
        {mensagem && <p className="pokemon-notice" role="status">{mensagem}</p>}
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
            {erroExclusao && <p className="pokemon-form-error" role="alert">{erroExclusao}</p>}
            {excluindo && <p role="status">Excluindo Pokémon…</p>}
            <div className="pokemon-actions">
              {onEdit && (
                <button
                  className="pokemon-action"
                  onClick={() => onEdit(pokemon)}
                  disabled={excluindo}
                >
                  Editar Pokémon
                </button>
              )}
              <button
                className="pokemon-action danger"
                onClick={confirmarExclusao}
                disabled={excluindo}
              >
                Excluir Pokémon
              </button>
            </div>
          </>
        )}
      </section>
    </div>
  )
}
