package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.Account;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/account")
public class AccountController {
	
	private AccountDAO accountDAO;
	
	public AccountController(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	
    @RequestMapping(path = "/balance/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id) {
    	return accountDAO.getBalanceByUserId(id);
    }
    
    
    @RequestMapping(path ="", method = RequestMethod.GET)
    public List<Account> getAllAccounts(){
    	return accountDAO.getAllAccounts();
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Account getAccountById(@PathVariable long id) {
    	return accountDAO.getAccountById(id);
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Account updateAccount(@RequestBody Account account, @PathVariable long id) {
    	id = account.getId();
    	return accountDAO.updateAccount(account);
    }

}
