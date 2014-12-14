package exception;

@SuppressWarnings("serial")
public class WrapperQueryException extends Exception {
	String msg;
	
	public WrapperQueryException (String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public String getMessage() {
		return msg;
	}
}
