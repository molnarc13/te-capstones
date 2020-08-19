package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Favorite;
import com.techelevator.model.UserPhotos;

public interface FavoriteDAO {
	
	/**
	 * SQL INSERT INTO statement to insert a row of data to
	 * the favorites table using the passed in data: userId
	 * and photoId.
	 * 
	 * @param userId - The id of the currently logged in user
	 * @param photoId - The id of the photo the user selected
	 * to add to their favorites list.
	 */
	public void addFavorite(Favorite favorite);
	
	/**
	 * SQL DELETE statement to delete a row of data from the
	 * favorites table with the data to match the columns
	 * user_id and photo_id.
	 * 
	 * @param userId - The id of the currently logged in user
	 * @param photoId - The id of the photo the user selected
	 * to remove from their favorites list.
	 */
	public void deleteFavorite(Favorite favorite);
	
	public List<UserPhotos> getFavorites(int userId);
}
