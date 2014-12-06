package tools;

import java.io.InputStream;
import javax.xml.xquery.*;

import com.saxonica.xqj.SaxonXQDataSource;



public class XQueryExecutioner {
	
	
	public static String executeQuery(String query) throws XQException {		
		XQDataSource ds = new SaxonXQDataSource();
        XQConnection con = ds.getConnection();
        
        XQPreparedExpression expr = con.prepareExpression(query);
        
        XQSequence seq = expr.executeQuery();	
		
        String result = seq.getSequenceAsString(null);
        
        
		seq.close();
        expr.close();
        con.close();
        
		return result;
	}
	
	public static String executeQuery(InputStream file) throws XQException {		
		XQDataSource ds = new SaxonXQDataSource();
        XQConnection con = ds.getConnection();
        
        XQPreparedExpression expr = con.prepareExpression(file);
        
        XQSequence seq = expr.executeQuery();
        
        String result = seq.getSequenceAsString(null);
        
        
		seq.close();
        expr.close();
        con.close();
        
		return result;
	}
	
	
	/*
	public static void main (String[] args) throws XQException, FileNotFoundException {
		XQueryExecutioner xexec = new XQueryExecutioner();
		
        //String query = "<encheres> { for $o in doc(\"objets.xml\")/objets/obj_tuple/noobj let $e := doc(\"encheres.xml\")/encheres/ench_tuple[noobj/text() = $o/text()]/ench order by avg($e)	return if(count($e) > 2) then (	<article id=\"{$o/text()}\"> {avg($e)}</article>)else()}</encheres>";
        
		String query = "<test> { 1 + 1 } </test>";
		
        System.out.println(xexec.executeQuery(query));
        System.out.println("\n\n");
        //System.out.println(xexec.executeQuery(queryFromFile));
    }
	*/
	
}
