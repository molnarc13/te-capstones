package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import com.techelevator.model.UserPhotos;

@Service
public class UserPhotosSqlDAO  implements UserPhotosDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public UserPhotosSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void addNewPhoto(UserPhotos newPhoto) {
		String sqlInsertUserPhotos = "INSERT INTO user_photos "
								   + "(user_id, username, photo_url, description) "
								   + "VALUES(?,?,?,?)"
								   ;
		jdbcTemplate.update(sqlInsertUserPhotos,
							newPhoto.getUser_id(),
							newPhoto.getUsername(),
							newPhoto.getPhoto_url(),
							newPhoto.getDescription())
							;
	}

	@Override
	public List<UserPhotos> getAllPhotos() {
		List<UserPhotos> allPhotos = new ArrayList<>();
		String sqlSelectAllPhotos = "SELECT * "
								  + "FROM user_photos "
								  + "ORDER BY date_added DESC"
								  ;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllPhotos);
		while(results.next()) {
			UserPhotos aPhoto = mapRowToUserPhotos(results);
			allPhotos.add(aPhoto);
		}
		return allPhotos;
	}
	
	@Override
	public UserPhotos getPhoto(UserPhotos aPhoto) {
		String sqlSelectPhoto = "SELECT * "
							  + "FROM user_photos "
							  + "WHERE photo_url = ? "
							  + "ORDER BY date_added DESC"
							  ;
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlSelectPhoto, aPhoto.getPhoto_url());
		while(result.next()) {
			aPhoto = mapRowToUserPhotos(result);
		}
		return aPhoto;
	}
	
	@Override
	public void deletePhoto(Long id) {
		String sqlDeletePhoto = "DELETE FROM user_photos "
							  + "WHERE id = ?"
							  ;
		jdbcTemplate.update(sqlDeletePhoto, id);
	}
	
	@Override
	public UserPhotos getPhotoById(UserPhotos aPhoto) {
		UserPhotos photoToReturn = null;
		String sqlSelectPhoto = "SELECT * "
							  + "FROM user_photos "
							  + "WHERE id = ?"
							  ;
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlSelectPhoto, aPhoto.getId());
		while(result.next()) {
			photoToReturn = mapRowToUserPhotos(result);
		}
		return photoToReturn;
	}
	
	
	private long getNextUserPhotoId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('user_photos_id_seq')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new city");
		}
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
