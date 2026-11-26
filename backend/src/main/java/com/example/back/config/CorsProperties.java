package com.example.back.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Lê do application.properties tudo que começa com "app.cors":
 *
 * app.cors.allowed-origins=http://localhost:5173,https://seu-front.vercel.app
 *
 * O Spring separa os valores pela vírgula e monta a lista sozinho.
 */
@Configuration
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {

    private List<String> allowedOrigins = new ArrayList<>();

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}
