package wrappers;
import java.util.List;

public interface IWrapper {

	/**
	 * Fetch the model of the database for a given table.
	 * @param table
	 * @return DTD
	 */
	public abstract String getModel(String table);
	
	/**
	 * Fetch the names of the tables of the database. 
	 * Names are prefixed by the type of Database (e.g. "SQL" for an SQL database).
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
