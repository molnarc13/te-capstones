package com.techelevator.tenmo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.User;

public class UserService {

	public static String AUTH_TOKEN;
	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();

	public UserService(String url) {
		this.BASE_URL = url + "user/";
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsernameAndId(int id) {	// pass in currentUser.getUser().getId() - long id
		List<String> idAndUsername = new ArrayList<String>();
		idAndUsername = restTemplate.exchange(BASE_URL, HttpMethod.GET, createAuthEntity(), ArrayList.class).getBody();

		List<User> allUsers = new ArrayList<User>();
		for (int i = 0; i < idAndUsername.size(); i++) {
			User user = new User();
			String[] info = idAndUsername.get(i).split(",");
			user.setId(Integer.parseInt(info[0]));
			user.setUsername(info[1]);
			if (!user.getId().equals(id)) {
				allUsers.add(user);
			}
		}

		return allUsers;
	}

	public String getUsernameById(long id) {
		String username = "";
		username = restTemplate.exchange(BASE_URL + id, HttpMethod.GET, createAuthEntity(), String.class).getBody();
		return username;
	}

	private HttpEntity<?> createAuthEntity() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setBearerAuth(AUTH_TOKEN);
		HttpEntity<?> entity = new HttpEntity<>(header);
		return entity;
	}

}
