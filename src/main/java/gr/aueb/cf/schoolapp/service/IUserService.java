package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.exceptions.UserNotFoundException;

public interface IUserService {
	User insertUser(UserDTO userDTO) throws UserDAOException;
	User updateUser(UserDTO userDTO) throws UserDAOException, UserNotFoundException;
	void deleteUser(int id) throws UserDAOException, UserNotFoundException;
	User getUserById(int id) throws UserDAOException, UserNotFoundException;
	User getUserByUsername(String username) throws UserDAOException;
}
