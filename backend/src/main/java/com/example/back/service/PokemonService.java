package com.example.back.service;

import com.example.back.exception.DadosInvalidosException;
import com.example.back.exception.PokemonNaoEncontradoException;
import com.example.back.model.Pokemon.Pokemon;
import com.example.back.repository.PokemonRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Regras de negócio do CRUD de pokemons.
 * O controller recebe a requisição HTTP e o service decide o que fazer com o MongoDB.
 */
@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    // Injeção de dependência pelo construtor (equivale ao @Autowired no atributo)
    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    /** Lista todos os pokemons (ordem alfabética). Com "nome", filtra pelos que contêm o texto. */
    public List<Pokemon> listar(String nome) {
        if (nome == null || nome.isBlank()) {
            return pokemonRepository.findAll(Sort.by("nome"));
        }
        return pokemonRepository.findByNomeContainingIgnoreCaseOrderByNomeAsc(
            nome.trim()
        );
    }

    public Pokemon buscarPorId(String id) {
        return pokemonRepository
            .findById(id)
            .orElseThrow(() -> new PokemonNaoEncontradoException(id));
    }

    public Pokemon inserir(Pokemon pokemon) {
        validar(pokemon);
        pokemon.setId(null); // quem gera o id é o MongoDB
        limpar(pokemon);
        return pokemonRepository.save(pokemon);
    }

    public Pokemon atualizar(String id, Pokemon dados) {
        Pokemon existente = buscarPorId(id); // lança 404 se não existir
        validar(dados);

        existente.setNome(dados.getNome());
        //TODO  Criar os metodos de atualizar para todos os campos de pokemon

        limpar(existente);

        return pokemonRepository.save(existente);
    }

    public void excluir(String id) {
        if (!pokemonRepository.existsById(id)) {
            throw new PokemonNaoEncontradoException(id);
        }
        pokemonRepository.deleteById(id);
    }

    /** Validação simples dos campos (lança 400 pelo GlobalExceptionHandler). */
    private void validar(Pokemon pokemon) {
        if (pokemon == null) {
            throw new DadosInvalidosException(
                "Envie os dados do pokemon no corpo da requisição."
            );
        }
        if (pokemon.getNome() == null || pokemon.getNome().isBlank()) {
            throw new DadosInvalidosException(
                "O nome do pokemon é obrigatório."
            );
        }

        //TODO  Criar os metodos de validação para todos os campos de pokemon
    }

    /** Remove espaços sobrando no começo e no fim dos textos. */
    private void limpar(Pokemon pokemon) {
        pokemon.setNome(pokemon.getNome().trim());
        //TODO  Criar os metodos de limpar para todos os campos de pokemon
    }
}
