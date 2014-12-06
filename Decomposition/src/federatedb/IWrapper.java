package federatedb;
import java.util.List;

public abstract class IWrapper {
	
	static int QueryCounter;
	static String DestinationFolder;
	
	/**
	 * Fetch the model of the database.
	 * @return DTD
	 */
	abstract String getModel(String table);
	
	/**
	 * Fetch the names of the tables of the database.
	 * @return
	 */
	abstract List<String> getTables();
	
	/**
	 * Execute a query. Returns the results as XML.
	 * @param query Xpath query
	 * @param projections
	 * @param selections
	 * @return XML
	 */
	abstract String executeQuery(String relation, String query, List<String> projections, List<String> selections);
}
