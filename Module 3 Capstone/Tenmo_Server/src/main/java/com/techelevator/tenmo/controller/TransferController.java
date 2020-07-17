package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/transfer")
public class TransferController {
	
	private TransferDAO transferDAO;
	
	public TransferController(TransferDAO transferDAO) {
		this.transferDAO = transferDAO;
	}
	
	@RequestMapping(path = "", method = RequestMethod.POST)
	public Transfer addTransfer (@RequestBody Transfer transfer) {
		return transferDAO.addTransfer(transfer);
	}
	
	@RequestMapping(path = "/{id}/sent", method = RequestMethod.GET)
	public List<Transfer> getSentTransferById(@PathVariable long id){
		return transferDAO.getSentTransferById(id);
	}
	
	@RequestMapping(path = "/{id}/request", method = RequestMethod.GET)
	public List<Transfer> getRequestTransferById(@PathVariable long id){
		return transferDAO.getRequestTransferById(id);
	}

}
