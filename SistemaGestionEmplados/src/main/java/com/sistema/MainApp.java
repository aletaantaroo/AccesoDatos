package com.sistema;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Asegúrate de que el archivo FXML esté en la carpeta correcta dentro de tus recursos.
        // Suponiendo que el archivo FXML se encuentra en /vista/EmpleadoView.fxml
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/vista/EmpleadoView.fxml")));
        primaryStage.setTitle("Gestión de Empleados");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
