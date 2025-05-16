package com.evaluacion.evaluacion.model;
import java.util.List;
public class Estudiante {
    private String nombre;
    private String documento;
    private List<Integer> respuestas;
    private int nota;

    public Estudiante(String nombre, String documento, List<Integer> respuestas, int nota) {
        this.nombre = nombre;
        this.documento = documento;
        this.respuestas = respuestas;
        this.nota = nota;
    }

    public String getNombre() { return nombre; }
    public String getDocumento() { return documento; }
    public List<Integer> getRespuestas() { return respuestas; }
    public int getNota() { return nota; }

}
