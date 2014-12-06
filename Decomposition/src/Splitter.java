import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Splitter {
	BufferedReader file;
	
	public Splitter (String filename) throws FileNotFoundException {
		file = this.readFile(filename);
		
	}
	
	public BufferedReader readFile(String filename) {
		try {
			return new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			line = file.readLine();
			while (line != null) {
				sb.append(line);
	            sb.append(System.lineSeparator());
	            line = file.readLine();
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void main (String[] args){
		try {
			Splitter sp = new Splitter("query.xq");
			System.out.println(sp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

