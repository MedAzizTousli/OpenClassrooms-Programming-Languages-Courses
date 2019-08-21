package voiture.option;

public class BarreDeToit implements Option {

	public String toString() {
		return "Barre de toit (" + getPrix() + "€)";
	}

	public Double getPrix() {
		return 29.90d;
	}
}
