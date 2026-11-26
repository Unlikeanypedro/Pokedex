import { useState } from 'react'

export default function PokemonSprite({ id, className, lazy = false }) {
  const [idComErro, setIdComErro] = useState(null)

  if (!/^\d{1,10}$/.test(id) || idComErro === id) {
    return <span className={`${className} sprite-placeholder`} aria-hidden="true">—</span>
  }

  return (
    <img
      className={className}
      src={`https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png`}
      alt=""
      loading={lazy ? 'lazy' : undefined}
      onError={() => setIdComErro(id)}
    />
  )
}
