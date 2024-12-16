import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Crear estudiantes
            Estudiante est1 = new Estudiante("Juan", 20);
            Estudiante est2 = new Estudiante("Ana", 22);

            // Crear curso
            List<Estudiante> estudiantes = new ArrayList<>();
            estudiantes.add(est1);
            estudiantes.add(est2);
            Curso curso = new Curso("Matemáticas", estudiantes);

            // Serializar a XML
            File file = new File("curso.xml");
            JAXBUtil.marshal(curso, file);

            // Deserializar desde XML
            Curso cursoDeserializado = JAXBUtil.unmarshal(file);
            System.out.println("Curso: " + cursoDeserializado.getNombreCurso());
            for (Estudiante estudiante : cursoDeserializado.getEstudiantes()) {
                System.out.println("Estudiante: " + estudiante.getNombre() + ", Edad: " + estudiante.getEdad());
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
