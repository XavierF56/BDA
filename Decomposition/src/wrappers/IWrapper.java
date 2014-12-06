package wrappers;
import java.util.List;

public abstract class IWrapper {
	
	static int QueryCounter = 0;
	static String DestinationFolder = "temp/";
	
	/**
	 * Fetch the model of the database.
	 * @return DTD
	 */
	public abstract String getModel(String table);
	
	/**
	 * Fetch the names of the tables of the database.
	 * @return
	 */
	public abstract List<String> getTables();
	
	/**
	 * Execute a query. Returns the results as XML.
	 * @param query Xpath query
	 * @param projections
	 * @param selections
	 * @return XML
	 */
	public abstract String executeQuery(String relation, String query, List<String> projections, List<String> selections);
}
