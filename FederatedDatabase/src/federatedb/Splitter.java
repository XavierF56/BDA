package federatedb;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xquery.XQException;

import exception.WrapperQueryException;

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
	 * Output file
	 */
	private String output;
	
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
	public Splitter (String filename, String output, RoutingTable table) throws IOException {
		this.query = "";
		this.output = output;
		this.readFile(filename);
		
		// create temporary folder
		File dir = new File("tmp");
		dir.mkdir();
		
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
	 * @throws Exception 
	 * 
	 */
	public void run() throws Exception {
		String original = query;
		List<String[]> subqueries = new ArrayList<String[]>(); 
		
		Pattern p = Pattern.compile("doc\\(\"([\\w\\.\\-]+)\"\\)(\\/[^\\[\\],\\s]*(\\s*\\[[^\\[\\]]+\\])?(\\s*\\[[^\\[\\]]+\\])?)*");
		
		Matcher m = p.matcher(this.query);
		
		while (m.find()) {
			String[] tmp = new String[3];
			// The full subquery
			tmp[0] = m.group(0);
			
			// The document name
			tmp[1] = m.group(1);
			
			// The XPath query
			//tmp[2] = m.group(2);
			int indexXPath = tmp[0].indexOf("/");
			tmp[2] = tmp[0].substring(indexXPath);
			
			subqueries.add(tmp);
		}
		
		for (String[] subquery : subqueries) {
			StringBuilder newSubquery = new StringBuilder().append("doc(\"")
					.append(this.table.query(subquery[1], subquery[2], new ArrayList<String>(), new ArrayList<String>()))
					.append("\")/res/*");
			
			this.query = this.query.replace(subquery[0], newSubquery.toString());
		}
		
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(output)));
			writer.write(XQueryExecutioner.executeQuery(this.query));
			writer.close();
		} catch (XQException e) {
			throw(new WrapperQueryException("Error in Main query \n" +
					"original query: [" + original + "] \ntrnasformed query:[" + query + "]"));
		}
		
		// delete temporary folder
		File index = new File("tmp");
		String[]entries = index.list();
		for(String s: entries){
		    File currentFile = new File(index.getPath(),s);
		    currentFile.delete();
		}
        index.delete();
	}
}

