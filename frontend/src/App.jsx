import { useCallback, useEffect, useMemo, useState } from 'react'
import Header from './components/Header.jsx'
import SearchBar from './components/SearchBar.jsx'
import TypeFilter from './components/TypeFilter.jsx'
import PokemonList from './components/PokemonList.jsx'
import PokemonDetails from './components/PokemonDetails.jsx'
import PokemonForm from './components/PokemonForm.jsx'
import TrainerList from './components/TrainerList.jsx'
import {
  atualizarPokemon,
  buscarPokemon,
  criarPokemon,
  listarPokemons,
} from './service/pokemonService.js'
import { listarTreinadores } from './service/trainerService.js'
import { listarTipos } from './service/typeService.js'

function carregarDados(consulta, atualizar, signal) {
  consulta({ signal }).then(
    (dados) => {
      if (!signal.aborted) atualizar({ dados })
    },
    (erro) => {
      if (!signal.aborted) atualizar({ erro: erro.message })
    },
  )
}

export default function App() {
  const [search, setSearch] = useState('')
  const [selectedType, setSelectedType] = useState('All')
  const [view, setView] = useState('pokemon')
  const [catalogo, setCatalogo] = useState({})
  const [tipos, setTipos] = useState({})
  const [treinadores, setTreinadores] = useState({})
  const [selecao, setSelecao] = useState(null)
  const [detalhes, setDetalhes] = useState({})
  const [formulario, setFormulario] = useState(null)
  const [mensagem, setMensagem] = useState('')

  useEffect(() => {
    const controller = new AbortController()
    const { signal } = controller

    carregarDados(listarPokemons, setCatalogo, signal)
    carregarDados(listarTipos, setTipos, signal)
    carregarDados(listarTreinadores, setTreinadores, signal)

    return () => controller.abort()
  }, [])

  function selecionarPokemon(pokemon) {
    setMensagem('')
    setSelecao({ id: pokemon.id })
  }

  const fecharDetalhes = useCallback(() => {
    setSelecao(null)
  }, [])

  function abrirFormulario(pokemon = null) {
    setMensagem('')
    setFormulario({ pokemon })
  }

  async function salvarPokemon(dados) {
    const pokemonEmEdicao = formulario.pokemon
    const salvo = pokemonEmEdicao
      ? await atualizarPokemon(pokemonEmEdicao.id, dados)
      : await criarPokemon(dados)

    setCatalogo(({ dados: pokemons }) => ({
      dados: [...pokemons.filter(({ id }) => id !== salvo.id), salvo]
        .sort((a, b) => a.name.localeCompare(b.name)),
    }))
    if (pokemonEmEdicao) {
      setDetalhes({ dados: salvo, selecao })
    } else {
      setSearch('')
      setSelectedType('All')
    }
    setMensagem(`${salvo.name} ${pokemonEmEdicao ? 'atualizado' : 'cadastrado'} com sucesso.`)
    setFormulario(null)
  }

  useEffect(() => {
    if (!selecao) return

    const controller = new AbortController()
    carregarDados(
      (opcoes) => buscarPokemon(selecao.id, opcoes),
      (resultado) => setDetalhes({ ...resultado, selecao }),
      controller.signal,
    )

    return () => controller.abort()
  }, [selecao])

  const pokemonById = useMemo(() => {
    return new Map(catalogo.dados?.map((pokemon) => [pokemon.id, pokemon]))
  }, [catalogo.dados])

  const filteredPokemon = useMemo(() => {
    const term = search.trim().toLowerCase()

    return catalogo.dados?.filter((pokemon) => {
      const matchesSearch =
        term === '' ||
        pokemon.name.toLowerCase().includes(term) ||
        String(pokemon.id).includes(term)

      const matchesType =
        selectedType === 'All' ||
        pokemon.types.some((type) => type.name === selectedType)

      return matchesSearch && matchesType
    }) ?? []
  }, [catalogo.dados, search, selectedType])

  const listagem = view === 'pokemon' ? tipos : treinadores
  const erro = catalogo.erro || listagem.erro
  const carregando = !catalogo.dados || !listagem.dados
  const detalhesAtuais = detalhes.selecao === selecao ? detalhes : {}

  return (
    <div className="app">
      <Header view={view} onChangeView={setView} />

      <main className="main">
        {mensagem && !selecao && <p className="pokemon-notice" role="status">{mensagem}</p>}
        {erro ? (
          <div className="empty" role="alert">
            <h2>Falha ao carregar {view === 'pokemon' ? 'a Pokédex' : 'os treinadores'}</h2>
            <p>{erro}</p>
          </div>
        ) : carregando ? (
          <div className="empty" role="status">
            <p>{view === 'pokemon' ? 'Carregando Pokémon…' : 'Carregando treinadores…'}</p>
          </div>
        ) : view === 'pokemon' ? (
          <>
            <section className="controls">
              <div className="catalog-actions">
                <SearchBar value={search} onChange={setSearch} />
                <button
                  className="pokemon-action primary"
                  disabled={tipos.dados.length === 0}
                  onClick={() => abrirFormulario()}
                >
                  Cadastrar Pokémon
                </button>
              </div>
              <TypeFilter
                types={tipos.dados.map((tipo) => tipo.name)}
                selectedType={selectedType}
                onChange={setSelectedType}
              />
            </section>

            {filteredPokemon.length > 0 ? (
              <PokemonList
                pokemon={filteredPokemon}
                onSelect={selecionarPokemon}
              />
            ) : (
              <div className="empty">
                <h2>Nenhum Pokémon encontrado</h2>
                <p>{catalogo.dados.length ? 'Ajuste a pesquisa ou selecione outro tipo.' : 'O catálogo está vazio.'}</p>
              </div>
            )}
          </>
        ) : treinadores.dados.length > 0 ? (
          <TrainerList
            trainers={treinadores.dados}
            pokemonById={pokemonById}
            onSelectPokemon={selecionarPokemon}
          />
        ) : (
          <div className="empty">
            <h2>Nenhum treinador encontrado</h2>
          </div>
        )}
      </main>

      {selecao && !formulario && (
        <PokemonDetails
          pokemon={detalhesAtuais.dados}
          erro={detalhesAtuais.erro}
          mensagem={mensagem}
          onClose={fecharDetalhes}
          onEdit={tipos.dados?.length ? abrirFormulario : undefined}
        />
      )}
      {formulario && (
        <PokemonForm
          pokemon={formulario.pokemon}
          tipos={tipos.dados}
          onSave={salvarPokemon}
          onClose={() => setFormulario(null)}
        />
      )}
    </div>
  )
}
