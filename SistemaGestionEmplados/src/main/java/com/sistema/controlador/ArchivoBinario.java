package com.sistema.controlador;

import com.sistema.modelo.Empleado;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoBinario {

    private static final String FICHERO_BINARIO = "empleados.dat";

    public static List<Empleado> cargarEmpleados() {
        File file = new File(FICHERO_BINARIO);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Empleado> lista = (List<Empleado>) ois.readObject();
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void guardarEmpleados(List<Empleado> empleados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO_BINARIO))) {
            oos.writeObject(new ArrayList<>(empleados)); // Serializamos la lista
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

