package fr.ocr.sql;

/**
 * Classe qui liste les tables manipulables
 * @author cysboy
 */
public enum DatabaseTable {

	VEHICULE("vehicule"), MARQUE("marque"), TYPEMOTEUR("type_moteur"), MOTEUR(
			"moteur"), OPTION("option"), VEHICULE_OPTION("vehicule_option");

	private String name = "";

	DatabaseTable(String n) {
		name = n;
	}

	public String toString() {
		return name;
	}
}
