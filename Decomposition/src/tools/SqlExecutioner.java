package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlExecutioner {

	public static String executeQuery(String path, String query) throws SQLException {
		// TODO Virer l'exception
		// TODO Trouver comment fournir un chemin pour la base de données
		Connection connection = null;
		Statement statement = null;
		String xmlRes = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + path);
			connection.setAutoCommit(false);
			//System.out.println("Connection successfull.");
			
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			xmlRes = resultToXML(rs);
			
			rs.close();
			statement.close();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println(xmlRes);
		return xmlRes;
	}
	
	/**
	 * Format the SQL result to <res>[<tuple>]<name></name><id></id>...
	 * @param rs
	 * @return
	 */
	private static String resultToXML(ResultSet rs) {
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<res>");
		ResultSetMetaData metadata;
		try {
			metadata = rs.getMetaData();
			int colNumber = metadata.getColumnCount();
			while (rs.next()) {
				if (colNumber > 1)
					xmlBuilder.append("<tuple>");
				
				for (int i = 1; i <= colNumber; i++){
					xmlBuilder.append("<")
						.append(metadata.getColumnLabel(i).toLowerCase())
						.append(">")
						.append(rs.getObject(i))
						.append("</")
						.append(metadata.getColumnLabel(i).toLowerCase())
						.append(">");
				}
				
				if (colNumber > 1)
					xmlBuilder.append("</tuple>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		xmlBuilder.append("</res>");
		return xmlBuilder.toString();
	}
	
	/*
	public static void main(String[] args) {
		try {
			// executeQuery("fournisseur", "SELECT name FROM sqlite_master WHERE type='table';");
			executeQuery("sourcesSQL/fournisseur", "SELECT * FROM Fournisseur;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
}
