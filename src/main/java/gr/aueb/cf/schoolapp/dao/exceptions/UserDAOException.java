package gr.aueb.cf.schoolapp.dao.exceptions;

import gr.aueb.cf.schoolapp.model.User;

public class UserDAOException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UserDAOException(String s) {
		super(s);
	}
	
	public UserDAOException(User user) {
		super("Error with user: " + user);
	}

}
