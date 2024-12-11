module com.sistemagestionemplados {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base; // Asegúrate de que este también está presente si lo usas
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports com.sistema; // Exporta el paquete com.sistema si necesitas acceso a clases generales
    exports com.sistema.controlador; // Exporta el paquete com.sistema.controlador

    opens com.sistema.controlador to javafx.fxml; // Abre el paquete para que FXML pueda acceder

    // Abre el paquete com.sistema.modelo tanto a javafx.base como a com.fasterxml.jackson.databind para reflexión
    opens com.sistema.modelo to javafx.base, com.fasterxml.jackson.databind;
}
