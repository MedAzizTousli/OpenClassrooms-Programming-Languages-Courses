package voiture.moteur;
public class MoteurDiesel extends Moteur{
	
	public MoteurDiesel(String cyl, Double pPrix) {
		super(cyl, pPrix);
		super.type = TypeMoteur.DIESEL;
	}	
	
}
