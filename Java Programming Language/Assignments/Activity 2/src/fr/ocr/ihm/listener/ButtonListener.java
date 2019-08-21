package fr.ocr.ihm.listener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import voiture.Vehicule;
import fr.ocr.sql.DAOTableFactory;
import fr.ocr.sql.DatabaseTable;
import fr.ocr.sql.HsqldbConnection;
import fr.ocr.sql.VehiculeDAO;

//Notre listener pour le bouton
public class ButtonListener implements ActionListener {
	protected int column, row, id;
	protected JTable table;


	public void setColumn(int col) {
		this.column = col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public void actionPerformed(ActionEvent event) {
		// La colonne 4 correpond à notre ID de véhicule
		Object o = ((AbstractTableModel) table.getModel()).getValueAt(row, 4);

		id = Integer.valueOf(o.toString());

		Thread t = new Thread(new Runnable() {
			public void run() {
				// On récupère notre objet
				Vehicule v = new VehiculeDAO(HsqldbConnection.getInstance())
						.find(id);
				
				// Si la suppresion a fonctionnée, on met à jour le JTable : dans l'EDT
				if (new VehiculeDAO(HsqldbConnection.getInstance()).delete(v)){
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							//Astuce de sioux : nous pouvons récupérer les objets parents d'un composant, donc notre JFrame depuis le JTable
							JFrame frame = (JFrame)table	.getParent() //Va retourner le JScrollPane contenant le JTable
															.getParent() //Va retourner le JPanel contenant le JScrollPane
															.getParent() //Va retourner le JLayeredPane contenant le JPanel
															.getParent() //Va retourner le JRootPane contenant le JLayeredPane
															.getParent() .getParent(); //Va retourner notre JFrame;
							
							frame.getContentPane().removeAll();
							
							//On met à jour le tableau
							frame.getContentPane()
									.add(new JScrollPane(
											DAOTableFactory.getTable(
													HsqldbConnection
															.getInstance(),
													DatabaseTable.VEHICULE)),
											BorderLayout.CENTER);
							frame.getContentPane().revalidate();
						}
					});
				}
			}
		});
		t.start();
	}
}