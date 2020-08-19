package com.techelevator.model;

public class UserPhotos {
	
	// one instance variable for every column
	// data type must match (be compatible) with the table column data types
	
	private Long id; 
	private int user_id;
	private String username;
	private String photo_url;	
	private String description;
	private String date_added;	

	public UserPhotos() { }
	
	public UserPhotos(Long id, int user_id, String username, String photo_url, String description, String date_added) {
		this.id = id;
		this.user_id = user_id;
		this.username = username;
		this.description = description;
		this.date_added = date_added;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public int getUser_id() {
		return user_id;  
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate_added() {
		return date_added;
	}

	public void setDate_added(String date) {
		this.date_added = date;
	}

	@Override
	public String toString() {
		return "UserPhotos [id=" + id + ", user_id=" + user_id + ", photo_url=" + photo_url + ", description="
				+ description + ", date_added=" + date_added + "]";
	}

}
