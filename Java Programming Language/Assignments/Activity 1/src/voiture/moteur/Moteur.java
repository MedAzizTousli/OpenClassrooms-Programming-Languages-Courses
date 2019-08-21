package voiture.moteur;

import java.io.Serializable;

public abstract class Moteur implements Serializable{

	protected TypeMoteur type;
	protected Double prix;
	protected String cylindre;
	
	public Moteur(String cyl, Double pPrix){
		this.cylindre = cyl;
		this.prix = pPrix;
	}
	
	public Double getPrix(){
		return this.prix;
	}
	
	public String toString(){
		return type + " " + cylindre;
	}
}
