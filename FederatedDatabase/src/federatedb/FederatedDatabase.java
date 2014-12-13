package federatedb;

import java.util.ArrayList;
import java.util.List;

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
	
	
	public static void main(String[] args) throws Exception {
		List<IWrapper> wrappers = new ArrayList<IWrapper>();
		wrappers.add(new XMLWrapper("sourcesXML", "XML"));
		wrappers.add(new SqlWrapper("fournisseur", "SQL"));
		
		FederatedDatabase fdb = new FederatedDatabase(wrappers);
		
		if (args.length == 0)
			fdb.query("query.xq");
		else
			fdb.query(args[1]);
		
		
		System.out.println("Terminated with success");
	}
}
