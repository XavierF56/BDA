package wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlWrapper extends IWrapper {

	@Override
	public String getModel(String table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeQuery(String relation, String query, List<String> projections,
			List<String> selections) {
		return translateQuery(relation, query);
	}

	/**
	 * Parse the XPath query to SQL.
	 * @param relation
	 * @param queryString
	 * @return
	 */
	private String translateQuery(String relation, String queryString) {
		System.out.println("\nTranslation of " + queryString);
		queryString = queryString.replaceAll(" ", "");
		
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
		
		int rowIndex = queryString.indexOf("/row");
		if (rowIndex >= 0) {
			String sub = queryString.substring(rowIndex + 4).replaceAll("\\[.*?\\]", "");
			
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
		Pattern pattern = Pattern.compile("/(.*?)\\[(\\s*(.*?)\\s*=.*?)\\]");
		Matcher matcher = pattern.matcher(queryString);
		while (matcher.find()) {			
			String selection = matcher.group(2).replaceAll("./", "");
			if (matcher.group(3).equalsIgnoreCase(".")) {
				selection = selection.replaceFirst(".", matcher.group(1));
			}
			selections.add(selection);
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