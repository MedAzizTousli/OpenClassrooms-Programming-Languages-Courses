package fr.ocr.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import voiture.option.Option;

public class OptionDAO extends DAO<Option> {
	public OptionDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Option obj) {
		return true;
	}

	public boolean delete(Option obj) {
		return false;
	}

	public boolean update(Option obj) {
		return true;
	}

	public Option find(int id) {
		Option opt = new Option();

		try (Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)){
			
			ResultSet result = state.executeQuery(
					"SELECT * FROM option WHERE id = " + id);
			if (result.first())
				opt = new Option(id, result.getString("description"),
						result.getDouble("prix"));
		} catch (SQLException e) {
			new DAOException("OptionDAO : " + e.getMessage());
		}
		return opt;
	}

	public List<Option> findAll() {
		List<Option> list = new ArrayList<>();

		try (Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)){
			ResultSet result = state.executeQuery(
					"SELECT * FROM option ORDER BY description");
			while (result.next())
				list.add(new Option(result.getInt("id"), result
						.getString("description"), result.getDouble("prix")));
		} catch (SQLException e) {
			new DAOException("OptionDAO : " + e.getMessage());
		}
		return list;
	}
}