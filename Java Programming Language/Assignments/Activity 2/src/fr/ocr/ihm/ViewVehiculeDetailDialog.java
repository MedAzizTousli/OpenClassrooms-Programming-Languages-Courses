package fr.ocr.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import voiture.Vehicule;
import voiture.option.Option;

public class ViewVehiculeDetailDialog extends JDialog {
	
	private JLabel jlNomValue, jlMarqueValue, jlMoteurValue,
			jlPrixValue,jlPrix;
	private JLabel[] jlOptionsValue;

	private Vehicule v;

	public ViewVehiculeDetailDialog(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);

		this.setSize(550, 350);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public boolean showDialog(Vehicule pV) {
		this.v = pV;
		this.initComponent();
		this.setVisible(true);
		return true;
	}

	private void initComponent() {
		// Le nom
		JPanel panNom = new JPanel();
		panNom.setBackground(Color.white);
		panNom.setPreferredSize(new Dimension(220, 50));
		panNom.setBorder(BorderFactory.createTitledBorder("Nom du véhicule"));
		jlNomValue = new JLabel(v.getNom());
		panNom.add(jlNomValue);

		// La marque
		JPanel panMarque = new JPanel();
		panMarque.setBackground(Color.white);
		panMarque.setPreferredSize(new Dimension(220, 50));
		panMarque.setBorder(BorderFactory
				.createTitledBorder("Marque du véhicule"));

		jlMarqueValue = new JLabel(v.getMarque().toString());
		panMarque.add(jlMarqueValue);

		// Le moteur
		JPanel panMoteur = new JPanel();
		panMoteur.setBackground(Color.white);
		panMoteur.setBorder(BorderFactory
				.createTitledBorder("Type de moteur du véhicule"));
		panMoteur.setPreferredSize(new Dimension(440, 50));
		jlMoteurValue = new JLabel(v.getMoteur().toString());
		panMoteur.add(jlMoteurValue);

		//Le prix
		JPanel panPrix = new JPanel();
		panPrix.setBackground(Color.white);
		panPrix.setPreferredSize(new Dimension(220, 50));
		panPrix.setBorder(BorderFactory.createTitledBorder("Prix du véhicule"));
		jlPrix = new JLabel("Prix sans option : ");
		panPrix.add(jlPrix);
		jlPrixValue = new JLabel(v.getPrix().toString() + " €");
		panPrix.add(jlPrixValue);

		// Les options
		JPanel panOptions = new JPanel();
		panOptions.setBackground(Color.white);
		panOptions.setPreferredSize(new Dimension(530, 80));
		panOptions.setBorder(BorderFactory
				.createTitledBorder("Options Disponibles"));
		List<Option> listOptions = v.getOptions();
		jlOptionsValue = new JLabel[listOptions.size()];

		for (int i = 0; i < listOptions.size(); i++) {
			Option o = listOptions.get(i);
			jlOptionsValue[i] = new JLabel(o.toString() + "(" + o.getPrix()
					+ " €)");
			panOptions.add(jlOptionsValue[i]);
		}

		//Le prix total
		JPanel panPrixTotal = new JPanel();
		panPrixTotal.setBackground(Color.green);
		panPrixTotal.setPreferredSize(new Dimension(350, 50));
		panPrixTotal.setBorder(BorderFactory
				.createTitledBorder("Prix TOTAL du véhicule"));

		JLabel jlPrixTotal = new JLabel(v.getPrixTotal().toString() + " €");
		panPrixTotal.add(jlPrixTotal);

		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(panNom);
		content.add(panMarque);
		content.add(panMoteur);
		content.add(panPrix);
		content.add(panOptions);
		content.add(panPrixTotal);
		this.getContentPane().add(content, BorderLayout.CENTER);
	}
}