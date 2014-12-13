package federatedb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wrappers.IWrapper;
import wrappers.SqlWrapper;
import wrappers.XMLWrapper;

public class FederatedDatabase {
	RoutingTable table;
	
	public FederatedDatabase(List<IWrapper> wrappers) {
		table = new RoutingTable(wrappers); 
	}
	
	public void query(String query) throws Exception {
		Splitter splitter = new Splitter(query, table);
		splitter.run();
	}
	
	public void getModel() {
		Map<String, String> model = table.getModel();
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("model.txt")));
			for (String table: model.keySet()) {
					writer.write("----- " + table + "\n\n" + model.get(table));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		List<IWrapper> wrappers = new ArrayList<IWrapper>();
		wrappers.add(new XMLWrapper("sourcesXML", "XML"));
		wrappers.add(new SqlWrapper("sourcesSQL/fournisseur", "SQL"));
		// /!\ Path for windows
		
		FederatedDatabase fdb = new FederatedDatabase(wrappers);
		
		if (args.length == 0)
			fdb.query("query.xq");
		else
			fdb.query(args[1]);
		
		fdb.getModel();
		
		
		System.out.println("Terminated with success");
	}
}
