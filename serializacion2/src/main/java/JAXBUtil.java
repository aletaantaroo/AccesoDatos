import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

public class JAXBUtil {

    // Metodo para serializar un objeto Curso a XML
    public static void marshal(Curso curso, File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Curso.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(curso, file);
    }

    // Metodo para deserializar un objeto Curso desde XML
    public static Curso unmarshal(File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Curso.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Curso) unmarshaller.unmarshal(file);
    }
}
