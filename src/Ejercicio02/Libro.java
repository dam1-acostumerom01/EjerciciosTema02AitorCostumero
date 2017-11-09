package Ejercicio02;

import java.io.Serializable;

public class Libro implements Serializable {

	public String ISBN;
	public String titulo;
	public String autor;
	public String editorial;
	
	public Libro() {}
	
	public Libro(String iSBN, String titulo, String autor, String editorial) {
		
		ISBN = iSBN;
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	
	
	
}
