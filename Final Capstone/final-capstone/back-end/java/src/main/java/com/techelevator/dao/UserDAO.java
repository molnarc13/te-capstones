package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    User getUserById(Long userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password, String role);
    
    /**
     * This method runs an SQL UPDATE statement to update the username of
     * an existing user from the users table in the database. Taking the
     * current username of the user and replacing it with the new username.
     * After updating, the new username is retrieved from the database and
     * returned as a String.
     * 
     * @param currentUsername - Username of the current user before updating
     * @param newUsername - Username to replace the current username
     * @return - Returns the new username from the updated user in the database
     */
    public String updateUsername(long id, User newUsername);
}
