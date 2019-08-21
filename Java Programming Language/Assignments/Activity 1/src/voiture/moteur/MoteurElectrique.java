package voiture.moteur;

public class MoteurElectrique extends Moteur {

	public MoteurElectrique(String cyl, Double pPrix) {
		super(cyl, pPrix);
		super.type = TypeMoteur.ELECTRIQUE;
	}

}
