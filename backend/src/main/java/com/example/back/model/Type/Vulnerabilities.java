package com.example.back.model.Type;

import java.util.List;

public class Vulnerabilities {

    private List<Integer> superEffective;
    private List<Integer> notVeryEffective;
    private List<Integer> normal;
    private List<Integer> noEffect;

    // Construtor vazio: usado pelo Spring/Jackson para montar o objeto a partir do JSON
    public Vulnerabilities() {}

    // Construtor completo
    public Vulnerabilities(
        List<Integer> superEffective,
        List<Integer> notVeryEffective,
        List<Integer> normal,
        List<Integer> noEffect
    ) {
        this.superEffective = superEffective;
        this.notVeryEffective = notVeryEffective;
        this.normal = normal;
        this.noEffect = noEffect;
    }

    // Getters e Setters

    public List<Integer> getSuperEffective() {
        return superEffective;
    }

    public void setSuperEffective(List<Integer> superEffective) {
        this.superEffective = superEffective;
    }

    public List<Integer> getNotVeryEffective() {
        return notVeryEffective;
    }

    public void setNotVeryEffective(List<Integer> notVeryEffective) {
        this.notVeryEffective = notVeryEffective;
    }

    public List<Integer> getNormal() {
        return normal;
    }

    public void setNormal(List<Integer> normal) {
        this.normal = normal;
    }

    public List<Integer> getNoEffect() {
        return noEffect;
    }

    public void setNoEffect(List<Integer> noEffect) {
        this.noEffect = noEffect;
    }
}
