# Pokédex

Aplicação web desenvolvida com React 19 e Vite 8 para exibir, pesquisar, filtrar e detalhar Pokémon a partir do arquivo `src/data/pokedex-data.json`.

## Execução

```bash
npm install
npm run dev
```

Abra `http://localhost:5173` no navegador.

Se o npm bloquear o script de instalação do `esbuild`, aprove-o e reconstrua a dependência:

```bash
npm install-scripts approve esbuild
npm rebuild esbuild
npm run dev
```

## Build

```bash
npm run build
npm run preview
```

## Dados

Os Pokémon, tipos, atributos e treinadores são carregados de `src/data/pokedex-data.json`.

As imagens são obtidas pela PokéAPI, usando o ID de cada Pokémon:

`https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{id}.png`

## Organização

- `src/components`: componentes React da interface.
- `src/data`: dados locais da Pokédex.
- `src/utils`: cores e utilidades visuais dos tipos.
- `src/App.jsx`: estado principal, pesquisa, filtro, seleção e navegação.
- `src/index.css`: estilos globais e responsivos.
