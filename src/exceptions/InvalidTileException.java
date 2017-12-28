package exceptions;

/**
 * InvalidTileException is the exception thrown when a tile
 * that is not between 0 and the maximum tile value is passed
 * to a method.
 * 
 * @author Edward B.
 */

public class InvalidTileException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidTileException(String message) {
		super(message);
	}
	
}