package federatedb;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wrappers.*;

import javax.xml.xquery.XQException;

import tools.XQueryExecutioner;

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
	public void run() throws IOException {
		List<String[]> subqueries = new ArrayList<String[]>(); 
		
		Pattern p = Pattern.compile("doc\\(\"([\\w\\.\\-]+)\"\\)(\\/[^\\[\\]\\s]*(\\s*\\[[^\\[\\]]+\\])?(\\s*\\[[^\\[\\]]+\\])?)*");
		
		Matcher m = p.matcher(this.query);
		
		while (m.find()) {
			String[] tmp = new String[3];
			// The full subquery
			tmp[0] = m.group(0);
			// The document name
			tmp[1] = m.group(1);
			// The XPath query
			tmp[2] = m.group(2);
			subqueries.add(tmp);
		}
		
		for (String[] subquery : subqueries) {
			StringBuilder newSubquery = new StringBuilder().append("doc(\"")
					.append(this.table.query(subquery[1], subquery[2], new ArrayList<String>(), new ArrayList<String>()))
					.append("\")/res/*");
			
			this.query = this.query.replace(subquery[0], newSubquery.toString());
		}
		
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("result.xml")));
			
			writer.write(XQueryExecutioner.executeQuery(this.query));
			
			writer.close();
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		List<IWrapper> wrappers = new ArrayList<IWrapper>();
		wrappers.add(new XMLWrapper("sourcesXML", "XML"));
		wrappers.add(new SqlWrapper("sourcesSQL", "SQL"));
		
		RoutingTable table = new RoutingTable(wrappers);
		
		Splitter splitter = new Splitter("query.xq", table);
		
		splitter.run();
	}
}

