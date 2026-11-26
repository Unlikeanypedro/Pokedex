package com.example.back.service;

import com.example.back.dto.TypeRef;
import com.example.back.repository.TypeRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/** Lista os tipos cadastrados, usados no filtro e no formulário do front-end. */
@Service
public class TypeService {

    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<TypeRef> listar() {
        return typeRepository
            .findAll(Sort.by("name"))
            .stream()
            .map(tipo -> new TypeRef(tipo.getId(), tipo.getName()))
            .toList();
    }
}
