package wrappers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xquery.XQException;

import tools.*;


public class XMLWrapper extends IWrapper{	
	Map<String, String> tablesMap;
	List<String> tables;
	String id;
	String sourceFolder;
	
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

	
	public String getModel(String table) {

		return null;
	}	

	public List<String> getTables() {
		return tables;
	}

	public String executeQuery(String table, String query, List<String> projections, List<String> selections) {
		String queryToExecute = "<res>\n" +
				       "{for $o in doc(\"sourcesXML/" + tablesMap.get(table) + "\")" + query + "\n" +
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
