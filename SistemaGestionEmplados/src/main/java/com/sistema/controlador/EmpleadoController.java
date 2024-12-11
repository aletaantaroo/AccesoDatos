package com.sistema.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.sistema.modelo.Empleado;

import java.io.*;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class EmpleadoController {

    @FXML
    private TableColumn<Empleado, Integer> colId;

    @FXML
    private TableColumn<Empleado, String> colNombre;

    @FXML
    private TableColumn<Empleado, String> colApellidos;

    @FXML
    private TableColumn<Empleado, String> colDepartamento;

    @FXML
    private TableColumn<Empleado, Double> colSueldo;

    @FXML
    private TableView<Empleado> tablaEmpleados;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellidosField;

    @FXML
    private TextField departamentoField;

    @FXML
    private TextField sueldoField;

    private ObservableList<Empleado> listaEmpleados;

    private int nextId = 1; // Para asignar IDs automáticos

    @FXML
    private void initialize() {
        listaEmpleados = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));

        tablaEmpleados.setItems(listaEmpleados);
    }

    @FXML
    private void insertarEmpleado() {
        try {
            String nombre = nombreField.getText();
            String apellidos = apellidosField.getText();
            String departamento = departamentoField.getText();
            double sueldo = Double.parseDouble(sueldoField.getText());

            Empleado nuevoEmpleado = new Empleado(nextId++, nombre, apellidos, departamento, sueldo);
            listaEmpleados.add(nuevoEmpleado);

            limpiarCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de entrada", "El sueldo debe ser un número válido.");
        }
    }

    @FXML
    private void actualizarEmpleado() {
        Empleado seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                seleccionado.setNombre(nombreField.getText());
                seleccionado.setApellidos(apellidosField.getText());
                seleccionado.setDepartamento(departamentoField.getText());
                seleccionado.setSueldo(Double.parseDouble(sueldoField.getText()));

                tablaEmpleados.refresh();
                limpiarCampos();
            } catch (NumberFormatException e) {
                mostrarAlerta("Error de entrada", "El sueldo debe ser un número válido.");
            }
        } else {
            mostrarAlerta("Seleccionar empleado", "Por favor, selecciona un empleado para actualizar.");
        }
    }

    @FXML
    private void borrarEmpleado() {
        Empleado seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaEmpleados.remove(seleccionado);
            limpiarCampos();
        } else {
            mostrarAlerta("Seleccionar empleado", "Por favor, selecciona un empleado para borrar.");
        }
    }

    @FXML
    private void exportarXML() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("empleados.xml"))) {
            // Escribimos la cabecera del archivo XML
            writer.write("<empleados>\n");

            // Iteramos sobre la lista de empleados y escribimos cada uno en formato XML
            for (Empleado empleado : listaEmpleados) {
                writer.write("<empleado>\n");
                writer.write(String.format("<id>%d</id>\n", empleado.getId()));
                writer.write(String.format("<nombre>%s</nombre>\n", empleado.getNombre()));
                writer.write(String.format("<apellidos>%s</apellidos>\n", empleado.getApellidos()));
                writer.write(String.format("<departamento>%s</departamento>\n", empleado.getDepartamento()));
                writer.write(String.format("<sueldo>%.2f</sueldo>\n", empleado.getSueldo()));
                writer.write("</empleado>\n");
            }

            // Escribimos el cierre de la etiqueta root
            writer.write("</empleados>\n");

            // Alerta de éxito
            mostrarAlerta("Exportación completada", "Datos exportados a empleados.xml correctamente.");
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al exportar datos a XML.");
            e.printStackTrace(); // Para ver el error en la consola
        }
    }


    @FXML
    private void importarXML() {
        File archivoXML = new File("empleados.xml");
        if (archivoXML.exists()) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(archivoXML);

                NodeList nodeList = document.getElementsByTagName("empleado");
                listaEmpleados.clear(); // Limpiamos la lista actual

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element elemento = (Element) node;

                        int id = Integer.parseInt(elemento.getElementsByTagName("id").item(0).getTextContent());
                        String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                        String apellidos = elemento.getElementsByTagName("apellidos").item(0).getTextContent();
                        String departamento = elemento.getElementsByTagName("departamento").item(0).getTextContent();
                        double sueldo = Double.parseDouble(elemento.getElementsByTagName("sueldo").item(0).getTextContent());

                        Empleado empleado = new Empleado(id, nombre, apellidos, departamento, sueldo);
                        listaEmpleados.add(empleado);
                    }
                }

                mostrarAlerta("Importación completada", "Datos importados desde empleados.xml correctamente.");
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al importar datos desde XML.");
            }
        } else {
            mostrarAlerta("Error", "El archivo empleados.xml no existe.");
        }
    }


    @FXML
    private void exportarJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File archivoJSON = new File("empleados.json");
            // Asegúrate de que el archivo se crea o se sobrescribe
            mapper.writeValue(archivoJSON, listaEmpleados);
            mostrarAlerta("Exportación completada", "Datos exportados a empleados.json correctamente.");
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al exportar datos a JSON.");
        }
    }

    @FXML
    private void importarJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Usando InputStream para leer el archivo desde los recursos empaquetados
            InputStream inputStream = getClass().getResourceAsStream("/empleados.json");

            // Si el InputStream es null, el archivo no se encuentra
            if (inputStream == null) {
                mostrarAlerta("Error", "El archivo empleados.json no se encuentra.");
                return;
            }

            List<Empleado> empleadosImportados = mapper.readValue(inputStream, new TypeReference<List<Empleado>>() {});

            // Limpia la lista de empleados y agrega los empleados importados
            listaEmpleados.clear();
            listaEmpleados.addAll(empleadosImportados);

            // Muestra una alerta indicando que la importación fue exitosa
            mostrarAlerta("Importación completada", "Datos importados desde empleados.json correctamente.");
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al importar datos desde JSON.");
            e.printStackTrace(); // Muestra detalles del error en la consola
        }
    }



    private void limpiarCampos() {
        nombreField.clear();
        apellidosField.clear();
        departamentoField.clear();
        sueldoField.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
