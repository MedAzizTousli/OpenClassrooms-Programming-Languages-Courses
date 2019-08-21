package test.database;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) {
		String path = "";

		try {
			path = new File(".").getCanonicalPath();
			path += "/hsqldb/database/";
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println("Chemin d'accès à  HSQLDB : " + path);

		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (Exception e) {
			System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
			e.printStackTrace();
			return;
		}

		try {
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:"
					+ path + "VEHICULE", "SA", "");
			String[] tablesnames = { "marque", "type_moteur", "moteur",
					"option", "vehicule_option", "vehicule" };

			for (String table : tablesnames) {
				Statement state = conn.createStatement();
				System.out.println(("\nContenu de la table : " + table)
						.toUpperCase());
				ResultSet result = state.executeQuery("SELECT * FROM " + table);
				ResultSetMetaData resultMeta = result.getMetaData();

				String columnSeparator = "", rowSeparator = "";
				for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
					columnSeparator += "********************";
					rowSeparator += "--------------------";
				}
				System.out.println("\n" + columnSeparator);
				for (int i = 1; i <= resultMeta.getColumnCount(); i++)
					System.out.print(StringUtils.center(resultMeta
							.getColumnName(i).toUpperCase(), 19)
							+ "*");

				System.out.println("\n" + columnSeparator);

				while (result.next()) {
					for (int i = 1; i <= resultMeta.getColumnCount(); i++)
						System.out.print(StringUtils.center(result.getObject(i)
								.toString(), 19)
								+ "|");

					System.out.println("\n" + rowSeparator);
				}

				result.close();
				state.close();
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
