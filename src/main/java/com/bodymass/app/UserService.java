package com.bodymass.app;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bodymass.app.data.User;
import com.bodymass.app.data.UserDAOTools;

public class UserService {
	private UserDAOTools userDAO = new UserDAOTools();
	
	public String register(String email, String password, String secondPassword) throws SQLException {
		if(email.equals("")){
			return "empty email";
		}
		else if(checkValidEmail(email) == false) {
			return "incorrect email";
		}
		if(userDAO.ifUserExistsByEmail(email)){
			return "registered already";
		}
		else if(password.trim().equals("") || secondPassword.trim().equals("")) {
			return "empty password";
		}
		else if(!password.equals(secondPassword)) {
			return "password mismatch";
		}
		else {
			int result = userDAO.addUser(new User(email, password));
			if (result == 0) {
				return "successful";
			} else if (result == -1) {
				return "error";
			}
		}
		return "undefined";
	}

	public String login(String email, String password) throws SQLException {
		User user = userDAO.findUser(email, password);
		if(email.trim().equals("")){
			return "empty email";
		}
		else if(checkValidEmail(email) == false) {
			return "incorrect email";
		}
		if(password.trim().equals("")){
			return "empty password";
		}
		if(userDAO.ifUserExistsByEmail(email) == false){
			return "no such user";
		}
		else if(user!=null && user.getId()==0) {
			return "wrong password";
		}
		else if(user != null) {
			return "successful";
		} else {
			return "error";
		}
	}

	public User getUser(String email, String password) throws SQLException {
		User user = userDAO.findUser(email, password);
		return user;
	}
	
	/*public static void main(String[] args) throws SQLException {
		UserService service = new UserService();
		//service.register("11111", "111");
		System.out.println("Ok");
	}*/

	public boolean checkValidEmail(String email){
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
