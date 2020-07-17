package com.techelevator.tenmo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String TRANSFER_MENU_OPTION_SENT = "View sent transfers";
	private static final String TRANSFER_MENU_OPTION_REQUEST = "View request transfers";
	private static final String[] TRANSFER_MENU_OPTIONS = {TRANSFER_MENU_OPTION_SENT, TRANSFER_MENU_OPTION_REQUEST, MENU_OPTION_EXIT};


	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private AccountService accountService;
	private UserService userService;
	private TransferService transferService;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new AccountService(API_BASE_URL), new UserService(API_BASE_URL), new TransferService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService, AccountService accountService, UserService userService, TransferService transferService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.accountService = accountService;
		this.userService = userService;
		this.transferService = transferService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		int id = currentUser.getUser().getId();
		AccountService.AUTH_TOKEN = currentUser.getToken();
		System.out.println("Your current account balance is: $" + accountService.getBalance(id));
	}

	private void viewTransferHistory() {
		TransferService.AUTH_TOKEN = currentUser.getToken();
		UserService.AUTH_TOKEN = currentUser.getToken();
		boolean shouldLoop = true;
		while(shouldLoop) {
			String choice = (String)console.getChoiceFromOptions(TRANSFER_MENU_OPTIONS);
			if(TRANSFER_MENU_OPTION_SENT.equals(choice)) {
				Transfer[] sentTransfers = transferService.getSentTransfers((long)currentUser.getUser().getId());
				if(sentTransfers.length == 0) {
					System.out.println("You have no sent transfers.");
				} else {
					for(Transfer transfer : sentTransfers) {
						System.out.println("Transfer Id: " + transfer.getTransferId()
						+ ". To: " + userService.getUsernameById(transfer.getAccountTo())
						+ ". Amount $" + transfer.getAmount());
					}
					getSentTransferDetails(sentTransfers);
				}
			} else if(TRANSFER_MENU_OPTION_REQUEST.equals(choice)) {
				Transfer[] requestTransfers = transferService.getRequestTransfers((long)currentUser.getUser().getId());
				if(requestTransfers.length == 0) {
					System.out.println("You have no request transfers.");
				} else {
					for(Transfer transfer : requestTransfers) {
						System.out.println("Transfer Id: " + transfer.getTransferId()
						+ ". From: " + userService.getUsernameById(transfer.getAccountTo())
						+ ". Amount $" + transfer.getAmount());
					}
					getRequestTransferDetails(requestTransfers);
				}
			} else {
				shouldLoop = false;
			}
		}
	}

	private void viewPendingRequests() {
		// Auto-generated method stub

	}

	private void sendBucks() {
		UserService.AUTH_TOKEN = currentUser.getToken();
		List<User> allUsers = new ArrayList<User>(userService.getUsernameAndId(currentUser.getUser().getId()));
		User exitOption = new User();
		exitOption.setId(0);
		exitOption.setUsername("Exit");
		allUsers.add(exitOption);
		boolean shouldLoop = true;
		while(shouldLoop) {
			displayAllUsers(allUsers);
			// private method that loops through our array displaying optionNumber userId and userName for each User
			String choice = console.getUserInput("\nEnter option number of the user you are sending to (or " + allUsers.size() + " to Exit)");
			int userOption = Integer.parseInt(choice);
			if(userOption > 0 && userOption <= allUsers.size()) {
				if(userOption != allUsers.size()) {
					TransferService.AUTH_TOKEN = currentUser.getToken();
					User selectedUser = allUsers.get(userOption-1);
					// Get transferAmount from user
					BigDecimal transferAmount = console.getTransferAmount("\nEnter amount");
					BigDecimal userBalance = accountService.getBalance(currentUser.getUser().getId());

					if(accountService.checkBalance(userBalance, transferAmount)) {
						Account fromAccount = accountService.getAccountById((long) currentUser.getUser().getId());
						fromAccount = transferService.subtractBalance(fromAccount, transferAmount);
						Account toAccount = accountService.getAccountById((long) selectedUser.getId());
						toAccount = transferService.addBalance(toAccount, transferAmount);
						accountService.updateAccount(fromAccount);
						accountService.updateAccount(toAccount);
						// make a transfer object passing in selectedUser.getId, currentUser.getId, transferAmount
						Transfer newTransfer = transferService.makeNewTransfer((long) currentUser.getUser().getId(), (long) selectedUser.getId(), transferAmount);
						// addTransfer() from TransferService passing in transfer object just created
						transferService.addTransfer(newTransfer);
						System.out.println("Your transfer amount of $" + transferAmount.toString() + " has been successfully transfered!");
						shouldLoop = false;
					} else {
						System.out.println("\nI'm sorry, you do not have sufficient funds in your account to transfer the desired amount.");
						shouldLoop = false;
					}
				} else {
					shouldLoop = false;
				}
			} else {
				System.out.println("\nOops! You entered an invalid option number. Please try again.\n");
			}
		}
	}

	private void requestBucks() {
		// Auto-generated method stub

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) //will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch(AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}

	private void displayAllUsers(List<User> allUsers) {
		int i = 0;
		while(i < allUsers.size()) {
			if (allUsers.get(i).getId() == 0) {
				System.out.println((i + 1) + ") " + allUsers.get(i).getUsername());
			} else {
				int optionNumber = i + 1;
				System.out.println(optionNumber + ") " + allUsers.get(i));
			}
			i++;
		}
	}
	
	private void getSentTransferDetails(Transfer[] transfers) {
		boolean shouldLoop = true;
		while(shouldLoop) {
			Integer choice = console.getUserInputInteger("\nPlease enter transfer ID to view details (or 0 to cancel)");
			List<Integer> idList = new ArrayList<Integer>();
			for(Transfer transfer : transfers) { // take transfers[] loop through each transfer
				int id = Integer.parseInt(transfer.getTransferId().toString());
				idList.add(id); // pull out the transfer ID and add to a new idArray of integers
			}
			if(idList.contains(choice) || choice == 0) {
				if(choice != 0) {
					for(Transfer transfer : transfers) {
						if(transfer.getTransferId().equals((long)choice)) {
							System.out.println("\nId: " + transfer.getTransferId()
											 + "\nFrom: Me"
											 + "\nTo: " + userService.getUsernameById(transfer.getAccountTo())
											 + "\nType: Send"
											 + "\nStatus: Approved"
											 + "\nAmount: $" + transfer.getAmount());
							shouldLoop = false;
						}
					}
				} else { // get and display details of that transfer linked to the transfer ID
					shouldLoop = false; // else exit  (they entered in 0)
				}
			} else { // else let them know they entered a wrong id, loop back
				System.out.println("\nOops! You entered an invalid ID number. Please try again.");
			}
		}
	}
	
	private void getRequestTransferDetails(Transfer[] transfers) {
		boolean shouldLoop = true;
		while(shouldLoop) {
			Integer choice = console.getUserInputInteger("\nPlease enter transfer ID to view details (or 0 to cancel)");
			List<Integer> idList = new ArrayList<Integer>();
			for(Transfer transfer : transfers) { // take transfers[] loop through each transfer
				int id = Integer.parseInt(transfer.getTransferId().toString());
				idList.add(id); // pull out the transfer ID and add to a new idArray of integers
			}
			if(idList.contains(choice) || choice == 0) {
				if(choice != 0) {
					for(Transfer transfer : transfers) {
						if(transfer.getTransferId().equals((long)choice)) {
							System.out.println("\nId: " + transfer.getTransferId()
											 + "\nFrom: " + userService.getUsernameById(transfer.getAccountTo())
											 + "\nTo: Me" 
											 + "\nType: Send"
											 + "\nStatus: Approved"
											 + "\nAmount: $" + transfer.getAmount());
							shouldLoop = false;
						}
					}
				} else { // get and display details of that transfer linked to the transfer ID
					shouldLoop = false; // else exit  (they entered in 0)
				}
			} else { // else let them know they entered a wrong id, loop back
				System.out.println("\nOops! You entered an invalid ID number. Please try again.");
			}
		}
	}

}
