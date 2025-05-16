package com.evaluacion.evaluacion.model;

public class Respuesta {
    private String nombre;
    private String id;
    private String[] respuestas;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String[] getRespuestas() { return respuestas; }
    public void setRespuestas(String[] respuestas) { this.respuestas = respuestas; }
}
