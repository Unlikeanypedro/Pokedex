package com.example.back.repository;

import com.example.back.model.Pokemon;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends MongoRepository<Pokemon, String> {
    // Métodos de CRUD já estão disponíveis:
    // findAll, findById, save, deleteById, existsById

    // Consulta personalizada: alunos cujo nome contém o texto (sem diferenciar maiúsculas)
    List<Pokemon> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
}
