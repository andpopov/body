package com.bodymass.app;

import java.util.ArrayList;
import java.util.List;

import com.bodymass.app.data.User;

public class UserDAOImpl implements UserDAO {
	private long userId;
	private List<User> users = new ArrayList<>();
	
	public User findUser(String email, String password) {
		for(int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return user;
			}
		}
		
		return null;
	}
	
	public User addUser(String email, String password) {
		if(findUser(email, password) == null) {
			User user = new User(++userId, email, password);
			users.add(user);
			return user;
		} else {
			return null;
		}
	}
}
