export const typeColors = {
  Typeless: '#68a090',
  Normal: '#a8a878',
  Fire: '#f08030',
  Water: '#6890f0',
  Grass: '#78c850',
  Electric: '#f8d030',
  Ice: '#98d8d8',
  Fighting: '#c03028',
  Poison: '#a040a0',
  Ground: '#e0c068',
  Flying: '#a890f0',
  Psychic: '#f85888',
  Bug: '#a8b820',
  Rock: '#b8a038',
  Ghost: '#705898',
  Dark: '#705848',
  Dragon: '#7038f8',
  Steel: '#b8b8d0'
}

export function getTypeColor(name) {
  return typeColors[name] || '#a8a878'
}
