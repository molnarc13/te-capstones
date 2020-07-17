package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {
	
	BigDecimal getBalanceByUserId(long id);
	
	List<Account> getAllAccounts();
	
	Account getAccountById(long id);
	
	Account updateAccount(Account account);
	
}
