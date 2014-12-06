package federatedb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WrapperXML extends IWrapper{	
	Map<String, String> tablesMap;
	List<String> tables;
	String id;
	
	public WrapperXML(String sourceFolder, String id) {
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

	
	String getModel(String table) {
		return null;
	}	

	List<String> getTables() {
		return tables;
	}

	String executeQuery(String relation, String query, List<String> projections, List<String> selections) {
		String queryToExecute = "<res>\n" +
				       "for $o in doc(\"" + tablesMap.get(relation) + "\")" + query + "\n" +
				       "return <row>{$o}</row>"; 
		return null;
	}
	
	public static void main (String[] args){
		try {
			
			WrapperXML wrap= new WrapperXML("sourcesXML", "XML");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
