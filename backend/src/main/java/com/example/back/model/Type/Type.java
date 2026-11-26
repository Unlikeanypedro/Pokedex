package com.example.back.model.Type;

import java.util.HashMap;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento salvo na coleção "types" do MongoDB.
 *
 * Cada tipo possui:
 * - ID;
 * - Nome;
 * - Efetividade contra os demais tipos (opcional, usado na expansão).
 *
 * Exemplo de como fica no MongoDB Atlas:
 *
 * {
 *   "_id": "5",
 *   "name": "Electric",
 *   "effectiveness": {
 *     "superEffective": [3, 10],
 *     "notVeryEffective": [5, 4, 16],
 *     "normal": [1, 2, 6, 7, 8, 11, 12, 13, 14, 15, 17],
 *     "noEffect": [9]
 *   }
 * }
 */

@Document(collection = "types")
public class Type {

    @Id
    private String id;

    private String name;

    private HashMap<String, List<Integer>> effectiveness;

    // Construtor vazio: usado pelo Spring/Jackson
    public Type() {}

    // Construtor completo
    public Type(
        String id,
        String name,
        HashMap<String, List<Integer>> effectiveness
    ) {
        this.id = id;
        this.name = name;
        this.effectiveness = effectiveness;
    }

    // Getters e Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, List<Integer>> getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(HashMap<String, List<Integer>> effectiveness) {
        this.effectiveness = effectiveness;
    }
}
