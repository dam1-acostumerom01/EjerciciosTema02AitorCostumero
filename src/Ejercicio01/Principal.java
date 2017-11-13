package Ejercicio01;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class Principal {
	//ruta del fichero.
	private static final String sfichero = "agenda.dat";

	// Método que pide por teclado un entero
	public static int pedirInt(String frase) {
		System.out.println(frase);
		Scanner entrada = new Scanner(System.in);
		int entero = 0;
		try {
			entero = entrada.nextInt();
		} catch (Exception ex) {
			System.out.println("Debes introducir un número.");
		}
		return entero;
	}

	// Método que pide una cadena por teclado
	public static String pedirString(String frase) {
		System.out.println(frase);
		Scanner entrada = new Scanner(System.in);
		return entrada.nextLine();
	}

	// método que devuelve un entero y que muestra las opciones del menú.
	public static int menu() {
		Scanner entrada = new Scanner(System.in);
		System.out.println("Escoge una opción:");
		System.out.println("1. Crear un contacto.");
		System.out.println("2. Salir.");
		return entrada.nextInt();
	}

	// Método que gestiona las opciones del menú
	public static void gestionarMenu(File fichero) {
		int opcion;
		do {
			opcion = menu();
			switch (opcion) {
			case 1:
				escribirFichero(fichero);
				break;
			}
		} while (opcion != 2);
		System.out.println("Gracias por usar nuestro programa.");
	}

	// Método que escribe un registro en un fichero
	public static void escribirFichero(File fichero) {

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			// Si el fichero no existe, lo creamos y escribimos
			if (!fichero.exists()) {
				fos = new FileOutputStream(fichero);
				oos = new ObjectOutputStream(fos);

			} else {
				// Si el fichero existe, seguimos escribiendo en él sin sobreescribir cabeceras
				fos = new FileOutputStream(fichero, true);
				oos = new MiObjectOutputStream(fos);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado.");
		} catch (IOException e) {
			System.out.println("Error entrada/salida.");
		}

		try {
			// Creamos un contacto aux y lo escribimos en el fichero.
			Ejercicio01.Contacto aux = new Ejercicio01.Contacto(pedirString("Nombre:"), pedirString("Apellidos:"),
					pedirString("Email:"), pedirString("Teléfono:"));

			oos.writeObject(aux);
			System.out.println("Contacto creado con éxito.");

		} catch (IOException e) {
			System.out.println("Error entrada/salida.");
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Método que crea un documento xml con el método DOM
	 */
	public static void crearContactosXML() {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "contactos", null);
			document.setXmlVersion("1.0");

			ArrayList<Ejercicio01.Contacto> contactos = getContactosFichero();

			Iterator it = contactos.iterator();
			while (it.hasNext()) {
				Ejercicio01.Contacto aux = (Ejercicio01.Contacto) it.next();
				Element nodo = document.createElement("contacto");

				document.getDocumentElement().appendChild(nodo);

				crearElemento("nombre", aux.getNombre(), nodo, document);
				crearElemento("apellido", aux.getApellido(), nodo, document);
				crearElemento("email", aux.getEmail(), nodo, document);
				crearElemento("telefono", aux.getTelefono(), nodo, document);
			}

			Source source = new DOMSource(document);
			Result result = new StreamResult(new java.io.File("agenda.xml"));

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Método que lee un fichero y devuelve un arraylist de objetos de tipo contacto.
	 * @return
	 */
	public static ArrayList<Ejercicio01.Contacto> getContactosFichero() {
		ArrayList<Ejercicio01.Contacto> contactos = new ArrayList<>();

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Ejercicio01.Contacto aux;
		File fichero = new File(sfichero);

		try {
			fis = new FileInputStream(fichero);
			ois = new ObjectInputStream(fis);
			aux = (Ejercicio01.Contacto) ois.readObject();
			while (aux != null) {
				contactos.add(aux);
				aux = (Ejercicio01.Contacto) ois.readObject();

			}
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado.");
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return contactos;
	}

	
	/**
	 * Método que crea elementos que formarán parte del xml
	 * @param elemento
	 * @param valor
	 * @param nodo
	 * @param document
	 */
	static void crearElemento(String elemento, String valor, Element nodo, Document document) {
		Element elem = document.createElement(elemento);
		Text text = document.createTextNode(valor);
		elem.appendChild(text);
		nodo.appendChild(elem);
	}

	public static void main(String[] args) {
		File fichero = new File(sfichero);
		 gestionarMenu(fichero);
		//crearContactosXML();

	}
}
