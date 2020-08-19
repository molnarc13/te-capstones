package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.UserPhotos;

public interface UserPhotosDAO {

	/**
	 * This method runs an SQL INSERT INTO statement-taking a UserPhotos
	 * object passed in as a parameter with the attributes user_id, photo_url,
	 * and description-and adds that object to the user_photos table in the database.
	 * 
	 * @param newPhoto - A UserPhotos object to be added to the database
	 */
	public void addNewPhoto(UserPhotos newPhoto); 

	/**
	 * This method runs an SQL SELECT to take the data from all the columns
	 * and rows of the user_photos table and maps each row to a UserPhotos
	 * object. Each UserPhotos object is then added to an ArrayList and
	 * then that ArrayList is returned.
	 * 
	 * @return - An ArrayList of UserPhotos objects
	 */
	public List<UserPhotos> getAllPhotos();
	
	/**
	 * Takes a UserPhotos object as a parameter and returns a UserPhotos
	 * object from the user_photos table in the database with the matching
	 * photo_url of the UserPhotos object passed in.
	 * 
	 * @param aPhoto - A UserPhotos object passed in
	 * @return - Returns a UserPhotos object that matches the photo_url of
	 * the UserPhotos object passed in as a parameter.
	 */
	public UserPhotos getPhoto(UserPhotos aPhoto);
	
	/**
	 * Using a photo object passed in by the user, this method deletes
	 * the row of data in the database associated to the id (primary key)
	 * of the photo.
	 * 
	 * @param aPhoto - UserPhotos object passed in by the user to be deleted
	 */
	public void deletePhoto(Long id);

	UserPhotos getPhotoById(UserPhotos aPhoto);
}
