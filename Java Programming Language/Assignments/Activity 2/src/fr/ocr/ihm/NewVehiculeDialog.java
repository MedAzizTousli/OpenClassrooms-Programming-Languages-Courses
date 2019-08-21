package fr.ocr.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import voiture.Marque;
import voiture.Vehicule;
import voiture.moteur.Moteur;
import voiture.option.Option;
import fr.ocr.sql.DAOTableFactory;
import fr.ocr.sql.DatabaseTable;
import fr.ocr.sql.HsqldbConnection;
import fr.ocr.sql.MarqueDAO;
import fr.ocr.sql.MoteurDAO;
import fr.ocr.sql.OptionDAO;
import fr.ocr.sql.VehiculeDAO;

public class NewVehiculeDialog extends JDialog {

	private JTextField nom;
	private JFormattedTextField prix;
	private JLabel jlNom, jlVehicule, jlMarque, jlMoteur, jlOptions, jlPrix;
	private JComboBox jcbMoteur, jcbMarque;
	private JCheckBox[] jcbOptions;

	private List<Option> listOptions = new OptionDAO(
			HsqldbConnection.getInstance()).findAll();
	private List<Moteur> listMoteur = new MoteurDAO(
			HsqldbConnection.getInstance()).findAll();
	private List<Marque> listMarque = new MarqueDAO(
			HsqldbConnection.getInstance()).findAll();

	private Vehicule v;
	private JFrame frame;

	public NewVehiculeDialog(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		frame = parent;
		this.setSize(550, 350);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.initComponent();
	}

	public boolean showDialog() {
		this.setVisible(true);
		return true;
	}

	private void initComponent() {

		// Le nom
		JPanel panNom = new JPanel();
		panNom.setBackground(Color.white);
		panNom.setPreferredSize(new Dimension(220, 60));
		nom = new JTextField();
		nom.setPreferredSize(new Dimension(100, 25));
		panNom.setBorder(BorderFactory.createTitledBorder("Nom du véhicule"));
		jlNom = new JLabel("Saisir un nom :");
		panNom.add(jlNom);
		panNom.add(nom);

		// La marque
		JPanel panMarque = new JPanel();
		panMarque.setBackground(Color.white);
		panMarque.setPreferredSize(new Dimension(220, 60));
		panMarque.setBorder(BorderFactory
				.createTitledBorder("Marque du véhicule"));
		jcbMarque = new JComboBox();

		for (Marque m : listMarque)
			jcbMarque.addItem(m);

		jlMarque = new JLabel("Marque : ");
		panMarque.add(jlMarque);
		panMarque.add(jcbMarque);

		// Le moteur
		JPanel panMoteur = new JPanel();
		panMoteur.setBackground(Color.white);
		panMoteur.setBorder(BorderFactory
				.createTitledBorder("Type de moteur du véhicule"));
		panMoteur.setPreferredSize(new Dimension(440, 60));

		jcbMoteur = new JComboBox();

		for (Moteur m : listMoteur)
			jcbMoteur.addItem(m);

		jlMoteur = new JLabel("Marque : ");
		panMoteur.add(jlMoteur);
		panMoteur.add(jcbMoteur);

		
		//Le prix
		JPanel panPrix = new JPanel();
		panPrix.setBackground(Color.white);
		panPrix.setPreferredSize(new Dimension(220, 60));
		prix = new JFormattedTextField(NumberFormat.getNumberInstance());
		prix.setPreferredSize(new Dimension(100, 25));
		panPrix.setBorder(BorderFactory.createTitledBorder("Prix du véhicule"));
		jlPrix = new JLabel("Prix :");
		panPrix.add(jlPrix);
		panPrix.add(prix);

		// Les options
		JPanel panOptions = new JPanel();
		panOptions.setBackground(Color.white);
		panOptions.setPreferredSize(new Dimension(530, 80));
		panOptions.setBorder(BorderFactory
				.createTitledBorder("Options Disponibles"));
		jlOptions = new JLabel("Options ");

		jcbOptions = new JCheckBox[listOptions.size()];

		for (int i = 0; i < listOptions.size(); i++) {
			Option o = listOptions.get(i);
			jcbOptions[i] = new JCheckBox(o.toString());
			panOptions.add(jcbOptions[i]);
		}

		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(panNom);
		content.add(panMarque);
		content.add(panMoteur);
		content.add(panPrix);
		content.add(panOptions);

		JPanel control = new JPanel();
		JButton okBouton = new JButton("OK");

		//Si on clique sur OK
		okBouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nomVehicule = nom.getText();
				try {
					
					//on contrôle bien qu'il n'y ai pas de champ vide...
					if (nomVehicule.trim().equals(""))
						throw new EmptyFieldException(
								"Le champ 'nom' est obligatoire ! ");

					if (prix.getText().trim().equals(""))
						throw new EmptyFieldException(
								"Le champ 'prix' est obligatoire ! ");

					//On récupère ensuite les valeurs des autres champs de formulaire
					Marque marque = (Marque) jcbMarque.getSelectedItem();
					
					Moteur moteur = (Moteur) jcbMoteur.getSelectedItem();
					List<Option> list = new ArrayList<>();
					for (int i = 0; i < jcbOptions.length; i++) {
						if (jcbOptions[i].isSelected())
							list.add(listOptions.get(i));
					}

					Double price = ((Number) prix.getValue()).doubleValue();
					
					//On créer notre vehicule
					v = new Vehicule(0, nomVehicule, marque, moteur, list,
							price);

					//On sauvegarde le tout en base et on met à jour notre JTable
					//En tâche de fond 
					Thread t = new Thread(new Runnable() {
						public void run() {
							VehiculeDAO vDao = new VehiculeDAO(HsqldbConnection
									.getInstance());
							boolean result = vDao.create(v);
							
							if(result){
								//Et la mise à jour du JTable dans l'EDT
								SwingUtilities.invokeLater(new Runnable() {
									public void run() {
										frame.getContentPane().removeAll();
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
					setVisible(false);
				} catch (EmptyFieldException e) {
				} catch (NumberFormatException e) {
					new EmptyFieldException(e.getMessage());
				}
			}
		});

		JButton cancelBouton = new JButton("Annuler");
		cancelBouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		control.add(okBouton);
		control.add(cancelBouton);

		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}
}