package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.model.User;

public interface IUserDAO {
	User insert(User user) throws UserDAOException;
	User update(User user) throws UserDAOException;
	void delete(int id) throws UserDAOException;
	User getUserById(int id) throws UserDAOException;
	User getUserByUsername(String username) throws UserDAOException;
	boolean isUserValid(String username, String password);
}
