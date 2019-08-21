package voiture.option;

public class GPS implements Option {

	public String toString() {
		return "GPS (" + getPrix() + "€)";
	}

	public Double getPrix() {
		return 113.50d;
	}

}
