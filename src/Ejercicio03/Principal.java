package Ejercicio03;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Principal {
/**
 * M�todo que lee un fichero xml y lo muestra por pantalla.
 */
	public static void leerXML() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("agenda.xml"));
			
			document.getDocumentElement().normalize();
			System.out.println("Elemento ra�z: "+document.getDocumentElement().getNodeName());
			//Creamos una lista con todos los nodos contacto
			NodeList contactos = document.getElementsByTagName("contacto");
	
			for (int i = 0; i < contactos.getLength(); i++) {
				Node contacto = contactos.item(i); //Se obtiene un nodo
				Element elemento = (Element) contacto;
				System.out.println("Nombre: "+getNodo("nombre", elemento));
				System.out.println("Apellido: "+getNodo("apellido", elemento));
				System.out.println("Email: "+getNodo("email", elemento));
				System.out.println("Tel�fono: "+getNodo("telefono", elemento));
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * M�todo gen�rico que devuelve un nodo xml dependiendo de los par�metros que recibe.
	 * @param etiqueta
	 * @param elem
	 * @return
	 */
	public static String getNodo(String etiqueta, Element elem) {
		Node nodo = elem.getElementsByTagName(etiqueta).item(0).getFirstChild();
		return nodo.getTextContent();
	}

	public static void main(String[] args) {

		leerXML();

	}

}
