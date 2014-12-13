package exception;
import wrappers.*;

@SuppressWarnings("serial")
public class WrapperQueryException extends Exception {
	public WrapperQueryException (IWrapper wrapper, String query) {
		super("In Wrapper" + wrapper.getId() + " with query [" + query +"]");
	}
}
