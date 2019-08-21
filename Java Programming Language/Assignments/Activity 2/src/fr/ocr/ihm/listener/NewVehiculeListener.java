package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import fr.ocr.ihm.NewVehiculeDialog;

public class NewVehiculeListener implements ActionListener {

	private JFrame frame;

	public NewVehiculeListener(JFrame f) {
		frame = f;
	}

	public void actionPerformed(ActionEvent e) {
		NewVehiculeDialog dialog = new NewVehiculeDialog(frame,
				"Ajouter un nouveau véhicule", true);
		dialog.showDialog();
	}
}
