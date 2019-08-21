package voiture.moteur;

public class MoteurEssence extends Moteur {

	public MoteurEssence(String cyl, Double pPrix) {
		super(cyl, pPrix);
		super.type = TypeMoteur.ESSENCE;
	}

}
