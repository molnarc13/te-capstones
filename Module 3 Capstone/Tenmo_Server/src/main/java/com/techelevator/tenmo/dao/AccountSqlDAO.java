package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;

@Service
public class AccountSqlDAO implements AccountDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public AccountSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public BigDecimal getBalanceByUserId(long id) {
		BigDecimal balance = new BigDecimal("0.00");
		
		String sqlGetBalance = "SELECT balance FROM accounts WHERE user_id = ?";
		
		SqlRowSet results =  jdbcTemplate.queryForRowSet(sqlGetBalance, id);
		
		while(results.next()) {
			balance = results.getBigDecimal("balance");
		}
		
		return balance;
	}

	@Override
	public List<Account> getAllAccounts() {
		List<Account> allAccounts = new ArrayList<Account>();
		
		String sqlAllAccounts = "SELECT * FROM accounts";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllAccounts);
				
		while(results.next()) {
			Account currentAccount = mapRowToAccount(results);
			allAccounts.add(currentAccount);
		}
				
		return allAccounts;
	}
	
	
	private Account mapRowToAccount(SqlRowSet aRow) {
		Account currentAccount = new Account();
		currentAccount.setId(aRow.getLong("account_id"));
		currentAccount.setUserId(aRow.getLong("user_id"));
		currentAccount.setBalance(aRow.getBigDecimal("balance"));
		return currentAccount;
	}

	@Override
	public Account getAccountById(long id) {
		Account anAccount = new Account();
		
		String sqlAnAccount = "SELECT * FROM accounts WHERE user_id = ?";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlAnAccount, id);
		
		while(result.next()) {
			anAccount = mapRowToAccount(result);
		}

		return anAccount;
	}

	@Override
	public Account updateAccount(Account account) {
		String sqlUpdateBalance = "UPDATE accounts "
				+ "SET balance = ? "
				+ "WHERE user_id = ?";

		jdbcTemplate.update(sqlUpdateBalance, account.getBalance(), account.getUserId());
		return account;
	}

}
