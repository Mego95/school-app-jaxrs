package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.util.DBUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements IUserDAO{

	@Override
	public User insert(User user) throws UserDAOException {
		String sql = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES (?, ?)";
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement p = conn.prepareCall(sql)) {
			
			String username = user.getUsername();
			String password = user.getPassword();
			
			if (username.equals("") || password.equals("")) {
				return null;
			}
			
			p.setString(1, username);
			p.setString(2, password);
			p.execute();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserDAOException("Error in inserting user with username: " + user.getUsername());
		}
	}

	@Override
	public User update(User user) throws UserDAOException {
		String sql = "UPDATE USERS SET PASSWORD = ? WHERE ID = ? ";
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement p = conn.prepareCall(sql)) {
			
			int id = user.getId();
			String password = user.getPassword();
			
			if (password.equals("") || String.valueOf(id).equals("")) {
				return null;
			}
			p.setString(1, password);
			p.setInt(2, id);
			p.execute();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserDAOException("Error in updating user with id: " + user.getId());
		}
	}

	@Override
	public void delete(int id) throws UserDAOException {
		String sql = "DELETE FROM USERS WHERE ID = ?";
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement p = conn.prepareCall(sql)) {
			
			p.setInt(1, id);
			p.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserDAOException("Error in deleting user with id: " + id);
		}
		
	}

	@Override
	public User getUserById(int id) throws UserDAOException {
		String sql = "SELECT ID, USERNAME, PASSWORD FROM USERS WHERE ID = ?";
		ResultSet rs;
		User user = null;
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement p = conn.prepareCall(sql)) {
			
			p.setInt(1, id);
			rs = p.executeQuery();
			
			if (rs.next()) {
				user = new User(
						rs.getInt("ID"),
						rs.getString("USERNAME"),
						rs.getString("PASSWORD"));
			}
			
			return user;	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserDAOException("Error in getting user with id: " + id);
		}
	}
	
	@Override
	public User getUserByUsername(String username) throws UserDAOException {
		String sql = "SELECT ID, USERNAME, PASSWORD FROM USERS WHERE USERNAME = ?";
		ResultSet rs;
		User user = null;
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement p = conn.prepareCall(sql)) {
			
			p.setString(1, username);
			rs = p.executeQuery();
			
			if (rs.next()) {
				user = new User(
						rs.getInt("ID"),
						rs.getString("USERNAME"),
						rs.getString("PASSWORD"));
			}
			
			return user;	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserDAOException("Error in getting user with username: " + username);
		}
	}

	@Override
	public boolean isUserValid(String username, String password) {

		if ((username.equals("admin@admin.com")) && (password.equals(System.getenv("TS_ADMIN_PASSWORD")))) {
			return true;
		}

		// TODO
		try {
			String inputPassword = password;
			User user = getUserByUsername(username);

			if (user == null) return false;

			String hashedPassword = user.getPassword();

			return BCrypt.checkpw(inputPassword, hashedPassword);
		} catch (UserDAOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
