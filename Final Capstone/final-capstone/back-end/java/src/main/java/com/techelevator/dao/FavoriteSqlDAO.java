package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Favorite;
import com.techelevator.model.UserPhotos;

@Service
public class FavoriteSqlDAO implements FavoriteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public FavoriteSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void addFavorite(Favorite favorite) {
		String sqlInsertFavorite = "INSERT INTO favorites "
								  + "(user_id, photo_id) "
								  + "VALUES(?,?)"
								  ;
		jdbcTemplate.update(sqlInsertFavorite,
							favorite.getUserId(),
							favorite.getPhotoId())
							;
	}

	@Override
	public void deleteFavorite(Favorite favorite) {
		String sqlDeleteFavorite = "DELETE FROM favorites "
								 + "WHERE user_id = ? "
								 + "AND photo_id = ?"
								 ;
		jdbcTemplate.update(sqlDeleteFavorite,
							favorite.getUserId(),
							favorite.getPhotoId())
							;
	}
	
	@Override
	public List<UserPhotos> getFavorites(int userId) {
		List<UserPhotos> favoritePhotos = new ArrayList<>();
		String sqlSelectFavorites = "SELECT user_photos.* "
								  + "FROM user_photos "
								  + "INNER JOIN favorites "
								  + "ON favorites.photo_id = user_photos.id "
								  + "WHERE favorites.user_id = ? "
								  + "ORDER BY user_photos.id DESC"
								  ;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectFavorites, userId);
		while(results.next()) {
			UserPhotos aPhoto = mapRowToUserPhotos(results);
			favoritePhotos.add(aPhoto);
		}
		return favoritePhotos;
	}
	
	/**
	 * A helper method to map the results from SQL SELECT statement
	 * that selects all data from the user_photos table to a UserPhotos
	 * object and returns that object.
	 * 
	 * @param results - SqlRowSet from the SELECT statement in addNewPhoto()
	 * @return - Returns a UserPhotos object for every SqlRowSet in results
	 */
	private UserPhotos mapRowToUserPhotos(SqlRowSet results) {
		UserPhotos aPhoto = new UserPhotos();
		aPhoto.setId(results.getLong("id"));
		aPhoto.setUser_id(results.getInt("user_id"));
		aPhoto.setUsername(results.getString("username"));
		aPhoto.setPhoto_url(results.getString("photo_url"));
		aPhoto.setDescription(results.getString("description"));
		aPhoto.setDate_added(results.getString("date_added"));
		return aPhoto;
	}

}
