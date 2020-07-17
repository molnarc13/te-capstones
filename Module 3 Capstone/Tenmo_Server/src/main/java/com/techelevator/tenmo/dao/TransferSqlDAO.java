package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;

@Service
public class TransferSqlDAO implements TransferDAO {
	
	private static List<Transfer> allTransfers = new ArrayList<>();
	
	private JdbcTemplate jdbcTemplate;
	
	public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	@SuppressWarnings("static-access")
	@Override
	public Transfer addTransfer(Transfer transfer) {
		
		transfer.setTransferId(getMaxIdPlusOne());
		
		String sqlAddTransfer = "INSERT INTO transfers "
							  + "(transfer_id, "
							  + "transfer_type_id, " 
							  + "transfer_status_id, "
							  + "account_from, "
							  + "account_to, " 
							  + "amount) "
							  + "VALUES(?,?,?,?,?,?)";
		
		jdbcTemplate.update(sqlAddTransfer, transfer.getTransferId(), transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
		this.allTransfers.add(transfer);
		return transfer;
			
	}

	private Long getMaxId() {
		Long maxId = (long) 0;
		
		for(Transfer transfer : allTransfers) {
			if(transfer.getTransferId() > maxId) {
				maxId = transfer.getTransferId();
			}
		}
		return maxId;
	}
	
	
	private Long getMaxIdPlusOne() {
		return getMaxId() + 1;
	}


	@Override
	public List<Transfer> getSentTransferById(long id) {
		List<Transfer> allTransfers = new ArrayList<Transfer>();
		
		String sqlAllTransfers = "SELECT * FROM transfers WHERE account_from = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllTransfers, id);
		
		while(results.next()) {
			Transfer currentTransfer = mapRowToTransfer(results);
			allTransfers.add(currentTransfer);
		}
		
		return allTransfers;
	}
	
	private Transfer mapRowToTransfer(SqlRowSet aRow) {
		Transfer currentTransfer = new Transfer();
		currentTransfer.setTransferId(aRow.getLong("transfer_id"));
		currentTransfer.setTransferTypeId(aRow.getLong("transfer_type_id"));
		currentTransfer.setTransferStatusId(aRow.getLong("transfer_status_id"));
		currentTransfer.setAccountFrom(aRow.getLong("account_from"));
		currentTransfer.setAccountTo(aRow.getLong("account_to"));
		currentTransfer.setAmount(aRow.getBigDecimal("amount"));
		return currentTransfer;
	}


	@Override
	public List<Transfer> getRequestTransferById(long id) {
		List<Transfer> allTransfers = new ArrayList<Transfer>();
		
		String sqlAllTransfers = "SELECT * FROM transfers WHERE account_to = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllTransfers, id);
		
		while(results.next()) {
			Transfer currentTransfer = mapRowToTransfer(results);
			allTransfers.add(currentTransfer);
		}
		
		return allTransfers;
	}

}
