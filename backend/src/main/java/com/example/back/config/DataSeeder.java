package com.example.back.config;

import com.example.back.model.Pokemon.Pokemon;
import com.example.back.model.Trainer.Trainer;
import com.example.back.model.Trainer.TrainerTeam;
import com.example.back.model.Type.Type;
import com.example.back.repository.PokemonRepository;
import com.example.back.repository.TrainerRepository;
import com.example.back.repository.TrainerTeamRepository;
import com.example.back.repository.TypeRepository;
import java.io.InputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

/**
 * Popula o MongoDB na primeira execução com os dados do Script_Pokedex.sql
 * (arquivo seed/pokedex-seed.json, gerado a partir do script).
 *
 * É idempotente: se a coleção "pokemons" já tiver dados, nada é inserido.
 * Pode ser desativado com app.seed.enabled=false.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);
    private static final String ARQUIVO_SEED = "seed/pokedex-seed.json";

    private final PokemonRepository pokemonRepository;
    private final TypeRepository typeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainerTeamRepository trainerTeamRepository;

    @Value("${app.seed.enabled:true}")
    private boolean habilitado;

    public DataSeeder(
        PokemonRepository pokemonRepository,
        TypeRepository typeRepository,
        TrainerRepository trainerRepository,
        TrainerTeamRepository trainerTeamRepository
    ) {
        this.pokemonRepository = pokemonRepository;
        this.typeRepository = typeRepository;
        this.trainerRepository = trainerRepository;
        this.trainerTeamRepository = trainerTeamRepository;
    }

    @Override
    public void run(String... args) {
        if (!habilitado) {
            log.info("Seed desativado (app.seed.enabled=false).");
            return;
        }

        try {
            if (pokemonRepository.count() > 0) {
                log.info("Coleção de pokémons já possui dados; seed ignorado.");
                return;
            }

            SeedDados dados = lerSeed();

            typeRepository.saveAll(dados.types);
            pokemonRepository.saveAll(dados.pokemons);
            trainerRepository.saveAll(
                dados.trainers.stream().map(this::paraTreinador).toList()
            );
            trainerTeamRepository.saveAll(
                dados.trainers.stream().map(this::paraTime).toList()
            );

            log.info(
                "Seed concluído: {} tipos, {} pokémons, {} treinadores.",
                dados.types.size(),
                dados.pokemons.size(),
                dados.trainers.size()
            );
        } catch (Exception e) {
            // Não derruba a aplicação: se o Atlas estiver fora, a API sobe vazia
            log.warn(
                "Não foi possível popular o MongoDB automaticamente: {}",
                e.getMessage()
            );
        }
    }

    private SeedDados lerSeed() throws Exception {
        try (InputStream arquivo = new ClassPathResource(ARQUIVO_SEED).getInputStream()) {
            return JsonMapper.builder().build().readValue(arquivo, SeedDados.class);
        }
    }

    private Trainer paraTreinador(SeedTrainer dados) {
        return new Trainer(dados.id, dados.name, dados.badges);
    }

    private TrainerTeam paraTime(SeedTrainer dados) {
        return new TrainerTeam(dados.id, dados.id, dados.pokemonIds);
    }

    // ---------------------------------------------------------------------
    // Formato do arquivo seed/pokedex-seed.json
    // ---------------------------------------------------------------------

    public static class SeedDados {

        public List<Type> types;
        public List<Pokemon> pokemons;
        public List<SeedTrainer> trainers;
    }

    public static class SeedTrainer {

        public String id;
        public String name;
        public int badges;
        public List<String> pokemonIds;
    }
}
