package gr.aueb.cf.schoolapp.service.util;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dto.UserDTO;

public class UserAuthUtil {
	private static final IUserDAO dao = new UserDAOImpl();
	
	private UserAuthUtil() {}

	public static boolean isAdmin(UserDTO userDTO) {
		String adminPassword = System.getenv("TS_ADMIN_PASSWORD");
		String username = userDTO.getUsername();
		String password = userDTO.getPassword();
		
		return username.equals("admin") && password.equals(adminPassword);
	}
}
