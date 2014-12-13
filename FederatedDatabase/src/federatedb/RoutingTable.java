package federatedb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wrappers.*;

/**
 * 
 * @author xfraboul
 */
public class RoutingTable {
	private Map<String, IWrapper> routing;
	private Map<String, String> model;
	
	private int counter;  

	/**
	 * RoutingTable constructor
	 * @param wrappers
	 */
	public RoutingTable(List<IWrapper> wrappers) {
		routing = new HashMap<String, IWrapper>();
		model = new HashMap<String, String>();
		
		this.counter = 1;
		
		// Generate Routing Table
		for (IWrapper wrapper: wrappers) {
			List<String> tables = wrapper.getTables();
			for (String table: tables) {
				routing.put(table, wrapper);
			}
		}	
		
		// Generate MetaModel
		for (String table: routing.keySet()) {
			model.put(table, routing.get(table).getModel(table));
		}
	}
	
	/**
	 * 
	 * @param table
	 * @param query
	 * @param projections
	 * @param selections
	 * @return
	 * @throws IOException 
	 */
	public String query(String table, String query, List<String> projections, List<String> selections) throws Exception {
		IWrapper wrapper = routing.get(table); 
		
		String fileName = "tmp/tmp" + counter++ +".xml";
				
		String result = wrapper.executeQuery(table, query, projections, selections);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
		
		writer.write(result);
		
		writer.close();
		
		return fileName;
	}
	
	/**
	 * 
	 * @return MetaModel of the federated DataBase
	 */
	public Map<String, String> getModel() {
		return model;
	}
}
