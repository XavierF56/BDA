package federatedb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exception.WrapperQueryException;

import wrappers.IWrapper;
import wrappers.SqlWrapper;
import wrappers.XMLWrapper;

public class FederatedDatabase {
	RoutingTable table;
	
	public FederatedDatabase(List<IWrapper> wrappers) {
		table = new RoutingTable(wrappers); 
	}
	
	public void query(String query, String output) throws Exception {
		Splitter splitter = new Splitter(query, output, table);
		try {
			splitter.run();
			System.out.println("Terminated with success");
		} catch(WrapperQueryException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void getModel() {
		Map<String, String> model = table.getModel();
		
		for (String table: model.keySet()) {
				System.out.println("----- " + table + "\n\n" + model.get(table));
		}
	}
	
	public void cli(String[] args) throws Exception {
		if(args[0].equals("-q")) {
			if (args.length == 1 || args.length > 3) {
				System.out.println("Wrong options: -h to review available options");
				return;
			}
			
			String query = args[1];
			String output = "result.xml";
			if (args.length == 3)
				output = args[2];
				
			this.query(query, output);
		} else if (args[0].equals("-m")) {
			this.getModel();
		} else if (args[0].equals("-h")) {
			System.out.println("Available options:\n" +
					"   -q <query_file> (<output_file>) \n" +
					"         query the Federated database\n" +
					"   -m    display database model\n" +
					"   -h    display help message\n");
		}else {
			System.out.println("Wrong options: -h to review available options");
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		List<IWrapper> wrappers = new ArrayList<IWrapper>();
		wrappers.add(new XMLWrapper("sourcesXML", "XML"));
		wrappers.add(new SqlWrapper("sourcesSQL/fournisseur", "SQL"));
		
		FederatedDatabase fdb = new FederatedDatabase(wrappers);
		
		// default query/output
		String query = "Requetes/complexe1XML1SQL.xq";
		String output = "Requetes/result.xml";
		
		if (args.length > 0) {
			// Command line interface mode
			fdb.cli(args);
		} else {
			// default mode
			fdb.query(query, output);
			//fdb.getModel();
		}
	}
}
