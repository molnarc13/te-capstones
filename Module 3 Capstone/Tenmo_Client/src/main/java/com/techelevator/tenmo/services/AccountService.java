package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;

public class AccountService {

	public static String AUTH_TOKEN;
	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();

	public AccountService(String url) {
		this.BASE_URL = url + "account/";
	}

	public BigDecimal getBalance(int id) {
		BigDecimal balance = new BigDecimal("0.00");
		balance = restTemplate.exchange(BASE_URL + "balance/" + id, HttpMethod.GET, createEntity(), BigDecimal.class).getBody();
		return balance;
	}

	public Account[] getAllAccounts(){
		Account[] allAccounts = null;
		allAccounts = restTemplate.exchange(BASE_URL, HttpMethod.GET, createEntity(), Account[].class).getBody();
		return allAccounts;
	}

	public boolean checkBalance(BigDecimal currentUserBalance, BigDecimal transferAmount) {
		if(currentUserBalance.compareTo(transferAmount) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public Account getAccountById(long id) {
		Account anAccount = new Account();
		anAccount = restTemplate.exchange(BASE_URL + id, HttpMethod.GET, createEntity(), Account.class).getBody();
		return anAccount;
	}

	public Account updateAccount(Account account) {
		Account anAccount = account;

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setBearerAuth(AUTH_TOKEN);
		HttpEntity<?> entity = new HttpEntity<>(account, header);
		anAccount = restTemplate.exchange(BASE_URL + account.getUserId(), HttpMethod.PUT, entity, Account.class).getBody();
		return anAccount;
	}

	private HttpEntity<?> createEntity() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setBearerAuth(AUTH_TOKEN);
		HttpEntity<?> entity = new HttpEntity<>(header);
		return entity;
	}

}
