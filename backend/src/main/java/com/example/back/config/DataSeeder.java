package com.example.back.config;

import com.example.back.model.Pokemon.Pokemon;
import com.example.back.model.Trainer.Trainer;
import com.example.back.model.Trainer.TrainerTeam;
import com.example.back.model.Type.Type;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import java.io.InputStream;
import java.util.List;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

/**
 * Popula o MongoDB na primeira execução com os dados do Script_Pokedex.sql
 * (arquivo seed/pokedex-seed.json, gerado a partir do script).
 *
 * Registra a conclusão da carga no banco. Uma carga interrompida pode
 * continuar na próxima inicialização, inserindo apenas documentos ausentes.
 * Pode ser desativado com app.seed.enabled=false.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);
    private static final String ARQUIVO_SEED = "seed/pokedex-seed.json";
    private static final String COLECAO_CONTROLE = "seed_metadata";
    private static final String ID_SEED = "pokedex-sql-v1";

    private final MongoTemplate mongoTemplate;

    @Value("${app.seed.enabled:true}")
    private boolean habilitado;

    public DataSeeder(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) {
        if (!habilitado) {
            log.info("Seed desativado (app.seed.enabled=false).");
            return;
        }

        try {
            MongoCollection<Document> controle = mongoTemplate.getCollection(COLECAO_CONTROLE)
                .withReadPreference(ReadPreference.primary());
            if (controle.find(Filters.eq("_id", ID_SEED)).first() != null) {
                log.info("Carga inicial já concluída.");
                return;
            }

            SeedDados dados = lerSeed();
            List<TrainerTeam> times = dados.trainers.stream().map(this::paraTime).toList();

            // A versão anterior gravava os times por último. Se todos estiverem
            // presentes, registra a carga anterior e preserva as exclusões.
            if (todosTimesPresentes(times)) {
                registrarConclusao(controle);
                log.info("Carga inicial existente registrada.");
                return;
            }

            inserirAusentes(Type.class, dados.types);
            inserirAusentes(Pokemon.class, dados.pokemons);
            inserirAusentes(
                Trainer.class,
                dados.trainers.stream().map(this::paraTreinador).toList()
            );
            inserirAusentes(TrainerTeam.class, times);
            registrarConclusao(controle);

            log.info(
                "Carga inicial concluída: {} tipos, {} pokémons, {} treinadores.",
                dados.types.size(),
                dados.pokemons.size(),
                dados.trainers.size()
            );
        } catch (Exception e) {
            // A API continua disponível para consultar o status do banco.
            log.warn(
                "Não foi possível popular o MongoDB automaticamente: {}",
                e.getMessage()
            );
        }
    }

    private boolean todosTimesPresentes(List<TrainerTeam> times) {
        List<String> ids = times.stream().map(TrainerTeam::getId).toList();
        return colecao(TrainerTeam.class)
            .withReadPreference(ReadPreference.primary())
            .countDocuments(Filters.in("_id", ids)) == ids.size();
    }

    private void registrarConclusao(MongoCollection<Document> controle) {
        controle.updateOne(
            Filters.eq("_id", ID_SEED),
            new Document("$setOnInsert", new Document("concluido", true)),
            new UpdateOptions().upsert(true)
        );
    }

    private <T> void inserirAusentes(Class<T> classe, List<T> entidades) {
        List<WriteModel<Document>> operacoes = entidades.stream()
            .map(entidade -> inserirSeAusente(entidade))
            .toList();

        if (!operacoes.isEmpty()) {
            colecao(classe).bulkWrite(operacoes);
        }
    }

    private WriteModel<Document> inserirSeAusente(Object entidade) {
        Document documento = new Document();
        mongoTemplate.getConverter().write(entidade, documento);
        Object id = documento.remove("_id");
        return new UpdateOneModel<>(
            Filters.eq("_id", id),
            new Document("$setOnInsert", documento),
            new UpdateOptions().upsert(true)
        );
    }

    private MongoCollection<Document> colecao(Class<?> classe) {
        return mongoTemplate.getCollection(mongoTemplate.getCollectionName(classe));
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
