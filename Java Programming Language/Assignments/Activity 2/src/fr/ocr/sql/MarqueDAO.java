package fr.ocr.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import voiture.Marque;

public class MarqueDAO extends DAO<Marque> {
	public MarqueDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Marque obj) {
		return true;
	}

	public boolean delete(Marque obj) {
		return false;
	}

	public boolean update(Marque obj) {
		return true;
	}

	public Marque find(int id) {
		Marque marque = new Marque();
		try(Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)) {
			ResultSet result = state.executeQuery(
					"SELECT * FROM marque WHERE id = " + id);
			if (result.first())
				marque = new Marque(id, result.getString("nom"));
		} catch (SQLException e) {
			new DAOException("MarqueDAO : " + e.getMessage());
		}
		return marque;
	}

	public List<Marque> findAll() {
		List<Marque> list = new ArrayList<>();

		try(Statement state = this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			ResultSet result = state.executeQuery(
					"SELECT * FROM marque ORDER BY nom");
			while (result.next())
				list.add(new Marque(result.getInt("id"), result
						.getString("nom")));
		} catch (SQLException e) {
			new DAOException("MarqueDAO : " + e.getMessage());
		}

		return list;
	}

}