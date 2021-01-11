package com.bodymass.app;

public interface UserDAO {
	public User findUser(String email, String password);
	
	public User addUser(String email, String password);
}
