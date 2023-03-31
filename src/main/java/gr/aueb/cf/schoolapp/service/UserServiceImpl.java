package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.exceptions.UserNotFoundException;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements IUserService{
	private final IUserDAO dao;
	
	public UserServiceImpl(IUserDAO dao) {
		this.dao = dao;
	}

	@Override
	public User insertUser(UserDTO userDTO) throws UserDAOException {
		if (userDTO == null) return null;
		
		try {
			User user = mapUser(userDTO);
			return dao.insert(user);
		} catch (UserDAOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public User updateUser(UserDTO userDTO) throws UserDAOException, UserNotFoundException {
		if (userDTO == null) return null;
		
		try {
			
			if (dao.getUserById(userDTO.getId()) == null) {
				throw new UserNotFoundException("User with id: " + userDTO.getId() + " not found");
			}
			
			User user = mapUser(userDTO);
			return dao.update(user);
		} catch (UserDAOException | UserNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteUser(int id) throws UserDAOException, UserNotFoundException {
		
		try {
			if (dao.getUserById(id) == null) {
				throw new UserNotFoundException("User with id: " + id + " not found");
			}
			
			dao.delete(id);
		} catch (UserDAOException | UserNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public User getUserById(int id) throws UserDAOException, UserNotFoundException {
		try {
			
			if (dao.getUserById(id) == null) {
				throw new UserNotFoundException("User with id: " + id + " not found");
			}
			
			return dao.getUserById(id);
		} catch (UserDAOException | UserNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public User getUserByUsername(String username) throws UserDAOException{
		try {
			return dao.getUserByUsername(username);
		} catch (UserDAOException e) {
			//e.printStackTrace();
			throw e;
		}
	}
	
	private User mapUser(UserDTO dto) {
		int workload = 12;
		String salt = BCrypt.gensalt(workload);
		String inputPassword = dto.getPassword();
		String hashedPassword = BCrypt.hashpw(inputPassword, salt);
		
		return new User(dto.getId(), dto.getUsername(), hashedPassword);
	}

}
