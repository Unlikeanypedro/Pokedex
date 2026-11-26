# Pokédex — React + Spring Boot + MongoDB

A **Atividade 04** integra a interface React + Vite da Atividade 03 a uma
**API REST em Spring Boot** com persistência no **MongoDB**. A interface permite
pesquisar Pokémon por nome ou ID, filtrar por tipo, consultar atributos e times
de treinadores, cadastrar, editar e excluir Pokémon.

```text
Script_Pokedex.sql → MongoDB → Spring Boot REST API → React + Vite
```

O projeto também foi expandido para um domínio maior (tipos, treinadores,
times, movimentos, jogos, animes e builds). A motivação e o escopo dessa
expansão estão em [`docs/README.md`](docs/README.md).

---

## Sumário

- [Executar com Docker Compose](#executar-com-docker-compose)
- [Estrutura](#estrutura)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do MongoDB](#configuração-do-mongodb)
- [Como executar o backend](#como-executar-o-backend)
- [Como executar o frontend](#como-executar-o-frontend)
- [Endpoints da API](#endpoints-da-api)
- [Seed dos dados](#seed-dos-dados)
- [Coleções no MongoDB](#coleções-no-mongodb)
- [Roteiro de validação](#roteiro-de-validação)
- [Problemas comuns](#problemas-comuns)

---

## Executar com Docker Compose

Com Docker e o plugin Compose instalados, [configure o MongoDB externo](#configuração-do-mongodb)
no `.env` da raiz. Depois execute na raiz do repositório.

```bash
docker compose up --build --wait
```

Abra **http://localhost:5173**. O Compose sobe o frontend e o Spring Boot,
que se conecta ao banco externo definido por `MONGODB_URI` e `MONGODB_DB`.
Confira a conexão em **http://localhost:5173/api/status**.

Se a porta `5173` estiver ocupada, defina `POKEDEX_PORT` no ambiente ou no
`.env` da raiz e use a porta escolhida no navegador.

```bash
docker compose logs -f
docker compose down
```

Os dados ficam no MongoDB externo. Ao trocar de uma versão que subia um
MongoDB no Compose, rode `docker compose up --build --wait --remove-orphans`
uma vez para remover o contêiner antigo.

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
│   ├── Dockerfile
│   └── src/
│       ├── components/       Cards, lista, detalhes, busca, filtro e formulário
│       ├── config/           URL base da API
│       ├── service/          httpClient, pokemon, trainer, type e status
│       └── App.jsx
├── docs/
│   ├── ENUNCIADO.md          Especificação da Atividade 04
│   ├── Script_Pokedex.sql    Fonte dos dados (Pokémon, tipos, treinadores)
│   ├── README.md             Contexto e expansão do escopo
│   └── Diagrama UML da Pokédex MongoDB.png
├── compose.yaml              Inicialização do frontend e da API
└── .env.example
```

---

## Pré-requisitos

O projeto usa um **MongoDB externo**, como o MongoDB Atlas. Para executar
frontend e backend manualmente, instale os seguintes componentes.

- **JDK 17 ou superior**. O Maven Wrapper baixa a versão do Maven usada pelo projeto.
- **Node.js com npm**, nas versões compatíveis com o lockfile do Vite 8 (`^20.19.0 || >=22.12.0`).

---

## Configuração do MongoDB

Na raiz do projeto, se ainda não tiver um `.env`, copie o arquivo de exemplo.

```bash
cp .env.example .env
```

Preencha `MONGODB_URI` com a URI do servidor externo e `MONGODB_DB` com o nome
do banco que a Pokédex deve usar. Use valores sem aspas para que o mesmo arquivo
funcione no Compose e no Spring Boot. O Git ignora o `.env`. Mantenha
`APP_SEED_ENABLED=false` para usar o catálogo existente.

### MongoDB Atlas

1. No cluster, crie um usuário de banco com permissão de leitura e escrita no banco escolhido.
2. Adicione o IP da máquina que executará o backend à lista de acesso do projeto Atlas.
3. Copie a URI em **Connect → Drivers → Java** e preencha `MONGODB_URI` com os dados
   do cluster. Codifique os caracteres reservados da senha. Por exemplo, `@` vira
   `%40`. [Documentação do MongoDB Atlas](https://www.mongodb.com/docs/atlas/driver-connection/)

```properties
MONGODB_URI=mongodb+srv://usuario:senha@cluster.exemplo.mongodb.net/?retryWrites=true&w=majority
MONGODB_DB=pokedex
APP_SEED_ENABLED=false
```

---

## Como executar o backend

```bash
cd backend
sh ./mvnw spring-boot:run
```

No PowerShell, execute `.\mvnw.cmd spring-boot:run` dentro de `backend`.
A API fica disponível em **http://localhost:8080**.

Para conferir a conexão com o banco:

```bash
curl http://localhost:8080/api/status
# {"api":"online","mongodb":"online"}
```

---

## Como executar o frontend

```bash
cd frontend
npm ci
npm run dev -- --strictPort
```

Abra **http://localhost:5173**.

O endereço padrão da API é `http://localhost:8080`. Para usar outro endereço,
defina `VITE_API_URL` em `frontend/.env` antes de iniciar o Vite ou gerar o build.

Build de produção:

```bash
npm run build
npm run preview -- --strictPort
```

A prévia fica em **http://localhost:4173**. Para liberar essa origem, adicione a
linha abaixo ao `.env` da raiz e reinicie o backend. Use a origem exata que o
navegador abre, incluindo a porta.

```properties
app.cors.allowed-origins=http://localhost:5173,http://localhost:4173
```

---

## Endpoints da API

| Método   | Endpoint                  | Descrição                                           | Respostas       |
| -------- | ------------------------- | --------------------------------------------------- | --------------- |
| `GET`    | `/api/status`             | Status da API e do MongoDB                          | 200             |
| `GET`    | `/api/pokemons`           | Lista todos os Pokémon                              | 200             |
| `GET`    | `/api/pokemons?name=pika` | Busca por nome (contém, sem diferenciar maiúsculas) | 200             |
| `GET`    | `/api/pokemons/{id}`      | Busca um Pokémon                                    | 200 / 404       |
| `POST`   | `/api/pokemons`           | Cadastra um Pokémon                                 | 201 / 400       |
| `PUT`    | `/api/pokemons/{id}`      | Atualiza todos os campos                            | 200 / 400 / 404 |
| `DELETE` | `/api/pokemons/{id}`      | Remove um Pokémon                                   | 204 / 404       |
| `GET`    | `/api/types`              | Lista os tipos (`{id, name}`)                       | 200             |
| `GET`    | `/api/trainers`           | Lista treinadores com insígnias e time              | 200             |

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

No MongoDB, `types` guarda os IDs (`[5]`). A API devolve os tipos com seus nomes.
O `id` do Pokémon é uma string. O backend gera um novo ID no cadastro.

### Exemplos

```bash
# listar e consultar um Pokémon
curl -i http://localhost:8080/api/pokemons
curl -i http://localhost:8080/api/pokemons/25

# buscar por nome
curl "http://localhost:8080/api/pokemons?name=char"

# cadastrar
curl -i -X POST http://localhost:8080/api/pokemons \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Testemon",
    "stats": {"hp": 40, "attack": 50, "defense": 60, "specialAttack": 70, "specialDefense": 80, "speed": 90},
    "types": [{"id": "5", "name": "Electric"}]
  }'

# substituir SEU_ID pelo id retornado no cadastro
POKEMON_ID=SEU_ID
curl -i -X PUT "http://localhost:8080/api/pokemons/${POKEMON_ID}" \
  -H "Content-Type: application/json" \
  -d '{"name":"Editado","stats":{"hp":1,"attack":1,"defense":1,"specialAttack":1,"specialDefense":1,"speed":1},"types":[{"id":"2","name":"Fire"}]}'

# excluir e conferir a resposta 404
curl -i -X DELETE "http://localhost:8080/api/pokemons/${POKEMON_ID}"
curl -i "http://localhost:8080/api/pokemons/${POKEMON_ID}"
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

O `DataSeeder` registra a conclusão da carga na coleção `seed_metadata`. Se a
carga for interrompida, a próxima inicialização insere os documentos que
faltam e preserva os registros existentes. Depois da conclusão, o catálogo
continua vazio ao reiniciar se todos os Pokémon tiverem sido excluídos.
A carga é opcional. Para popular um banco de teste vazio, altere esta
configuração no `.env` da raiz antes de subir o backend.

```properties
APP_SEED_ENABLED=true
```

O tipo do Mew foi preenchido como `Psychic` (ID 11). A conversão também removeu
as entradas repetidas nos times de Koga e Lance.

---

## Coleções no MongoDB

| Coleção                                                       | Conteúdo                                           | Situação                 |
| ------------------------------------------------------------- | -------------------------------------------------- | ------------------------ |
| `pokemons`                                                    | Pokémon com `name`, `stats` e IDs de `types`       | Em uso                   |
| `types`                                                       | Tipos (`name`, efetividade opcional)               | Em uso                   |
| `trainers`                                                    | Treinadores (`name`, `badges`)                     | Em uso                   |
| `trainer_teams`                                               | Time de cada treinador (`trainerId`, `pokemonIds`) | Em uso                   |
| `seed_metadata`                                               | Registro de conclusão da carga inicial             | Em uso                   |
| `moves`, `pokemon_moves`, `games`, `pokemon_builds`, `animes` | Modelos da expansão                                | Modelados, sem endpoints |

---

## Roteiro de validação

1. Use um banco vazio de teste, como `MONGODB_DB=pokedex_teste`, com
   `APP_SEED_ENABLED=true`. Suba o backend e confira `mongodb: online` em
   `GET /api/status`. Na primeira carga, confira 151 Pokémon, 18 tipos e 12
   treinadores com times.
2. Rode os [exemplos da API](#exemplos). Confira `200` nas consultas, `201` no
   cadastro, `200` na edição, `204` na exclusão e `404` na consulta do ID excluído.
   Reinicie o backend com o mesmo banco e confirme o `404` novamente.
3. No navegador, pesquise por `Pikachu` e `25`, aplique o filtro `Electric` e
   abra os detalhes pelo catálogo e pelo time de Ash.
4. Cadastre um Pokémon pela interface, edite um atributo e recarregue a
   página para conferir o valor salvo. Cancele uma tentativa de exclusão,
   confirme outra e verifique que o Pokémon desapareceu.
5. Execute `npm run build` e abra a prévia de produção com o backend rodando.

---

## Problemas comuns

| Sintoma                                                | Causa provável                                                                                                       |
| ------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------- |
| `503` ou `mongodb: offline` em `/api/status`           | Confira a URI, as credenciais e a disponibilidade do banco. No Atlas, confira a lista de IPs permitidos.             |
| Erro de CORS no navegador                              | Origem do front fora de `app.cors.allowed-origins`                                                                   |
| `Port 8080 was already in use`                         | Já existe outra instância rodando; encerre-a ou use `--server.port=8081`                                             |
| Lista vazia na API                                     | Confira `MONGODB_DB` e os dados do banco externo. Para popular um banco de teste vazio, use `APP_SEED_ENABLED=true`. |
| `npm: command not found` ou aviso de versão do Node.js | Instale uma versão do Node.js com npm conforme os pré-requisitos.                                                    |
