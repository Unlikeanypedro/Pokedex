package com.example.back.service;

import com.example.back.dto.PokemonRequest;
import com.example.back.dto.PokemonResponse;
import com.example.back.dto.TypeRef;
import com.example.back.exception.DadosInvalidosException;
import com.example.back.exception.PokemonNaoEncontradoException;
import com.example.back.model.Pokemon.Pokemon;
import com.example.back.model.Pokemon.PokemonStats;
import com.example.back.model.Type.Type;
import com.example.back.repository.PokemonRepository;
import com.example.back.repository.TypeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Regras de negócio do CRUD de pokémons.
 * O controller recebe a requisição HTTP e o service decide o que fazer com o MongoDB.
 */
@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final TypeRepository typeRepository;

    // Injeção de dependência pelo construtor (equivale ao @Autowired no atributo)
    public PokemonService(PokemonRepository pokemonRepository, TypeRepository typeRepository) {
        this.pokemonRepository = pokemonRepository;
        this.typeRepository = typeRepository;
    }

    /** Lista todos os pokémons (ordem alfabética). Com "name", filtra pelos que contêm o texto. */
    public List<PokemonResponse> listar(String name) {
        List<Pokemon> pokemons;

        if (name == null || name.isBlank()) {
            pokemons = pokemonRepository.findAll(Sort.by("name"));
        } else {
            pokemons = pokemonRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name.trim());
        }

        // Carrega os tipos uma única vez para não consultar o banco por Pokémon
        Map<String, String> nomesDosTipos = typeRepository
            .findAll()
            .stream()
            .collect(Collectors.toMap(Type::getId, Type::getName));

        return pokemons
            .stream()
            .map(pokemon -> montarResposta(pokemon, nomesDosTipos))
            .toList();
    }

    public PokemonResponse buscarPorId(String id) {
        return montarResposta(buscarEntidade(id));
    }

    public PokemonResponse inserir(PokemonRequest dados) {
        validar(dados);

        Pokemon pokemon = new Pokemon();
        pokemon.setName(dados.name().trim());
        pokemon.setStats(copiarStats(dados.stats()));
        pokemon.setTypes(extrairIds(dados.types()));

        return montarResposta(pokemonRepository.save(pokemon));
    }

    public PokemonResponse atualizar(String id, PokemonRequest dados) {
        Pokemon existente = buscarEntidade(id); // lança 404 se não existir
        validar(dados);

        // Atualiza todos os campos editáveis
        existente.setName(dados.name().trim());
        existente.setStats(copiarStats(dados.stats()));
        existente.setTypes(extrairIds(dados.types()));

        return montarResposta(pokemonRepository.save(existente));
    }

    public void excluir(String id) {
        if (!pokemonRepository.existsById(id)) {
            throw new PokemonNaoEncontradoException(id);
        }
        pokemonRepository.deleteById(id);
    }

    // ---------------------------------------------------------------------
    // Apoio
    // ---------------------------------------------------------------------

    private Pokemon buscarEntidade(String id) {
        return pokemonRepository
            .findById(id)
            .orElseThrow(() -> new PokemonNaoEncontradoException(id));
    }

    /** Converte o documento do MongoDB no formato da API (tipos com nome). */
    private PokemonResponse montarResposta(Pokemon pokemon) {
        Map<String, String> nomesDosTipos = typeRepository
            .findAll()
            .stream()
            .collect(Collectors.toMap(Type::getId, Type::getName));

        return montarResposta(pokemon, nomesDosTipos);
    }

    private PokemonResponse montarResposta(Pokemon pokemon, Map<String, String> nomesDosTipos) {
        List<TypeRef> tipos =
            pokemon.getTypes() == null
                ? List.of()
                : pokemon
                      .getTypes()
                      .stream()
                      .map(tipoId ->
                          new TypeRef(
                              String.valueOf(tipoId),
                              nomesDosTipos.getOrDefault(String.valueOf(tipoId), "Desconhecido")
                          )
                      )
                      .toList();

        return new PokemonResponse(pokemon.getId(), pokemon.getName(), pokemon.getStats(), tipos);
    }

    /** Valida os campos obrigatórios (lança 400 pelo GlobalExceptionHandler). */
    private void validar(PokemonRequest dados) {
        if (dados == null) {
            throw new DadosInvalidosException("Envie os dados do pokémon no corpo da requisição.");
        }
        if (dados.name() == null || dados.name().isBlank()) {
            throw new DadosInvalidosException("O nome do pokémon é obrigatório.");
        }
        if (dados.stats() == null) {
            throw new DadosInvalidosException("As estatísticas são obrigatórias.");
        }

        validarStat("HP", dados.stats().getHp());
        validarStat("Ataque", dados.stats().getAttack());
        validarStat("Defesa", dados.stats().getDefense());
        validarStat("Ataque Especial", dados.stats().getSpecialAttack());
        validarStat("Defesa Especial", dados.stats().getSpecialDefense());
        validarStat("Velocidade", dados.stats().getSpeed());

        if (dados.types() == null || dados.types().isEmpty()) {
            throw new DadosInvalidosException("Informe ao menos um tipo para o pokémon.");
        }

        for (TypeRef tipo : dados.types()) {
            if (tipo == null || tipo.id() == null) {
                throw new DadosInvalidosException("Tipo inválido na lista de tipos.");
            }
            if (!typeRepository.existsById(tipo.id())) {
                throw new DadosInvalidosException("O tipo " + tipo.id() + " não existe.");
            }
        }
    }

    private void validarStat(String nome, int valor) {
        if (valor < 0 || valor > 255) {
            throw new DadosInvalidosException("O valor de " + nome + " deve estar entre 0 e 255.");
        }
    }

    private PokemonStats copiarStats(PokemonStats origem) {
        PokemonStats destino = new PokemonStats();
        destino.setHp(origem.getHp());
        destino.setAttack(origem.getAttack());
        destino.setDefense(origem.getDefense());
        destino.setSpecialAttack(origem.getSpecialAttack());
        destino.setSpecialDefense(origem.getSpecialDefense());
        destino.setSpeed(origem.getSpeed());
        return destino;
    }

    private List<Integer> extrairIds(List<TypeRef> tipos) {
        List<Integer> ids = new ArrayList<>();
        for (TypeRef tipo : tipos) {
            ids.add(Integer.valueOf(tipo.id()));
        }
        return ids;
    }
}
