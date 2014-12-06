package federatedb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author xfraboul
 */
public class RoutingTable {
	Map<String, IWrapper> routing;
	Map<String, String> metaModel;

	/**
	 * RoutingTable constructor
	 * @param wrappers
	 */
	public RoutingTable(List<IWrapper> wrappers) {
		routing = new HashMap<String, IWrapper>();
		metaModel = new HashMap<String, String>();
		
		
		// Generate Routing Table
		for (IWrapper wrapper: wrappers) {
			List<String> tables = wrapper.getTables();
			for (String table: tables) {
				routing.put(table, wrapper);
			}
		}	
		
		// Generate MetaModel
		for (String table: routing.keySet()) {
			metaModel.put(table, routing.get(table).getModel(table));
		}
	}
	
	/**
	 * 
	 * @param table
	 * @param query
	 * @param projections
	 * @param selections
	 * @return
	 */
	public String query(String table, String query, List<String> projections, List<String> selections) {
		IWrapper wrapper = routing.get(table);
		return wrapper.executeQuery(table, query, projections, selections);
	}
	
	/**
	 * 
	 * @return MetaModel of the federated DataBase
	 */
	public Map<String, String> getMetaModel() {
		return metaModel;
	}
}
