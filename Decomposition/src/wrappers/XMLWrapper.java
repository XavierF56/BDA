package wrappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

public class XMLWrapper extends IWrapper{	
	Map<String, String> tablesMap;
	List<String> tables;
	String id;
	String sourceFolder;
	
	public XMLWrapper(String sourceFolder, String id) {
		sourceFolder = sourceFolder;
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
		//InputStream xml = new FileInputStream(sourceFolder + "/" + tablesMap.get(table));
		
		//Document document = documentBuilder.parse(xml);
		//String systemId = document.getDoctype().getSystemId();
		return null;
	}	

	public List<String> getTables() {
		return tables;
	}

	public String executeQuery(String table, String query, List<String> projections, List<String> selections) {
		String queryToExecute = "<res>\n" +
				       "for $o in doc(\"" + tablesMap.get(table) + "\")" + query + "\n" +
				       "return <row>{$o}</row>\n"
				       + "</res>"; 
		System.out.println(queryToExecute);
		return null;
	}
	
	public static void main (String[] args){
		try {
			
			XMLWrapper wrap= new XMLWrapper("sourcesXML", "XML");
			wrap.executeQuery("XMLencheres", "/encheres/ench_tuple", null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
