import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class AnalizadorDatosAbiertos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce la ruta del archivo a analizar:");
        String rutaArchivo = scanner.nextLine();
        if (rutaArchivo.endsWith(".csv")) {
            List<String[]> datosCSV = parsearCSV(rutaArchivo);
            mostrarResumenCSV(datosCSV);
        } else if (rutaArchivo.endsWith(".json")) {
            JsonElement datosJSON = parsearJSON(rutaArchivo);
            mostrarResumenJSON(datosJSON);
        } else if (rutaArchivo.endsWith(".xml")) {
            Document datosXML = parsearXML(rutaArchivo);
            mostrarResumenXML(datosXML);
        } else {
            System.out.println("Formato de archivo no soportado.");
        }
    }

    public static List<String[]> parsearCSV(String rutaArchivo) {
        List<String[]> registros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new
                FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                registros.add(valores);
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo CSV: " +
                    e.getMessage());
        }
        return registros;
    }


    public static JsonElement parsearJSON(String rutaArchivo) {
        JsonElement jsonElement = null;
        try (FileReader reader = new FileReader(rutaArchivo)) {
            jsonElement = new Gson().fromJson(reader, JsonElement.class);
        } catch (Exception e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
        return jsonElement;
    }


    public static Document parsearXML(String rutaArchivo) {
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(rutaArchivo);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo XML: " +
                    e.getMessage());
        }
        return doc;
    }

    public static void mostrarResumenCSV(List<String[]> datos) {
        if (datos.isEmpty()) {
            System.out.println("No se encontraron datos.");
            return;
        }
        System.out.println("Resumen del archivo CSV:");
        System.out.println("Número total de filas: " + datos.size());
        System.out.println("Número de columnas: " + datos.get(0).length);
        System.out.println("\nPrimeros 5 registros:");
        for (int i = 0; i < Math.min(5, datos.size()); i++) {
            System.out.println(String.join(" | ", datos.get(i)));
        }
    }

    public static void mostrarResumenJSON(JsonElement datos) {
        if (datos == null) {
            System.out.println("No se encontraron datos.");
            return;
        }

        if (datos.isJsonObject()) {
            JsonObject jsonObject = datos.getAsJsonObject();
            System.out.println("Resumen del archivo JSON:");
            for (String key : jsonObject.keySet()) {
                System.out.println(key + ": " + jsonObject.get(key).toString());
            }
        } else if (datos.isJsonArray()) {
            JsonArray jsonArray = datos.getAsJsonArray();
            System.out.println("Resumen del archivo JSON:");
            System.out.println("Número de elementos en el array: " + jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                System.out.println("Elemento "+ (i+1));
                for (String key : jsonArray.get(i).getAsJsonObject().keySet()) {
                    System.out.println(key + ": " + jsonArray.get(i).getAsJsonObject().get(key).toString());
                }
                System.out.println("\n");
            }
        } else {
            System.out.println("El archivo JSON no es ni un objeto ni un array.");
        }
    }


    public static void mostrarResumenXML(Document datos) {
        if (datos == null) {
            System.out.println("No se encontraron datos.");
            return;
        }

        Element rootElement = datos.getDocumentElement();
        System.out.println("Resumen del archivo XML:");
        System.out.println("Elemento raíz: " + rootElement.getNodeName());

        NodeList nodeList = datos.getElementsByTagName("*");
        System.out.println("Número total de nodos: " + nodeList.getLength());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);

            NodeList hijos = element.getChildNodes();
            boolean tieneElementosHijos = false;

            for (int j = 0; j < hijos.getLength(); j++) {
                if (hijos.item(j) instanceof Element) {
                    tieneElementosHijos = true;
                    break;
                }
            }

            if (!tieneElementosHijos && !element.getTextContent().trim().isEmpty()) {
                System.out.println(element.getNodeName() + " -> " + element.getTextContent().trim());
            }
        }
    }

}