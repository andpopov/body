package com.bodymass.app;

import com.bodymass.app.UserDAOImpl;
import com.bodymass.app.data.User;

public class UserService {
	private UserDAO userDAO = new UserDAOImpl();
	
	public Long register(String email, String password) {
		User user = userDAO.addUser(email, password);
		if(user != null) {
			return user.getId();
		} else {
			return -1L;
		}
	}

	public Long login(String email, String password) {
		User user = userDAO.findUser(email, password);
		if(user != null) {
			return user.getId();
		} else {
			return -1L;
		}
	}
}
