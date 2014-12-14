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
}
