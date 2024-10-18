package com.dam.accesodata;

public record Estudiante(String nombre, double nota) {
    @Override
    public String toString() {
        return nombre + ", " + nota;
    }
}