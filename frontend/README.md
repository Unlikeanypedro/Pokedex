# Pokédex

Interface em React 19 e Vite 8 para consultar Pokémon e treinadores, pesquisar
por nome ou ID, filtrar por tipo e cadastrar, editar ou excluir Pokémon pela API.

## Execução

Configure o MongoDB e inicie o Spring Boot seguindo o
[README principal](../README.md#configuração-do-mongodb). Com Node.js e npm
instalados, execute os comandos abaixo dentro de `frontend`.

```bash
npm ci
npm run dev -- --strictPort
```

Abra `http://localhost:5173` no navegador.

O frontend chama `/api` na mesma origem por padrão. Em desenvolvimento e na
prévia, o Vite encaminha essas requisições a `http://localhost:8080`.

Para acessar um backend em outra origem, defina `VITE_API_URL` em `frontend/.env`
antes de iniciar o Vite ou gerar o build. Use a origem do backend, sem `/api`.

```properties
VITE_API_URL=http://localhost:8080
```

## Build

```bash
npm run build
npm run preview -- --strictPort
```

Abra `http://localhost:4173`. Para o deploy, consulte a
[configuração do frontend](../README.md#como-executar-o-frontend).

## Dados

Os dados vêm da API Spring Boot, que usa o MongoDB. Os sprites dos Pokémon
com ID numérico vêm do repositório da PokéAPI. A interface usa um espaço
reservado quando o Pokémon não tem sprite ou quando a imagem está indisponível.

## Organização

- `src/components` contém os componentes da interface, incluindo o formulário.
- `src/service` contém as funções que consultam e alteram dados pela API.
- `src/utils` concentra as cores dos tipos e os rótulos dos atributos.
- `src/App.jsx` gerencia o catálogo, a pesquisa, o filtro e a navegação.
- `src/index.css` define os estilos globais e responsivos.
