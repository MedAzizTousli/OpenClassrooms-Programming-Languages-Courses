package fr.ocr.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import voiture.moteur.TypeMoteur;

public class TypeMoteurDAO extends DAO<TypeMoteur> {
	public TypeMoteurDAO(Connection conn) {
		super(conn);
	}

	public boolean create(TypeMoteur obj) {
		return true;
	}

	public boolean delete(TypeMoteur obj) {
		return false;
	}

	public boolean update(TypeMoteur obj) {
		return true;
	}

	public TypeMoteur find(int id) {
		TypeMoteur type = new TypeMoteur();

		try (Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)){
			ResultSet result = state.executeQuery(
					"SELECT * FROM type_moteur WHERE id = " + id);
			if (result.first())
				type = new TypeMoteur(id, result.getString("description"));
		} catch (SQLException e) {
			new DAOException("TypeMoteurDAO : " + e.getMessage());
		}
		return type;
	}

	public List<TypeMoteur> findAll() {
		List<TypeMoteur> list = new ArrayList<>();

		try (Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)){
			ResultSet result = state.executeQuery(
					"SELECT * FROM type_moteur ORDER BY nom");
			while (result.next())
				list.add(new TypeMoteur(result.getInt("id"), result
						.getString("description")));
		} catch (SQLException e) {
			new DAOException("TypeMoteurDAO : " + e.getMessage());
		}
		return list;
	}
}