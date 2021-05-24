package f21as.g5.cw.exceptions;
public class InvalidIDException extends Exception{
	public InvalidIDException(String id){
		super("Invalid ID = " + id);
	}
}