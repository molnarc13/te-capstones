package com.techelevator.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.FavoriteDAO;
import com.techelevator.model.Favorite;
import com.techelevator.model.UserPhotos;

@CrossOrigin
@RestController
public class FavoritesController {
	
	private FavoriteDAO favoriteDAO;
	
	public FavoritesController(FavoriteDAO favoriteDAO) {
		this.favoriteDAO = favoriteDAO;
	}

	/**
	 * Adds a row to the favorites table using the information passed in by the user.
	 * 
	 * @param id - The id of the currently logged in user.
	 * @param photoId - The id of the photo the user wants to add to the favorites list.
	 */
	@RequestMapping(path = "/favorites/{id}/add", method = RequestMethod.POST)
	public void addFavorite(@PathVariable int id, @RequestBody Favorite favorite) {
		logRequest("Request to add a favorited photo to the favorites table.");
		favoriteDAO.addFavorite(favorite);
	}
	
	/**
	 * Deletes a row from the favorites table using the information passed in by the user.
	 * 
	 * @param id - The id of the currently logged in user.
	 * @param photoId - The id of the photo the user wants to delete from the favorites list.
	 */
	@RequestMapping(path = "/favorites/{id}/delete", method = RequestMethod.DELETE)
	public void deleteFavorite(@PathVariable int id, @RequestBody Favorite favorite) {
		logRequest("Request to delete a favorited photo from the favorites table.");
		favoriteDAO.deleteFavorite(favorite);
	}
	
	@RequestMapping(path = "/favorites/{id}", method = RequestMethod.GET)
	public List<UserPhotos> getFavorites(@PathVariable int id) {
		logRequest("Request to get all photos linked to the favorites table by the user id: " + id);
		return favoriteDAO.getFavorites(id);
	}
	
	static void logRequest(String message) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp + " - " + message);
	}

}
