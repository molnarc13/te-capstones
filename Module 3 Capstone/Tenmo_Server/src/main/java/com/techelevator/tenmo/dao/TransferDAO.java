package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	Transfer addTransfer(Transfer transfer);
	
	List<Transfer> getSentTransferById(long id);
	
	List<Transfer> getRequestTransferById(long id);

}
