package voiture.moteur;

public class TypeMoteur {

	private String nom = "";
	private int id = 0;

	public TypeMoteur(int id, String nom) {
		super();
		this.nom = nom;
		this.id = id;
	}

	public TypeMoteur() {
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return this.nom;
	}
}
