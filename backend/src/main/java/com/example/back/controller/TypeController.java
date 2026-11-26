package com.example.back.controller;

import com.example.back.dto.TypeRef;
import com.example.back.service.TypeService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** GET /api/types -> lista de tipos (id + nome). */
@RestController
@RequestMapping("/api/types")
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public List<TypeRef> listar() {
        return typeService.listar();
    }
}
