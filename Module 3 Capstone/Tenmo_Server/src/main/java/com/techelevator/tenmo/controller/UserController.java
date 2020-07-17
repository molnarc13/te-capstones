package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.UserDAO;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/user")
public class UserController {

	private UserDAO userDAO;
	
	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<String> getUsernameAndId() {
		return userDAO.getUsernameAndId();
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public String getUsernameById(@PathVariable long id) {
		return userDAO.getUsernameById(id);
	}
	
}
