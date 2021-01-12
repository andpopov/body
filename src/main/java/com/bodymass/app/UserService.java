package com.bodymass.app;

import java.sql.SQLException;

import com.bodymass.app.data.User;
import com.bodymass.app.data.UserDAOTools;

public class UserService {
	private UserDAOTools userDAO = new UserDAOTools();
	
	public int register(String email, String password) throws SQLException {
		int result = userDAO.addUser(new User(email, password));
		return result;
	}

	public int login(String email, String password) throws SQLException {
		User user = userDAO.findUser(email, password);
		if(user != null) {
			return 0;
		} else {
			return -1;
		}
	}
}
