package voiture.option;

import java.io.Serializable;

public class Option {

	private int id = 0;
	private String nom = "";
	private double prix = 0d;

	public Option() {
	}

	public Option(int id, String nom, double prix) {
		this.id = id;
		this.nom = nom;
		this.prix = prix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String toString() {
		return nom;
	}

}
