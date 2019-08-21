package voiture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import voiture.moteur.Moteur;
import voiture.option.Option;

public abstract class Vehicule implements Serializable{
	protected Marque marque;
	protected Double prix;
	protected String nom;
	protected List<Option> listOptions = new ArrayList<>();
	protected Moteur mot = null;
	
	public String toString(){
		String str =  marque + " : " + nom + " " + mot + " (" + this.prix + "€) " +  listOptions;
		str += " d'une valeur totale de " + getPrix() + " €";
		return str;
	}
	
	public Marque getMarque(){return marque;}
	public Double getPrix() {
		double prixTotal = prix;
		for(Option opt : listOptions)
			prixTotal += opt.getPrix();
		
		return prixTotal;
	}
	public List<Option> getOptions(){return listOptions;}
	public void addOption(Option opt){
		listOptions.add(opt);
	}
	
	public void setMoteur(Moteur mot){
		this.mot = mot;
	}
}
