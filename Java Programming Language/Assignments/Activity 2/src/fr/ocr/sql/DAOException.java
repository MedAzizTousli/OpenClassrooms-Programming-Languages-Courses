package fr.ocr.sql;

import javax.swing.JOptionPane;

public class DAOException extends Exception {

	public DAOException(String mess) {
		super(mess);
		JOptionPane jop = new JOptionPane();
		jop.showMessageDialog(null, mess, "Erreur de recherche dans le DAO",
				JOptionPane.ERROR_MESSAGE);
	}

}
