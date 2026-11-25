import { useMemo, useState } from 'react'
import data from './data/pokedex-data.json'
import Header from './components/Header.jsx'
import SearchBar from './components/SearchBar.jsx'
import TypeFilter from './components/TypeFilter.jsx'
import PokemonList from './components/PokemonList.jsx'
import PokemonDetails from './components/PokemonDetails.jsx'
import TrainerList from './components/TrainerList.jsx'

export default function App() {
  const [search, setSearch] = useState('')
  const [selectedType, setSelectedType] = useState('All')
  const [selectedPokemon, setSelectedPokemon] = useState(null)
  const [view, setView] = useState('pokemon')

  const pokemonById = useMemo(() => {
    return new Map(data.pokemon.map((pokemon) => [pokemon.id, pokemon]))
  }, [])

  const availableTypes = useMemo(() => {
    const names = new Set()

    data.pokemon.forEach((pokemon) => {
      pokemon.types.forEach((type) => names.add(type.name))
    })

    return Array.from(names).sort()
  }, [])

  const filteredPokemon = useMemo(() => {
    const term = search.trim().toLowerCase()

    return data.pokemon.filter((pokemon) => {
      const matchesSearch =
        term === '' ||
        pokemon.name.toLowerCase().includes(term) ||
        String(pokemon.id).includes(term)

      const matchesType =
        selectedType === 'All' ||
        pokemon.types.some((type) => type.name === selectedType)

      return matchesSearch && matchesType
    })
  }, [search, selectedType])

  return (
    <div className="app">
      <Header view={view} onChangeView={setView} />

      <main className="main">
        {view === 'pokemon' ? (
          <>
            <section className="controls">
              <SearchBar value={search} onChange={setSearch} />
              <TypeFilter
                types={availableTypes}
                selectedType={selectedType}
                onChange={setSelectedType}
              />
            </section>

            {filteredPokemon.length > 0 ? (
              <PokemonList
                pokemon={filteredPokemon}
                onSelect={setSelectedPokemon}
              />
            ) : (
              <div className="empty">
                <h2>Nenhum Pokémon encontrado</h2>
                <p>Ajuste a pesquisa ou selecione outro tipo.</p>
              </div>
            )}
          </>
        ) : (
          <TrainerList
            trainers={data.trainers}
            pokemonById={pokemonById}
            onSelectPokemon={setSelectedPokemon}
          />
        )}
      </main>

      {selectedPokemon && (
        <PokemonDetails
          pokemon={selectedPokemon}
          onClose={() => setSelectedPokemon(null)}
        />
      )}
    </div>
  )
}
