package com.evaluacion.evaluacion.model;

import java.util.List;

public class Pregunta {
    private int id;
    private String enunciado;
    private List<String> opciones;
    private int correcta;

    public Pregunta(int id, String enunciado, List<String> opciones, int correcta) {
        this.id = id;
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.correcta = correcta;
    }

    public int getId() {
        return id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public int getCorrecta() {
        return correcta;
    }
}
