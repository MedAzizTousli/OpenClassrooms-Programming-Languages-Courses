package voiture;

import java.util.ArrayList;
import java.util.List;

import voiture.moteur.Moteur;
import voiture.option.Option;

public class Vehicule {
	private int id = 0;

	private Marque marque;
	private Double prix = 0.0d;
	private String nom;
	private List<Option> listOptions = new ArrayList<>();
	private Moteur mot = null;

	public Vehicule() {
	}

	public Vehicule(int id, String nom, Marque marque, Moteur mot, double prix) {
		this.id = id;
		this.nom = nom;
		this.marque = marque;
		this.mot = mot;
		this.prix = prix;
	}

	public Vehicule(int id, String nom, Marque marque, Moteur mot,
			List<Option> lopt, Double prix) {
		this.id = id;
		this.nom = nom;
		this.marque = marque;
		this.mot = mot;
		this.prix = prix;
		this.listOptions = lopt;
	}

	public String toString() {
		String str = marque + " : " + nom + " " + mot + " (" + this.prix
				+ "€) " + listOptions;
		str += " d'une valeur totale de " + getPrixTotal() + " €";
		return str;
	}

	public Marque getMarque() {
		return marque;
	}

	public Double getPrix() {
		return this.prix;
	}

	public Double getPrixTotal() {
		double prixTotal = prix;
		for (Option opt : listOptions)
			prixTotal += opt.getPrix();

		return prixTotal;
	}

	public void addOption(Option opt) {
		listOptions.add(opt);
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

	public List<Option> getOptions() {
		return listOptions;
	}

	public void setListOptions(List<Option> listOptions) {
		this.listOptions = listOptions;
	}

	public void setMarque(Marque marque) {
		this.marque = marque;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public Moteur getMoteur() {
		return mot;
	}

	public void setMoteur(Moteur mot) {
		this.mot = mot;
	}

}
