package Ejercicio07;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import Ejercicio01.Contacto;

public class Principal {

	
	/**
	 * Lee un fichero de objetos de tipo Contacto y los mete en una lista.
	 * @param fichero
	 * @return devuelve un arraylist de tipo contacto con los contactos del fichero.
	 */
	public static ArrayList<Contacto> leerFichero(File fichero) {
		ArrayList <Contacto> agenda = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(fichero);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Contacto aux;
			
			while (true) {
				 aux = (Contacto) ois.readObject();
				agenda.add(aux);
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Fin de lectura.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return agenda;
	}
	
	/**
	 * Método que escribe un fichero xml a partir de un arraylist, con la premisa stax.
	 * @param agenda arraylist de contactos.
	 */
	public static void escribirXML(ArrayList<Contacto> agenda) {
		
		try {
			FileOutputStream fos = new FileOutputStream("contactosnuevos.xml");
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlStream = factory.createXMLStreamWriter(fos, "UTF-8");

			xmlStream.writeStartDocument();
			xmlStream.writeStartElement("contactos");

			for (int i = 0; i < agenda.size(); i++) {
				xmlStream.writeStartElement("contacto");
				xmlStream.writeStartElement("nombre");
				xmlStream.writeCharacters(agenda.get(i).getNombre());
				xmlStream.writeEndElement();
				
				xmlStream.writeStartElement("apellido");
				xmlStream.writeCharacters(agenda.get(i).getApellido());
				xmlStream.writeEndElement();
				
				xmlStream.writeStartElement("email");
				xmlStream.writeCharacters(agenda.get(i).getEmail());
				xmlStream.writeEndElement();
				
				xmlStream.writeStartElement("telefono");
				xmlStream.writeCharacters(agenda.get(i).getTelefono());
				xmlStream.writeEndElement();
				
				xmlStream.writeEndElement(); // cierro contacto
			}
			
			
			xmlStream.writeEndElement(); // cierro contactos
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
		File fichero = new File("agenda.dat");
		ArrayList <Contacto> agenda = leerFichero(fichero);
		escribirXML(agenda);
	}
	
	
}
