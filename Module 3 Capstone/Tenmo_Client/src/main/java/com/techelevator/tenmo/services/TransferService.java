package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;

public class TransferService {
	
	public static String AUTH_TOKEN;
	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	
	public TransferService(String url) {
		this.BASE_URL = url + "transfer/";
	}
	
	public Transfer addTransfer(Transfer transfer) {
		Transfer newTransfer = transfer;
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setBearerAuth(AUTH_TOKEN);
		HttpEntity<?> entity = new HttpEntity<>(transfer, header);
		
		newTransfer = restTemplate.exchange(BASE_URL, HttpMethod.POST, entity, Transfer.class).getBody();
		return newTransfer;
	}
	

	public Transfer makeNewTransfer(Long fromUserId, Long toUserId, BigDecimal balance) {
		Transfer transfer = new Transfer();
		transfer.setAccountFrom(fromUserId);
		transfer.setAccountTo(toUserId);
		transfer.setAmount(balance);
		transfer.setTransferTypeId((long) 2);
		transfer.setTransferStatusId((long) 2);
		return transfer;
	}
	
	
	public Account subtractBalance(Account account, BigDecimal transferAmount) {
		Account updatedAccount = account;
		BigDecimal balance = updatedAccount.getBalance().subtract(transferAmount);
		updatedAccount.setBalance(balance);
		return updatedAccount;
	}
	
	public Account addBalance(Account account, BigDecimal transferAmount) {
		Account updatedAccount = account;
		BigDecimal balance = updatedAccount.getBalance().add(transferAmount);
		updatedAccount.setBalance(balance);
		return updatedAccount;
	}
	
	public Transfer[] getSentTransfers(Long id) {
		Transfer[] sentTransfers = null;
		sentTransfers = restTemplate.exchange(BASE_URL + id + "/sent", HttpMethod.GET, createAuthEntity(), Transfer[].class).getBody();
		return sentTransfers;
	}
	
	public Transfer[] getRequestTransfers(Long id) {
		Transfer[] requestTransfers = null;
		requestTransfers = restTemplate.exchange(BASE_URL + id + "/request", HttpMethod.GET, createAuthEntity(), Transfer[].class).getBody();
		return requestTransfers;
	}
	
	private HttpEntity<?> createAuthEntity() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setBearerAuth(AUTH_TOKEN);
		HttpEntity<?> entity = new HttpEntity<>(header);
		return entity;
	}

}
