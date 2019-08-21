package fr.ocr.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JCheckBox;
import javax.swing.JTable;

import fr.ocr.ihm.ButtonEditor;
import fr.ocr.ihm.ButtonRenderer;
import fr.ocr.ihm.listener.ViewDetailVehiculeListener;

/**
 * Classe permettant de créer l'objet JTable en fonction de la table que nous
 * souhaitons afficher
 * 
 * @author cysboy
 */
public class DAOTableFactory {

	public static JTable getTable(Connection conn, DatabaseTable table) {
		JTable tab = new JTable();

		try (
				Statement state = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
				ResultSet result = state.executeQuery("SELECT * FROM " + table);){
			
			ResultSetMetaData resultMeta = result.getMetaData();
			int nbreColumn = resultMeta.getColumnCount();
			
			// Pour récupérer le nombre total de ligne
			// on se place sur la dernière puis on revient avant la première
			// pour parcourir
			result.last();
			int nbreRow = result.getRow();
			result.beforeFirst();

			String[] title = new String[nbreColumn];
			Object[][] data = new Object[nbreRow][nbreColumn];

			/**
			 * Pour la table vehicule, on rajoute une colonne qui sera le bouton
			 * de suppression de ligne
			 */
			if (table.equals(DatabaseTable.VEHICULE)) {
				title = new String[nbreColumn + 2];
				title[nbreColumn] = "DETAIL";
				title[nbreColumn + 1] = "ACTION";

				data = new Object[nbreRow][nbreColumn + 2];
				for (Object[] rows : data) {
					rows[nbreColumn] = "DETAIL";
					rows[nbreColumn + 1] = "SUPPRIMER";
				}
			}

			for (int i = 0; i < nbreColumn; i++)
				title[i] = resultMeta.getColumnName(i + 1).toUpperCase();

			int nbreLine = 0;
			while (result.next()) {
				for (int i = 0; i < nbreColumn; i++)
					data[nbreLine][i] = result.getObject(i + 1).toString();

				nbreLine++;
			}

			tab = new JTable(data, title);

			/**
			 * On affiche les boutons uniquement pour la table véhicule
			 */
			if (table.equals(DatabaseTable.VEHICULE)) {
				tab.getColumn("ACTION").setCellRenderer(
						new ButtonRenderer("SUPPRIMER"));
				tab.getColumn("ACTION").setCellEditor(
						new ButtonEditor(new JCheckBox(), "SUPPRIMER"));
				tab.getColumn("DETAIL").setCellRenderer(
						new ButtonRenderer("DETAIL"));
				tab.getColumn("DETAIL").setCellEditor(
						new ButtonEditor(new JCheckBox(), "DETAIL",
								new ViewDetailVehiculeListener()));
			}
			tab.setRowHeight(30);
		} catch (SQLException e) {
			new DAOException(e.getMessage());
		}

		return tab;
	}

}
