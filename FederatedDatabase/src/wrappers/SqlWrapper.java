package wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.WrapperQueryException;
import tools.DTDGenerator;
import tools.SqlExecutioner;

public class SqlWrapper implements IWrapper {
	
	private String id;
	private String databasePath;
	private List<String> tables;
	private Map<String, String> tablesMap;
	
	public SqlWrapper(String databasePath, String id) {
		this.id = id;
		this.databasePath = databasePath;
		
		this.tables = queryTables();
	}

	public String getModel(String table) {
		// TODO Stocker le resultat de la requete pour ne la faire qu'une fois
		String query = "SELECT * FROM " + tablesMap.get(table) + " LIMIT 2;";
		String xml = "";
		try {
			xml = SqlExecutioner.executeQuery(databasePath, query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DTDGenerator app = new DTDGenerator();
		
		app.runString(xml);
       
		return app.printDTD();
	}
	

	public List<String> getTables() {
		return tables;
	}
	
	public String getId() {
		return id;
	}


	public String executeQuery(String relation, String query, List<String> projections, List<String> selections) throws WrapperQueryException {
		// remove res(root of xml) at the begining of the query
		int indexRoot = query.indexOf("/res");
		if (indexRoot < 2 && indexRoot >= 0) {
			query = query.substring(indexRoot + 4);
		}
			
		String sqlQuery = translateQuery(tablesMap.get(relation), query);
		System.out.println(sqlQuery);
		
		
		try {
			return SqlExecutioner.executeQuery(databasePath, sqlQuery);
		} catch (SQLException e) {
			throw(new WrapperQueryException(this, query));
		}
	}

	private List<String> queryTables() {
		List<String> tablesList = new ArrayList<String>();
		tablesMap = new HashMap<String, String>();
		
		String query = "SELECT name FROM sqlite_master WHERE type='table';";
		String xmlTables = "";
		try {
			xmlTables = SqlExecutioner.executeQuery(databasePath, query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Pattern pattern = Pattern.compile("<name>(.*?)</name>");
		Matcher matcher = pattern.matcher(xmlTables);
		while (matcher.find()) {
			String name = matcher.group(1).toLowerCase();
			tablesMap.put(this.id + name, name);
			tablesList.add(id + name);
		}
		
		return tablesList;
	}

	/**
	 * Parse the XPath query to SQL.
	 * @param relation
	 * @param queryString
	 * @return
	 */
	private String translateQuery(String relation, String queryString) {
		//System.out.println("\nTranslation of " + queryString);
		
		String column = extractProjection(queryString);		
		List<String> where = extractSelections(queryString);
		
		return buildSqlQuery(relation, column, where);
	}
	
	/**
	 * Extract the projection out of the XPath query.
	 * @param queryString
	 * @return
	 */
	private String extractProjection(String queryString) {
		String projection = "*";
		
		int rowIndex = queryString.indexOf("/tuple");
		if (rowIndex >= 0) {
			String sub = queryString.substring(rowIndex + 6).replaceAll("\\[.*?\\]", "");
			if (sub.length() > 0) {
				projection = sub.replaceAll("/", "");
			}
		}
		else {
			int doubleSlashIndex = queryString.indexOf("//");
			if (doubleSlashIndex >= 0) {
				String sub = queryString.substring(rowIndex + 2).replaceAll("\\[.*?\\]", "");
				
				if (sub.length() > 0) {
					projection = sub.replaceAll("/", "");
				}
			}
		}
		
		return projection;
	}
	
	/**
	 * Extract the selections out of the XPath query.
	 * @param queryString
	 * @return
	 */
	private List<String> extractSelections(String queryString) {
		List<String> selections = new ArrayList<String>();
		
		// /$1[$3=$]
		Pattern pattern = Pattern.compile("/([^/]*?)\\[((.*?)[<>=!]{1,2}.*?)\\]");
		Matcher matcher = pattern.matcher(queryString);
		while (matcher.find()) {			
			String selection = matcher.group(2).replaceAll("\\./", "");
			selection = selection.replaceAll("\\.", matcher.group(1));
			
			selections.add(selection.replaceAll("\\/text\\(\\)", ""));
		}
		
		return selections;
	}
	
	/**
	 * Build the SQL query. No computing here !
	 * @param column
	 * @param relation
	 * @return
	 */
	private String buildSqlQuery(String relation, String projection, List<String> selections) {
		StringBuilder sqlQueryBuilder = new StringBuilder();
		
		sqlQueryBuilder.append("SELECT ").append(projection);
		sqlQueryBuilder.append(" FROM ").append(relation);
		
		if (selections != null && selections.size() > 0) {
			String prefix = " WHERE ";
			
			for (String selection : selections) {
				sqlQueryBuilder.append(prefix).append(selection);
				prefix = " AND ";
			}
		}
		
		return sqlQueryBuilder.toString();
	}
}