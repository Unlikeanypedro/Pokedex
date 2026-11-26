# Pokédex — React + Spring Boot + MongoDB

Continuação da **Atividade 03 (Pokédex em React + Vite)** para a **Atividade 04**:
os dados deixam de vir do arquivo `pokedex-data.json` e passam a vir de uma
**API REST em Spring Boot** com persistência no **MongoDB**.

```text
Script_Pokedex.sql → MongoDB → Spring Boot REST API → React + Vite
```

O projeto também foi expandido para um domínio maior (tipos, treinadores,
times, movimentos, jogos, animes e builds). A motivação e o escopo dessa
expansão estão em [`docs/README.md`](docs/README.md).

---

## Sumário

- [Estrutura](#estrutura)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do MongoDB](#configuração-do-mongodb)
- [Como executar o backend](#como-executar-o-backend)
- [Como executar o frontend](#como-executar-o-frontend)
- [Endpoints da API](#endpoints-da-api)
- [Seed dos dados](#seed-dos-dados)
- [Coleções no MongoDB](#coleções-no-mongodb)
- [Situação atual da integração](#situação-atual-da-integração)
- [Problemas comuns](#problemas-comuns)

---

## Estrutura

```text
Pokedex/
├── backend/                  Spring Boot 4 + Spring Data MongoDB
│   ├── src/main/java/com/example/back/
│   │   ├── application/      BackApplication (main)
│   │   ├── config/           Mongo, CORS e DataSeeder
│   │   ├── controller/       Pokemon, Type, Trainer e Status
│   │   ├── dto/              Corpos de requisição/resposta
│   │   ├── exception/        Erros padronizados (400/404/405/503/500)
│   │   ├── model/            Documentos do MongoDB
│   │   ├── repository/       Spring Data repositories
│   │   └── service/          Regras de negócio
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── seed/pokedex-seed.json
│   ├── Dockerfile
│   └── pom.xml
├── frontend/                 React 19 + Vite 8
│   └── src/
│       ├── components/       Cards, lista, detalhes, filtro, busca
│       ├── data/             pokedex-data.json (dados locais da Atividade 03)
│       ├── service/          httpClient, pokemon, trainer, type e status
│       └── App.jsx
├── docs/
│   ├── ENUNCIADO.md          Especificação da Atividade 04
│   ├── Script_Pokedex.sql    Fonte dos dados (Pokémon, tipos, treinadores)
│   ├── README.md             Contexto e expansão do escopo
│   └── Diagrama UML da Pokédex MongoDB.png
└── .env.example
```

---

## Pré-requisitos

- **Java 17+** (o projeto compila com `release 17`)
- **Node.js 18+** (ou Bun) para o frontend
- Uma conta/cluster no **MongoDB Atlas** (ou um MongoDB local)

---

## Configuração do MongoDB

As credenciais **não vão para o Git**.

1. Copie o template:

   ```bash
   cp .env.example .env
   ```

2. Preencha o `.env` na raiz do projeto:

   ```properties
   MONGODB_URI=mongodb+srv://usuario:senha@cluster.exemplo.mongodb.net/?retryWrites=true&w=majority
   MONGODB_DB=pokedex
   ```

   > O arquivo é lido como `.properties` pelo Spring Boot — **não use aspas** nos
   > valores. Se a senha tiver caracteres especiais (`@ : / ? # %`), codifique-os
   > (por exemplo, `@` vira `%40`).

3. No Atlas, libere o acesso de rede (`Network Access → Add IP Address`).

O backend lê o `.env` da raiz pelo `spring.config.import` em
`backend/src/main/resources/application.properties`; as variáveis
`MONGODB_URI` e `MONGODB_DB` também funcionam se definidas no ambiente.

---

## Como executar o backend

```bash
cd backend
./mvnw spring-boot:run      # Windows: mvnw.cmd spring-boot:run
```

A API sobe em **http://localhost:8080**.

Para conferir a conexão com o banco:

```bash
curl http://localhost:8080/api/status
# {"api":"online","mongodb":"online"}
```

Na primeira execução, o `DataSeeder` popula o banco automaticamente
(veja [Seed dos dados](#seed-dos-dados)).

---

## Como executar o frontend

```bash
cd frontend
npm install
npm run dev
```

Abra **http://localhost:5173**.

O endereço da API vem de `VITE_API_URL` (padrão: `http://localhost:8080`) e é
lido em `src/config/apiConfig.js`.

Build de produção:

```bash
npm run build
npm run preview
```

---

## Endpoints da API

| Método | Endpoint | Descrição | Respostas |
| --- | --- | --- | --- |
| `GET` | `/api/status` | Status da API e do MongoDB | 200 |
| `GET` | `/api/pokemons` | Lista todos os Pokémon | 200 |
| `GET` | `/api/pokemons?name=pika` | Busca por nome (contém, sem diferenciar maiúsculas) | 200 |
| `GET` | `/api/pokemons/{id}` | Busca um Pokémon | 200 / 404 |
| `POST` | `/api/pokemons` | Cadastra um Pokémon | 201 / 400 |
| `PUT` | `/api/pokemons/{id}` | Atualiza todos os campos | 200 / 400 / 404 |
| `DELETE` | `/api/pokemons/{id}` | Remove um Pokémon | 204 / 404 |
| `GET` | `/api/types` | Lista os tipos (`{id, name}`) | 200 |
| `GET` | `/api/trainers` | Lista treinadores com insígnias e time | 200 |

### Formato do Pokémon

```json
{
  "id": "25",
  "name": "Pikachu",
  "stats": {
    "hp": 35,
    "attack": 55,
    "defense": 30,
    "specialAttack": 50,
    "specialDefense": 40,
    "speed": 90
  },
  "types": [{ "id": "5", "name": "Electric" }]
}
```

No MongoDB, `types` guarda apenas os IDs (`[5]`); a API devolve os tipos já
resolvidos com o nome.

### Exemplos

```bash
# listar
curl http://localhost:8080/api/pokemons

# buscar por nome
curl "http://localhost:8080/api/pokemons?name=char"

# cadastrar
curl -X POST http://localhost:8080/api/pokemons \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Testemon",
    "stats": {"hp": 40, "attack": 50, "defense": 60, "specialAttack": 70, "specialDefense": 80, "speed": 90},
    "types": [{"id": "5", "name": "Electric"}]
  }'

# atualizar
curl -X PUT http://localhost:8080/api/pokemons/SEU_ID \
  -H "Content-Type: application/json" \
  -d '{"name":"Editado","stats":{"hp":1,"attack":1,"defense":1,"specialAttack":1,"specialDefense":1,"speed":1},"types":[{"id":"2","name":"Fire"}]}'

# excluir
curl -X DELETE http://localhost:8080/api/pokemons/SEU_ID
```

Erros seguem um formato único:

```json
{
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Pokémon com id 9999 não encontrado.",
  "caminho": "/api/pokemons/9999",
  "timestamp": "2026-11-26T01:41:29.970"
}
```

---

## Seed dos dados

`backend/src/main/resources/seed/pokedex-seed.json` foi **gerado a partir de**
[`docs/Script_Pokedex.sql`](docs/Script_Pokedex.sql) e contém:

- 151 Pokémon com os seis atributos;
- 18 tipos (do `Typeless` ao `Steel`);
- 12 treinadores com insígnias;
- 12 times (relação treinador ↔ Pokémon).

O `DataSeeder` roda no start e **só insere se a coleção `pokemons` estiver
vazia** — reiniciar a aplicação não duplica dados. Para subir sem popular:

```properties
app.seed.enabled=false
```

> O script SQL não traz a relação de tipo do Mew; no seed ele foi cadastrado
> como `Psychic` (ID 11). Os times foram deduplicados (o script repete alguns
> Pokémon do Koga).

---

## Coleções no MongoDB

| Coleção | Conteúdo | Situação |
| --- | --- | --- |
| `pokemons` | Pokémon com `name`, `stats` e IDs de `types` | Em uso |
| `types` | Tipos (`name`, efetividade opcional) | Em uso |
| `trainers` | Treinadores (`name`, `badges`) | Em uso |
| `trainer_teams` | Time de cada treinador (`trainerId`, `pokemonIds`) | Em uso |
| `moves`, `pokemon_moves`, `games`, `pokemon_builds`, `animes` | Modelos da expansão | Modelados, sem endpoints |

---

## Situação atual da integração

- **Backend**: CRUD de Pokémon, listagem de tipos e de treinadores, validação,
  tratamento de erros, CORS e seed — tudo funcionando contra o MongoDB.
- **Frontend**: a interface continua consumindo `src/data/pokedex-data.json`.
  A camada de acesso à API já existe e está pronta
  (`src/service/pokemonService.js`, `trainerService.js`, `typeService.js`),
  mas **ainda não foi ligada aos componentes** — falta trocar a origem dos
  dados no `App.jsx` e adicionar o formulário de cadastro/edição/exclusão.

---

## Problemas comuns

| Sintoma | Causa provável |
| --- | --- |
| `503` com "Não foi possível conectar ao MongoDB Atlas" | Connection string inválida ou IP não liberado no Atlas |
| Erro de CORS no navegador | Origem do front fora de `app.cors.allowed-origins` |
| `Port 8080 was already in use` | Já existe outra instância rodando; encerre-a ou use `--server.port=8081` |
| Lista vazia na API | Coleções vazias e `app.seed.enabled=false`, ou seed rodou antes de o banco existir |
| `npm: command not found` | Instale o Node.js ou use `bun install && bun run dev` |
