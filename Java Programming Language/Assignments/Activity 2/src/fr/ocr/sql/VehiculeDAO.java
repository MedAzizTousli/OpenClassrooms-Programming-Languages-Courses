package fr.ocr.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import voiture.Vehicule;
import voiture.option.Option;

public class VehiculeDAO extends DAO<Vehicule> {

	public VehiculeDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Vehicule v) {

		try (PreparedStatement prep = connect
						.prepareStatement("INSERT INTO VEHICULE (id, nom, marque, moteur, prix) VALUES ?, ?, ?, ?, ?");
			PreparedStatement options = connect
					.prepareStatement("INSERT INTO vehicule_option (id_vehicule, id_option) VALUES ?, ?");
				){

			// quelques contrôles d'usage
			if (v.getNom().trim().equals(""))
				throw new DAOException(
						"VehiculeDAO : Erreur de creation en base de donnée. Champ 'nom' obligatoire !");

			if (v.getMarque().getId() < 0)
				throw new DAOException(
						"VehiculeDAO : Erreur de creation en base de donnée. Champ 'marque' obligatoire !");

			if (v.getMoteur().getId() < 0)
				throw new DAOException(
						"VehiculeDAO : Erreur de creation en base de donnée. Champ 'moteur' obligatoire !");

			if (v.getPrix() <= 0 || v.getPrix() == null)
				throw new DAOException(
						"VehiculeDAO : Erreur de creation en base de donnée. Champ 'prix' obligatoire !");

			// On démarre notre transction
			connect.setAutoCommit(false);

			// Nous allons récupérer le prochain ID
			ResultSet nextID = connect.prepareStatement(
					"CALL NEXT VALUE FOR seq_vehicule_id").executeQuery();

			if (nextID.next()) {
				int ID = nextID.getInt(1);

				
				prep.setInt(1, ID);
				prep.setString(2, v.getNom());
				prep.setInt(3, v.getMarque().getId());
				prep.setInt(4, v.getMoteur().getId());
				prep.setDouble(5, v.getPrix());

				prep.executeUpdate();

				// Nous avons enregistrer notre véhicule en base
				// Nous allons maitenant sauvegarder ses options dans la table
				// de jointure
				List<Option> list = v.getOptions();

				for (Option o : list) {

					options.setInt(1, ID);
					options.setInt(2, o.getId());

					try {
						options.executeUpdate();
					} catch (SQLException e) {
						throw new DAOException(
								"VehiculeDAO : Erreur lors de la sauvegarde de l'option "
										+ o + " du vehicule ! \n"
										+ e.getMessage());
					}

				}
			} else {
				throw new DAOException(
						"VehiculeDAO : Impossible de lire le résultat de la recherche de séquence !");
			}

		} catch (SQLException e) {
			new DAOException("VehiculeDAO : " + e.getMessage());
			try {
				connect.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (DAOException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}

		try {
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean delete(Vehicule v) {
		try (PreparedStatement options = connect
					.prepareStatement("DELETE FROM vehicule_option WHERE id_vehicule = ?");
			PreparedStatement vehicule = connect
					.prepareStatement("DELETE FROM vehicule WHERE id = ?");
				){
			if (v.getId() < 1)
				throw new DAOException(
						"Impossible de supprimer un véhicule non présent en base de données ! ");
			
			// On démarre notre transction
			connect.setAutoCommit(false);
						
			options.setInt(1, v.getId());
			options.executeUpdate();
			
			
			vehicule.setInt(1, v.getId());
			vehicule.executeUpdate();
			
		} catch (DAOException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			new DAOException("VehiculeDAO : Erreur lors que la suppression d'un véhicule");
			return false;
		}

		try {
			connect.commit();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	public boolean update(Vehicule v) {
		return true;
	}

	public Vehicule find(int id) {
		Vehicule voit = new Vehicule();

		String query = "SELECT * FROM vehicule WHERE id = " + id;
		
		try (Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)){
			
			ResultSet result = state.executeQuery(query);

			if (result.first())
				voit = new Vehicule(id, result.getString("nom"), new MarqueDAO(
						this.connect).find(result.getInt("marque")),
						new MoteurDAO(this.connect).find(result
								.getInt("moteur")), getOptions(id),
						result.getDouble("prix"));
		} catch (SQLException e) {
			new DAOException("VehiculeDAO : " + e.getMessage());
		}
		return voit;
	}

	public List<Vehicule> findAll() {
		List<Vehicule> list = new ArrayList<>();

		try (Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)){
			ResultSet result = state.executeQuery(
					"SELECT * FROM vehicule ORDER BY nom");
			while (result.next()) {
				int id = result.getInt("id");
				list.add(new Vehicule(id, result.getString("nom"),
						new MarqueDAO(this.connect).find(result
								.getInt("marque")), new MoteurDAO(this.connect)
								.find(result.getInt("moteur")), getOptions(id),
						result.getDouble("prix")));
			}
		} catch (SQLException e) {
			new DAOException("VehiculeDAO : " + e.getMessage());
		}

		return list;
	}

	private List<Option> getOptions(int id) {
		List<Option> list = new ArrayList<>();

		try (Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)){
			ResultSet result = state.executeQuery(
					"SELECT id_option FROM vehicule_option WHERE id_vehicule = "
							+ id);
			while (result.next())
				list.add(new OptionDAO(this.connect).find(result
						.getInt("id_option")));
		} catch (SQLException e) {
			new DAOException("TypeMoteurDAO : " + e.getMessage());
		}

		return list;
	}
}
