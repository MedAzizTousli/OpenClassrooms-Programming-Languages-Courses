package voiture.moteur;

public class Moteur {

	private int id = 0;
	private TypeMoteur type;
	private Double prix;
	private String cylindre;

	public Moteur() {
	}

	public Moteur(int id, TypeMoteur type, String cyl, Double pPrix) {
		this.id = id;
		this.type = type;
		this.cylindre = cyl;
		this.prix = pPrix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TypeMoteur getType() {
		return type;
	}

	public void setType(TypeMoteur type) {
		this.type = type;
	}

	public String getCylindre() {
		return cylindre;
	}

	public void setCylindre(String cylindre) {
		this.cylindre = cylindre;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public Double getPrix() {
		return this.prix;
	}

	public String toString() {
		return type + " " + cylindre;
	}
}
