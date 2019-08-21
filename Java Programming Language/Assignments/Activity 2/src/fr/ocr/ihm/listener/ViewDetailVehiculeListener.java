package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;

import javax.swing.table.AbstractTableModel;

import voiture.Vehicule;
import fr.ocr.ihm.ViewVehiculeDetailDialog;
import fr.ocr.sql.HsqldbConnection;
import fr.ocr.sql.VehiculeDAO;

public class ViewDetailVehiculeListener extends ButtonListener {
	private Integer id;

	public void actionPerformed(ActionEvent e) {

		// La colonne 4 correpond à notre ID de véhicule
		Object o = ((AbstractTableModel) table.getModel()).getValueAt(row, 4);

		id = Integer.valueOf(o.toString());

		Thread t = new Thread(new Runnable() {
			public void run() {
				// On récupère notre objet
				Vehicule v = new VehiculeDAO(HsqldbConnection.getInstance())
						.find(id);
				
				// On affiche notre boîte modale
				new ViewVehiculeDetailDialog(null, "Détail d'un véhicule", true)
						.showDialog(v);
			}
		});
		t.start();
	}
}
