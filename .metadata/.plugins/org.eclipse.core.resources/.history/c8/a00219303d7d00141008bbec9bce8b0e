package federatedb;
import java.util.List;

public interface IWrapper {
	
	/**
	 * Fetch the model of the database.
	 * @return DTD
	 */
	String getModel();
	
	/**
	 * Fetch the names of the tables of the database.
	 * @return
	 */
	List<String> getTables();
	
	/**
	 * Execute a query. Returns the results as XML.
	 * @param query Xpath query
	 * @param projections
	 * @param selections
	 * @return XML
	 */
	String executeQuery(String relation, String query, List<String> projections, List<String> selections);
}
