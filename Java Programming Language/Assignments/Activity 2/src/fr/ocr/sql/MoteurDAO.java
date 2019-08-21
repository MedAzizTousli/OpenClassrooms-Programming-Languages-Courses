package fr.ocr.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import voiture.moteur.Moteur;
import voiture.moteur.TypeMoteur;

public class MoteurDAO extends DAO<Moteur> {

	public MoteurDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Moteur obj) {
		return true;
	}

	public boolean delete(Moteur obj) {
		return false;
	}

	public boolean update(Moteur obj) {
		return true;
	}

	public Moteur find(int id) {
		Moteur mot = new Moteur();

		try (Statement state = this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY)){
			
			ResultSet result = state.executeQuery(
					"SELECT * FROM moteur WHERE id = " + id);
			if (result.first()) {
				DAO<TypeMoteur> typeMot = new TypeMoteurDAO(this.connect);
				mot = new Moteur(id, typeMot.find(result.getInt("moteur")),
						result.getString("cylindre"), result.getDouble("prix"));
			}
		} catch (SQLException e) {
			new DAOException("MoteurDAO : " + e.getMessage());
		}
		return mot;
	}

	public List<Moteur> findAll() {
		List<Moteur> list = new ArrayList<>();

		try (Statement state = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY)){
			ResultSet result = 
					state.executeQuery(
							"SELECT * FROM moteur inner join type_moteur on type_moteur.id = moteur ORDER BY description, cylindre");
			while (result.next()) {
				DAO<TypeMoteur> typeMot = new TypeMoteurDAO(this.connect);
				list.add(new Moteur(result.getInt("id"), typeMot.find(result
						.getInt("moteur")), result.getString("cylindre"),
						result.getDouble("prix")));
			}
		} catch (SQLException e) {
			new DAOException("MoteurDAO : " + e.getMessage());
		}

		return list;
	}
}
