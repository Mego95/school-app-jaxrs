package gr.aueb.cf.schoolapp.service.exceptions;

public class UserNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(String s) {
		super(s);
	}

}
