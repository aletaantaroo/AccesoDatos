package com.sistema.controlador;

import com.sistema.modelo.Empleado;
import javafx.scene.control.Alert;

public class Validaciones {

    public static boolean validarEmpleado(String nombre, String apellidos, String departamento, String sueldo) {
        if (nombre.isEmpty() || nombre.length() > 30) {
            mostrarError("El nombre no puede estar vacío y debe tener como máximo 30 caracteres.");
            return false;
        }
        if (apellidos.isEmpty() || apellidos.length() > 60) {
            mostrarError("Los apellidos no pueden estar vacíos y deben tener como máximo 60 caracteres.");
            return false;
        }
        if (departamento.isEmpty() || departamento.length() > 30) {
            mostrarError("El departamento no puede estar vacío y debe tener como máximo 30 caracteres.");
            return false;
        }
        try {
            double sueldoNum = Double.parseDouble(sueldo);
            if (sueldoNum < 0 || sueldoNum > 99999.99) {
                mostrarError("El sueldo debe estar entre 0 y 99,999.99.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El sueldo debe ser un número válido.");
            return false;
        }
        return true;
    }

    private static void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de validación");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
