package Ejercicio06;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class Principal {

	
/**
 * Método que escribe un fichero xml con Stax.
 * controlamos que se puedan añadir más de un autor en cada nodo libro.	
 */
	public static void escribirXML() {
		try {
			FileOutputStream fos = new FileOutputStream("variosautores.xml");
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlStream = factory.createXMLStreamWriter(fos, "UTF-8");

			xmlStream.writeStartDocument();
			xmlStream.writeStartElement("libros");

			xmlStream.writeStartElement("libro");
			xmlStream.writeAttribute("ISBN", "239-87-9964-088-4");

			xmlStream.writeStartElement("titulo");
			xmlStream.writeCharacters("Acceso a Datos");
			xmlStream.writeEndElement();

			xmlStream.writeStartElement("autores");
			xmlStream.writeStartElement("autor");
			xmlStream.writeCharacters("Alicia Ramos");
			xmlStream.writeEndElement();
			xmlStream.writeStartElement("autor");
			xmlStream.writeCharacters("Maria Jesús Ramos Garcia");
			xmlStream.writeEndElement(); // cierro autor
			xmlStream.writeEndElement(); // cierro autores

			xmlStream.writeStartElement("editorial");
			xmlStream.writeCharacters("Garceta");
			xmlStream.writeEndElement();

			xmlStream.writeEndElement(); // cierro libro
			xmlStream.writeEndElement(); // cierro libros
			xmlStream.writeEndDocument(); // cierro documento
			
			xmlStream.flush();
			xmlStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		escribirXML();
	}
}
