package voiture.option;

public class VitreElectrique implements Option {

	public String toString() {
		return "Vitre electrique (" + getPrix() + "€)";
	}

	public Double getPrix() {
		return 212.35d;
	}

}
