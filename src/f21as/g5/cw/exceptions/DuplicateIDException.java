package f21as.g5.cw.exceptions;

public class DuplicateIDException extends Exception {

	public DuplicateIDException(String dup){
		super("Duplicate id = " + dup);
	}
}
