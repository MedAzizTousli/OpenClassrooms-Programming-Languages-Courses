package voiture.moteur;

public class MoteurHybride extends Moteur {

	public MoteurHybride(String cyl, Double pPrix) {
		super(cyl, pPrix);
		super.type = TypeMoteur.HYBRIDE;
	}

}
