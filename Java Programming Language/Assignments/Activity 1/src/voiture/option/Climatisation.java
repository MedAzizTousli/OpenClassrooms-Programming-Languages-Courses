package voiture.option;

public class Climatisation implements Option {

	public String toString() {
		return "Climatisation (" + getPrix() + "€)";
	}

	public Double getPrix() {
		return 347.30d;
	}

}