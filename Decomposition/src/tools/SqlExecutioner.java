
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlExecutioner {

	public static String executeQuery(String path, String query) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + path);
			connection.setAutoCommit(false);
			System.out.println("Connection successfull.");
			
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				String nom = rs.getString("nom");
				
				System.out.println("Nom : " + nom);
			}
			
			rs.close();
			statement.close();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static void main(String[] args) {
		try {
			// executeQuery("fournisseur", "SELECT name FROM sqlite_master WHERE type='table';");
			executeQuery("fournisseur", "SELECT * FROM Fournisseur;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
