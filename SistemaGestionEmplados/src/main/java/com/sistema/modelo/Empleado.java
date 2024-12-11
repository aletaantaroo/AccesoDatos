package com.sistema.modelo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.*;

import java.io.Serializable;

/**
 * Clase que representa un empleado en el sistema.
 * Esta clase contiene información básica sobre un empleado como su ID, nombre, apellidos,
 * departamento y sueldo.
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class Empleado implements Serializable {

    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty apellidos;
    private final StringProperty departamento;
    private final DoubleProperty sueldo;

    /**
     * Constructor que inicializa un objeto Empleado con los valores proporcionados.
     * Este constructor se utiliza para deserializar datos desde un formato JSON, gracias a la
     * anotación de Jackson.
     *
     * @param id El ID del empleado.
     * @param nombre El nombre del empleado.
     * @param apellidos Los apellidos del empleado.
     * @param departamento El departamento al que pertenece el empleado.
     * @param sueldo El sueldo del empleado.
     */
    @JsonCreator
    public Empleado(
            @JsonProperty("id") int id,
            @JsonProperty("nombre") String nombre,
            @JsonProperty("apellidos") String apellidos,
            @JsonProperty("departamento") String departamento,
            @JsonProperty("sueldo") double sueldo
    ) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidos = new SimpleStringProperty(apellidos);
        this.departamento = new SimpleStringProperty(departamento);
        this.sueldo = new SimpleDoubleProperty(sueldo);
    }

    /**
     * Obtiene el ID del empleado.
     *
     * @return El ID del empleado.
     */
    public int getId() {
        return id.get();
    }

    /**
     * Establece el ID del empleado.
     *
     * @param id El nuevo ID del empleado.
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Obtiene la propiedad IntegerProperty del ID del empleado.
     *
     * @return La propiedad IntegerProperty del ID.
     */
    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * Obtiene el nombre del empleado.
     *
     * @return El nombre del empleado.
     */
    public String getNombre() {
        return nombre.get();
    }

    /**
     * Establece el nombre del empleado.
     *
     * @param nombre El nuevo nombre del empleado.
     */
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    /**
     * Obtiene la propiedad StringProperty del nombre del empleado.
     *
     * @return La propiedad StringProperty del nombre.
     */
    public StringProperty nombreProperty() {
        return nombre;
    }

    /**
     * Obtiene los apellidos del empleado.
     *
     * @return Los apellidos del empleado.
     */
    public String getApellidos() {
        return apellidos.get();
    }

    /**
     * Establece los apellidos del empleado.
     *
     * @param apellidos Los nuevos apellidos del empleado.
     */
    public void setApellidos(String apellidos) {
        this.apellidos.set(apellidos);
    }

    /**
     * Obtiene la propiedad StringProperty de los apellidos del empleado.
     *
     * @return La propiedad StringProperty de los apellidos.
     */
    public StringProperty apellidosProperty() {
        return apellidos;
    }

    /**
     * Obtiene el departamento al que pertenece el empleado.
     *
     * @return El departamento del empleado.
     */
    public String getDepartamento() {
        return departamento.get();
    }

    /**
     * Establece el departamento del empleado.
     *
     * @param departamento El nuevo departamento del empleado.
     */
    public void setDepartamento(String departamento) {
        this.departamento.set(departamento);
    }

    /**
     * Obtiene la propiedad StringProperty del departamento del empleado.
     *
     * @return La propiedad StringProperty del departamento.
     */
    public StringProperty departamentoProperty() {
        return departamento;
    }

    /**
     * Obtiene el sueldo del empleado.
     *
     * @return El sueldo del empleado.
     */
    public double getSueldo() {
        return sueldo.get();
    }

    /**
     * Establece el sueldo del empleado.
     *
     * @param sueldo El nuevo sueldo del empleado.
     */
    public void setSueldo(double sueldo) {
        this.sueldo.set(sueldo);
    }

    /**
     * Obtiene la propiedad DoubleProperty del sueldo del empleado.
     *
     * @return La propiedad DoubleProperty del sueldo.
     */
    public DoubleProperty sueldoProperty() {
        return sueldo;
    }

    /**
     * Método para exportar la información del empleado a formato XML.
     * Este método genera una representación en formato XML de los datos del empleado.
     *
     * @return La representación XML del empleado.
     */
    public String toXML() {
        return String.format(
                "<empleado><id>%d</id><nombre>%s</nombre><apellidos>%s</apellidos><departamento>%s</departamento><sueldo>%.2f</sueldo></empleado>\n",
                id.get(), nombre.get(), apellidos.get(), departamento.get(), sueldo.get()
        );
    }
}
