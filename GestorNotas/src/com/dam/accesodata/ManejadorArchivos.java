package com.dam.accesodata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorArchivos {
    private static final String ARCHIVO = "resources/notas_estudiantes.txt";

    public void añadirEstudiante(Estudiante estudiante) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(estudiante.toString());
            writer.newLine();
            System.out.println("Estudiante añadido: " + estudiante);
        } catch (IOException e) {
            System.err.println("Error al añadir estudiante: " + e.getMessage());
        }
    }
    public void mostrarEstudiantes() {
        List<Estudiante> estudiantes = leerEstudiantes();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
        } else {
            System.out.println("----- Lista de Estudiantes -----");
            for (Estudiante estudiante : estudiantes) {
                System.out.println(estudiante);
            }
        }
    }

    public void buscarEstudiante(String nombre) {
        List<Estudiante> estudiantes = leerEstudiantes();
        boolean encontrado = false;
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.nombre().equalsIgnoreCase(nombre)) {
                System.out.println("Estudiante encontrado: " + estudiante);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Estudiante no encontrado.");
        }
    }

    public void calcularMedia() {
        List<Estudiante> estudiantes = leerEstudiantes();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        double suma = 0;
        for (Estudiante estudiante : estudiantes) {
            suma += estudiante.nota();
        }
        double media = suma / estudiantes.size();
        System.out.printf("La nota media de los estudiantes es: %.2f%n", media);
    }

    private List<Estudiante> leerEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String nombre = partes[0];
                    double nota = Double.parseDouble(partes[1]);
                    estudiantes.add(new Estudiante(nombre, nota));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer estudiantes: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir la nota: " + e.getMessage());
        }
        return estudiantes;
    }
}