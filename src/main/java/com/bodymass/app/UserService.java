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
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		System.out.println(matcher.matches());
		if(matcher.matches() == false) {
			return "incorrect email";
		}
		else if(password.trim().equals("") || secondPassword.trim().equals("")) {
			return "empty password";
		}
		else if(!password.equals(secondPassword)) {
			return "password mismatch";
		}
		else {
			boolean userAlreadyExists = userDAO.ifUserExistsByEmail(email);
			if(userAlreadyExists){
				return "registered already";
			}
			int result = userDAO.addUser(new User(email, password));
			if (result == 0) {
				return "successful";
			} else if (result == -1) {
				return "error";
			}
		}
		return "undefined";
	}

	public int login(String email, String password) throws SQLException {
		User user = userDAO.findUser(email, password);
		if(user!=null && user.getId()==0) {
			return 1;
		}
		else if(user != null) {
			return 0;
		} else {
			return -1;
		}
	}
	
	/*public static void main(String[] args) throws SQLException {
		UserService service = new UserService();
		//service.register("11111", "111");
		System.out.println("Ok");
	}*/
}
