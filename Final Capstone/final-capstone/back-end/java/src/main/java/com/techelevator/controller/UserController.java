package com.techelevator.controller;

import java.sql.Timestamp;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.UserDAO;
import com.techelevator.model.User;

@CrossOrigin
@RestController
public class UserController {
	
	private UserDAO userDAO;
	
	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	/**
	 * Replaces a current username of a user (at user_id passed in as a PathVariable)
	 * to a new String passed in as the RequestBody. Retrieves the new username from
	 * the database and returns that as a String to the user.
	 * 
	 * @param id - PathVariable - The id of the current user changing their username
	 * @param newUsername - RequestBody - String passed in to change the username to
	 * @return - Returns the String grabbed from the updated username field in the database
	 */
	@RequestMapping(path = "/username/{id}", method = RequestMethod.PUT)
	public String updateUsername(@PathVariable int id, @RequestBody User newUsername) {
		logRequest("Request to update a username, (new username: " + newUsername.getUsername() + ") to the users table at the id: " + id);
		return userDAO.updateUsername((long) id, newUsername);
	}
	
	@RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
	public String getUsernameById(@PathVariable int id) {
		logRequest("Request to get a username by a user id (user id: " + id + ")");
		User user = userDAO.getUserById((long) id);
		return user.getUsername();
	}
	
	static void logRequest(String message) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp + " - " + message);
	}
}
