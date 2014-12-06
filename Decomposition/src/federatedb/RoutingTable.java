package federatedb;

import java.util.List;
import java.util.Map;

public class RoutingTable {
	Map<String, IWrapper> routing;

	/**
	 * RoutingTable constructor
	 * @param wrappers
	 */
	public RoutingTable(List<IWrapper> wrappers) {
		for (IWrapper wrapper: wrappers) {
			List<String> tables = wrapper.getTables();
			for (String table: tables) {
				routing.put(table, wrapper);
			}
		}	
	}
	
	public String query(String table, String query, List<String> projections, List<String> selections) {
		IWrapper wrapper = routing.get(table);
		return wrapper.executeQuery(table, query, projections, selections);
	}
}
