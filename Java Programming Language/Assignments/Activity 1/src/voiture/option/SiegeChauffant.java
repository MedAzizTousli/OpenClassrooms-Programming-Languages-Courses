package voiture.option;

public class SiegeChauffant implements Option {

	public String toString() {
		return "Siège chauffant (" + getPrix() + "€)";
	}

	public Double getPrix() {
		return 562.90d;
	}

}
