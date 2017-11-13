package Ejercicio02;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Principal {

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
			System.out.println("1. Crear un libro.");
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
				// Creamos un libro aux y lo escribimos en el fichero.
				Libro aux = new Libro(pedirString("ISBN:"), pedirString("Título:"), pedirString("Autor:"),
						pedirString("Editorial:"));

				oos.writeObject(aux);
				System.out.println("Libro creado con éxito.");

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
		 * Método que crea un fichero xml de libros.
		 * @param fichero
		 */
		public static void crearLibrosXML(File fichero) {
			try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
				builder = factory.newDocumentBuilder();
			
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "Libros", null);
			document.setXmlVersion("1.0");
			
			ArrayList <Libro> libros = getLibrosFichero(fichero);
			
			//Iterador que recorre los libros y genera los elementos del xml
			Iterator it = libros.iterator();
			while (it.hasNext()) {
				Libro aux = (Libro) it.next();
				Element nodo = document.createElement("libro");
				nodo.setAttribute("ISBN", aux.getISBN());  //Insertamos un atributo al nodo libro.
				document.getDocumentElement().appendChild(nodo);
				CrearElemento("Titulo",aux.getTitulo(),nodo,document);
				CrearElemento("Autor",aux.getAutor(),nodo,document);
				CrearElemento("Editorial",aux.getEditorial(),nodo,document);
			}
			
			Source source = new DOMSource(document);
			Result result = new StreamResult(new java.io.File("libros.xml"));
			
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
		 * Método que nos ayuda a que el proceso de introducción de elementos en el xml
		 * sea más rápico pasándole los parámetros.
		 * @param elemento
		 * @param valor
		 * @param nodo
		 * @param document
		 */
		private static void CrearElemento(String elemento, String valor, Element nodo, Document document) {
			Element elem = document.createElement(elemento);
			Text text = document.createTextNode(valor);
			
			elem.appendChild(text);
			nodo.appendChild(elem);
		}

		/**
		 * Método que lee un fichero de objetos de tipo libro devuelve una lista de objetos de tipo libro
		 * @param fichero
		 * @return
		 */
		public static ArrayList<Libro> getLibrosFichero(File fichero) {
			ArrayList <Libro> libros = new ArrayList<>();
			Libro aux;
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			
			
			
			try {
				fis = new FileInputStream(fichero);
				ois = new ObjectInputStream(fis);
				
				aux = (Libro) ois.readObject();
				while(aux!=null) {
					libros.add(aux);
					aux = (Libro) ois.readObject();
				}
				ois.close();
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Fin de lectura.");
		
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return libros;
		}

		public static void main(String[] args) {
			File fichero = new File("libros.dat");
			gestionarMenu(fichero);
			crearLibrosXML(fichero);
		}
	
}
