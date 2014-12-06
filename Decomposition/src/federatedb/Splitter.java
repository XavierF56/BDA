package federatedb;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Splitter {
	private String query;
	
	public Splitter (String filename) throws FileNotFoundException {
		this.query = "";
		this.readFile(filename);
	}
	
	public String getQuery() {
		return query;
	}
	
	public void readFile(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			String line = "";
			
			while ((line = reader.readLine()) != null) {
				this.query += line;
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void splitQuery() {
		List<String[]> subqueries = new ArrayList<String[]>(); 
		
		Pattern p = Pattern.compile("doc\\(\"([\\w\\.\\-]+)\"\\)(\\/[\\S]*)");
		
		Matcher m = p.matcher(this.query);
		
		while (m.find()) {
			String[] tmp = new String[2];
			tmp[0] = m.group(1);
			tmp[1] = m.group(2);
			subqueries.add(tmp);
			
			System.out.println("Table: " + tmp[0] + " Xpath: " + tmp[1]);
		}
		
		
	}
	
	public static void main2 (String[] args){
		try {
			Splitter sp = new Splitter("query.xq");
			System.out.println(sp.getQuery());
			sp.splitQuery();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

