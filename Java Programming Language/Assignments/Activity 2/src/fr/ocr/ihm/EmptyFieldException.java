package fr.ocr.ihm;

import javax.swing.JOptionPane;

public class EmptyFieldException extends Exception {
	public EmptyFieldException(String err) {
		super(err);
		JOptionPane jop = new JOptionPane();
		jop.showMessageDialog(null, err, "Champs obligatoire manquant",
				JOptionPane.ERROR_MESSAGE);
	}
}
