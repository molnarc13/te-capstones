package com.techelevator.dao;

import com.techelevator.model.User;
import com.techelevator.model.UserPhotos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author student
 *
 */
public class UserSqlDaoIntegrationTest extends DAOIntegrationTest {

    
	private static final String TEST_USERNAME = "TEGram_marTEG";
	private UserDAO dao;
	private UserPhotosDAO userSqlDAO;
    

    @Before
    public void setup() {
    	// what about UserPhotosDAO
    	String sqlInsertUser = "INSERT INTO users (username, password_hash, role) VALUES (?, 'password_hash123', 're')";
        DataSource dataSource = this.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sqlInsertUser, TEST_USERNAME);
        userSqlDAO = new UserPhotosSqlDAO(jdbcTemplate);
        dao = new UserSqlDAO(jdbcTemplate);
    }

    @Test
    public void createNewUser() {
        boolean userCreated = dao.create("TEST_USER","test_password","user");
        Assert.assertTrue(userCreated);
        User user = dao.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }
    
    @Test                                  
    public void test_addNewPhoto_and_read_it_back() throws SQLException {

    	UserPhotos userPhotos = getUserPhotos(1, TEST_USERNAME, "username" , "pasword_123");
    	
    	userSqlDAO.addNewPhoto(userPhotos);
    	UserPhotos addPhotos = userSqlDAO.getPhoto(userPhotos);
    	
    	assertNotEquals(null, userPhotos.getPhoto_url());
    	assertUserPhotosAreEqual(userPhotos, addPhotos);
    	
    }
    
    @Test
    public void test_get_one_photo() throws SQLException {
    	UserPhotos userPhotos = getUserPhotos(1, TEST_USERNAME , "username", "description");
    	
    	userSqlDAO.addNewPhoto(userPhotos);
    	UserPhotos addPhotos = userSqlDAO.getPhoto(userPhotos);
    	
    	assertNotEquals(null, userPhotos.getPhoto_url());
    	assertUserPhotosAreEqual(userPhotos, addPhotos);
    	
    	
    }
    
    @Test
    public void return_all_photos_for_user() {
    	List<UserPhotos> userPhotos = userSqlDAO.getAllPhotos();
    	
    	int userPhotoSize = userPhotos.size();
    	UserPhotos addUserPhotos = getUserPhotos(1, TEST_USERNAME ,"username", "description");
    	userSqlDAO.addNewPhoto(addUserPhotos);
    	
    	userPhotos = userSqlDAO.getAllPhotos();
    	
    	Assert.assertEquals(userPhotoSize+1, userPhotos.size());
    }
        
    @Test
    public void test_delete_userPhotos() { 
    	UserPhotos userPhotos = getUserPhotos(1, TEST_USERNAME , "username" ,"description");
    	userSqlDAO.addNewPhoto(userPhotos);
    	
    	userSqlDAO.deletePhoto(userPhotos.getId());
    	
    	UserPhotos deletedPhoto = userSqlDAO.getPhotoById(userPhotos);
    	assertNull(deletedPhoto);
    		
    }  
	  @Test 
	  public void test_updateUsername() { 
      String password = "jkls;ae";
      String role = "role";
      User user = createTestUser();
	  dao.create(TEST_USERNAME, password, role);
	  user.setUsername(TEST_USERNAME);
		  	
	  assertNotNull(user);  
	  } 
	  
	private User createTestUser() {
		long id = 12345668;
		String password = "kseosnlw";
		User newUser = new User();
		newUser.setId(id);
		newUser.setUsername(TEST_USERNAME);
		newUser.setPassword(password);
		return newUser;	
	}
    
    private UserPhotos getUserPhotos(int user_id, String username, String photo_url, String description) {
    	UserPhotos userPhotos = new UserPhotos();
    	userPhotos.setUser_id(user_id);
    	userPhotos.setUsername(username);
    	userPhotos.setPhoto_url(photo_url);
    	userPhotos.setDescription(description);
    	return userPhotos;
    	
    }
    
    private void assertUserPhotosAreEqual(UserPhotos expected, UserPhotos actual) {
    	assertEquals(expected.getUser_id(), actual.getUser_id());
    	assertEquals(expected.getPhoto_url(), actual.getPhoto_url());
		assertEquals(expected.getDescription(), actual.getDescription());
    }
    

}
