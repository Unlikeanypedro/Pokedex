package com.example.back.controller;

import com.example.back.service.StatusService;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Diz se a API está no ar e se o MongoDB Atlas responde.
 *
 * GET /api e GET /api/status retornam o status da API e do MongoDB.
 * No deploy integrado, / serve o frontend.
 *
 * { "api": "online", "mongodb": "online" }
 */
@RestController
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping({ "/api", "/api/status" })
    public Map<String, String> status() {
        Map<String, String> resposta = new LinkedHashMap<>();
        resposta.put("api", "online");
        resposta.put(
            "mongodb",
            statusService.mongoDbOnline() ? "online" : "offline"
        );
        return resposta;
    }
}
