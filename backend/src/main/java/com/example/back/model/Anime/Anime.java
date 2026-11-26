package com.example.back.model.Anime;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento salvo na coleção "animes" do MongoDB.
 *
 * Representa uma série completa do anime Pokémon,
 * e não episódios individuais.
 *
 * A divisão principal utilizada é a organização japonesa,
 * onde o anime é separado em diferentes séries contínuas.
 *
 * Cada anime possui:
 * - ID;
 * - Nome japonês/internacional da série;
 * - Nome ocidental;
 * - Período de exibição;
 * - Região principal;
 * - Lista de temporadas correspondentes no Ocidente.
 *
 * Exemplo de como fica no MongoDB Atlas:
 *
 * {
 *   "_id": "pocket-monsters-advanced-generation",
 *   "nome": "Pocket Monsters Advanced Generation",
 *   "nomeOcidental": "Pokémon the Series: Ruby and Sapphire",
 *   "anoInicio": 2002,
 *   "anoFim": 2006,
 *   "regiao": "Hoenn",
 *   "temporadasOcidentais": [
 *       "Advanced",
 *       "Advanced Challenge",
 *       "Advanced Battle",
 *       "Battle Frontier"
 *   ]
 * }
 */
@Document(collection = "animes")
public class Anime {

    @Id
    private String id;

    private String nome;

    private String nomeOcidental;

    private int anoInicio;

    private int anoFim;

    private String regiao;

    private List<String> temporadasOcidentais;

    public Anime() {}

    public Anime(
        String id,
        String nome,
        String nomeOcidental,
        int anoInicio,
        int anoFim,
        String regiao,
        List<String> temporadasOcidentais
    ) {
        this.id = id;
        this.nome = nome;
        this.nomeOcidental = nomeOcidental;
        this.anoInicio = anoInicio;
        this.anoFim = anoFim;
        this.regiao = regiao;
        this.temporadasOcidentais = temporadasOcidentais;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeOcidental() {
        return nomeOcidental;
    }

    public void setNomeOcidental(String nomeOcidental) {
        this.nomeOcidental = nomeOcidental;
    }

    public int getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(int anoInicio) {
        this.anoInicio = anoInicio;
    }

    public int getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(int anoFim) {
        this.anoFim = anoFim;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public List<String> getTemporadasOcidentais() {
        return temporadasOcidentais;
    }

    public void setTemporadasOcidentais(List<String> temporadasOcidentais) {
        this.temporadasOcidentais = temporadasOcidentais;
    }
}
