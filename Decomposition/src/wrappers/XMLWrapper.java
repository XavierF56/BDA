package wrappers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xquery.XQException;

import tools.*;

/**
 * XML Wrapper
 * @author xfraboul
 */
public class XMLWrapper implements IWrapper{	
	private Map<String, String> tablesMap;
	private List<String> tables;
	private String id;
	private String sourceFolder;
	
	/**
	 * Constructor of XMLWrapper
	 * @param sourceFolder path to for the XML files
	 * @param id string used to prefix the table name
	 */
	public XMLWrapper(String sourceFolder, String id) {
		this.sourceFolder = sourceFolder;
		tablesMap = new HashMap<String, String>(); 
		tables = new ArrayList<String>();
		
		File folder = new File(sourceFolder);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	String fileNameEx = file.getName();
		    	String fileName = fileNameEx.substring(0, fileNameEx.lastIndexOf('.'));
		        tablesMap.put(id + fileName, fileNameEx);
		        tables.add(id + fileName);
		    }
		}
	}

	/**
	 * Fetch the model of the database for a given table.
	 * @param table
	 * @return DTD
	 */
	public String getModel(String table) {
		DTDGenerator app = new DTDGenerator();
		
		app.run(sourceFolder + "/" + tablesMap.get(table));
       
		return app.printDTD();
	}	
	
	/**
	 * Fetch the names of the tables of the database. 
	 * Names are prefixed by the type of Database (e.g. "SQL" for an SQL database).
	 * @return
	 */
	public List<String> getTables() {
		return tables;
	}
	
	/**
	 * Execute a query and return the XML
	 * @param table
	 * @param query
	 * @param projections
	 * @param selections
	 * return xml as a stirng
	 */
	public String executeQuery(String table, String query, List<String> projections, List<String> selections) {
		String queryToExecute = "<res>\n" +
				       "{for $o in doc(\"" + sourceFolder + "/" + tablesMap.get(table) + "\")" + query + "\n" +
				       "return $o\n"
				       + "}</res>"; 
		String res = null;
		
		try {
			res = XQueryExecutioner.executeQuery(queryToExecute);
		} catch (XQException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	
	public static void maintest(String[] args){
		try {
			XMLWrapper wrap= new XMLWrapper("sourcesXML", "XML");
			System.out.println(wrap.executeQuery("XMLencheres", "/encheres/ench_tuple", null, null));
			System.out.println(wrap.getModel("XMLencheres"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
