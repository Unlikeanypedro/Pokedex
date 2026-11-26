package com.example.back.repository;

import com.example.back.model.Type.Type;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Acesso à coleção "types". Usado para montar a lista de tipos do filtro e
 * para resolver os nomes dos tipos de cada Pokémon.
 */
@Repository
public interface TypeRepository extends MongoRepository<Type, String> {}
