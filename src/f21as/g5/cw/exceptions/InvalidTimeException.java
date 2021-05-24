package f21as.g5.cw.exceptions;

public class InvalidTimeException extends Exception{
	public InvalidTimeException(String validFormat){
		super("Invalid Time format = " + validFormat);
	}

}

