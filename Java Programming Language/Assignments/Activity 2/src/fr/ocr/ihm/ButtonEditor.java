package fr.ocr.ihm;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import fr.ocr.ihm.listener.ButtonListener;
/**
 * Classe permettant de définir les comportements
 * des boutons du JTable 
 * @author cysboy
 */
public class ButtonEditor extends DefaultCellEditor {

	protected JButton button;
	private boolean isPushed;
	private ButtonListener bListener;
	private String title = "";

	// Constructeur avec une CheckBox
	public ButtonEditor(JCheckBox checkBox, String t) {
		// Par défaut, ce type d'objet travaille avec un JCheckBox
		super(checkBox);
		// On crée à nouveau un bouton
		button = new JButton();
		button.setOpaque(true);
		// On lui attribue un listener
		bListener = new ButtonListener();
		button.addActionListener(bListener);
		title = t;
	}

	public ButtonEditor(JCheckBox checkBox, String t, ButtonListener l) {
		// Par défaut, ce type d'objet travaille avec un JCheckBox
		super(checkBox);
		// On crée à nouveau un bouton
		button = new JButton();
		button.setOpaque(true);
		// On lui attribue un listener
		bListener = l;
		button.addActionListener(bListener);
		title = t;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// On précise le numéro de ligne à notre listener
		bListener.setRow(row);
		// Idem pour le numéro de colonne
		bListener.setColumn(column);
		// On passe aussi le tableau en paramètre pour des actions potentielles
		bListener.setTable(table);

		// On réaffecte le libellé au bouton
		button.setText(title);
		// On renvoie le bouton
		return button;
	}

}