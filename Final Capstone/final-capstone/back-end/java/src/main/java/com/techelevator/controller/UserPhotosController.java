package com.techelevator.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.UserPhotosDAO;
import com.techelevator.model.UserPhotos;

@CrossOrigin
@RestController
public class UserPhotosController {

	private UserPhotosDAO userPhotosDAO;
	
	public UserPhotosController(UserPhotosDAO userPhotosDAO) { 
		this.userPhotosDAO = userPhotosDAO;
	} 
	
	/**
	 * Return all column data for every row in the user_photos table
	 */
	@RequestMapping(path="/user_photos", method = RequestMethod.GET)
	public List<UserPhotos> getAllPhotos() {
		logRequest("Request to get all photos from the database.");
		return userPhotosDAO.getAllPhotos();
	}

	/**
	 * Method to add photo to database via URL, e.g., upload photo 
	 */
	@RequestMapping(path="/user_photos/add", method = RequestMethod.POST)	
	public UserPhotos newPhoto(@RequestBody UserPhotos newPhoto) {
		logRequest("Request from user at user_id: "
				 + newPhoto.getUser_id()
				 + " to post a new photo to the user_photos table");
		userPhotosDAO.addNewPhoto(newPhoto);
		return userPhotosDAO.getPhoto(newPhoto);
	}
	
	/**
	 * Deletes a photo in the database linked to the photo information passed in the
	 * RequestBody of the API.
	 * 
	 * @param aPhoto - JSON object matching the data attributes of a UserPhotos object
	 * passed in by the user to delete.
	 */
	@RequestMapping(path="/user_photos/delete/{id}", method = RequestMethod.DELETE)
	public void deletePhoto(@PathVariable int id) {
		logRequest("Request from user to delete a photo (photo id: " 
				 + id 
				 + " from the user_photos table");
		userPhotosDAO.deletePhoto((long) id);
	}
	
	static void logRequest(String message) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp + " - " + message);
	}
}
