package federatedb;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author btravers
 *
 */
public class Splitter {
	/**
	 * The query we want to execute.
	 */
	private String query;
	
	/**
	 * The routing table.
	 */
	private RoutingTable table;
	
	/**
	 * Construct a splitter.
	 * @param filename, The file name containing the XQuery request.
	 * @param table, The routing table.
	 * @throws IOException
	 */
	public Splitter (String filename, RoutingTable table) throws IOException {
		this.query = "";
		this.readFile(filename);
		this.table = table;
	}
	
	/**
	 * Read the file and store the query. 
	 * @param filename, The file name containing the XQuery request.
	 * @throws IOException 
	 */
	private void readFile(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
			
		StringBuilder query = new StringBuilder();
			
		String line;
			
		while ((line = reader.readLine()) != null) {
			query.append(line);
		}

		this.query = query.toString();
			
		reader.close();
	}
	
	/**
	 * 
	 */
	public void run() {
		List<String[]> subqueries = new ArrayList<String[]>(); 
		
		Pattern p = Pattern.compile("doc\\(\"([\\w\\.\\-]+)\"\\)(\\/[\\S]*)");
		
		Matcher m = p.matcher(this.query);
		
		while (m.find()) {
			String[] tmp = new String[3];
			tmp[0] = m.group(0);
			tmp[1] = m.group(1);
			tmp[2] = m.group(2);
			subqueries.add(tmp);
		}
		
		Map<String, String> newSubqueries = new HashMap<String, String>();
		
		for (String[] subquery : subqueries) {
			StringBuilder newSubquery = new StringBuilder().append("doc(\"")
					.append(this.table.query(subquery[1], subquery[2], new ArrayList<String>(), new ArrayList<String>()))
					.append("\")/res/row");
			newSubqueries.put(subquery[0], newSubquery.toString());
		}
		
		for (String subquery : newSubqueries.keySet()) {
			this.query.replaceAll(subquery, newSubqueries.get(subquery));

		}
		
	}
}

