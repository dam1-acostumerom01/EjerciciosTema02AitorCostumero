package Ejercicio04;

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
	 * Método que lee un fichero xml y lo muestra por pantalla.
	 */
	public static void leerXML() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("libros.xml"));
			
			document.getDocumentElement().normalize();
			System.out.println("Elemento raíz: "+document.getDocumentElement().getNodeName());
			
			//Creamos una lista con todos los nodos contacto
			NodeList libros = document.getElementsByTagName("libro");
	
			for (int i = 0; i < libros.getLength(); i++) {
				Node libro = libros.item(i); //Se obtiene un nodo
				Element elemento = (Element) libro;
				System.out.println("ISBN: "+elemento.getAttribute("ISBN")); //Obtenemos el atributo del nodo libro
				System.out.println("Titulo: "+getNodo("Titulo", elemento));
				System.out.println("Autor: "+getNodo("Autor", elemento));
				System.out.println("Editorial: "+getNodo("Editorial", elemento));
				
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
	 * Método genérico que devuelve un nodo xml dependiendo de los parámetros que recibe.
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
