package exceptions;

/**
 * InvalidDirectionException is the exception thrown when the
 * direction the empty tile slides is invalid.
 * 
 * @author Edward B.
 */

public class InvalidDirectionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidDirectionException(String message) {
		super(message);
	}
	
}