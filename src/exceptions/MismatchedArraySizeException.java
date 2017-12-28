package exceptions;

/**
 * MismatchedArraySizeException is the exception thrown when an
 * array passed to a method does not match the size of the board.
 * 
 * @author Edward B.
 */

public class MismatchedArraySizeException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public MismatchedArraySizeException(String message) {
		super(message);
	}
	
}