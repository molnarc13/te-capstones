package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class VendingMachine {
	
	private BigDecimal balance = new BigDecimal("0.00");
	private Map<String, Slot> inventory;
	private String fullAudit = "";
	private BigDecimal totalSales = new BigDecimal("0.00");
	
	public VendingMachine() throws IOException {
		inventory = new TreeMap<String, Slot>();
		loadInventory();
	}

	public void loadInventory() throws FileNotFoundException {
		File inventoryFile = new File("vendingmachine.csv");
		Scanner fileReader = new Scanner(inventoryFile);
		
		while(fileReader.hasNextLine()) {
			String currentLine = fileReader.nextLine();
			String[] inventoryItemArray = currentLine.split("\\|");
			VendingMachineItem item1 = new VendingMachineItem(inventoryItemArray[3], inventoryItemArray[1]);
			Slot slot1 = new Slot(item1, 5, BigDecimal.valueOf(Double.parseDouble((inventoryItemArray[2]))));
			inventory.put(inventoryItemArray[0], slot1);
		}
		fileReader.close();
	}
	
	public void feedMoney() throws FileNotFoundException {
		@SuppressWarnings("resource")
		Scanner userInput = new Scanner(System.in);
		System.out.print("\n" + "What bill would you like to add?" + "\n" + "(Only whole bills: e.g. 1, 2, 5, 10...) >>> ");
		BigDecimal moneyIn = userInput.nextBigDecimal();
		BigDecimal setDecimal = new BigDecimal("0.00");
		moneyIn = moneyIn.add(setDecimal);
		balance = balance.add(moneyIn);
		addAuditEntry("FEED MONEY", moneyIn);
	}

	
	public void selectProduct() {
		@SuppressWarnings("resource")
		Scanner userInput = new Scanner(System.in);
		Set<String> theKeys = inventory.keySet();
		System.out.println(); 
		for(String aKey : theKeys) {
		System.out.println(aKey + " - " + inventory.get(aKey));									
		}
		
		System.out.print("\n" + "Pick a product and enter in an item code (case sensitive) >>> ");	
		String userItem = userInput.nextLine();
		if (inventory.containsKey(userItem)) {
			Slot userSlot = inventory.get(userItem);
			if (userSlot.getNumberOfItems() == 0) {										
				System.out.println("SOLD OUT");
			} else if (userSlot.getItemPrice().compareTo(balance) > 0) {
				System.out.println("INSUFFICIENT FUNDS");
			} else {
				System.out.println("\n" + userSlot.getAnItem().getItemName());
				System.out.println("Cost: $" + userSlot.getItemPrice());
				balance = balance.subtract(userSlot.getItemPrice());
				totalSales = totalSales.add(userSlot.getItemPrice());
				userSlot.setNumberOfItems(userSlot.getNumberOfItems() - 1);
				System.out.println("Money remaining: $" + balance);
				if (userSlot.getAnItem().getItemType().contains("Chip")) {
					System.out.println("Crunch crunch, Yum!");
				} else if (userSlot.getAnItem().getItemType().contains("Candy")) {
					System.out.println("Munch munch, Yum!");
				} else if (userSlot.getAnItem().getItemType().contains("Drink")) {
					System.out.println("Glug glug, Yum!");
				} else {
					System.out.println("Chew chew, Yum!");
				}
				addAuditEntry(userSlot.getAnItem().getItemName() + " " + userItem, balance.add(userSlot.getItemPrice()));
			}
		} else {
			System.out.println("\n" + "Oops! You entered an invalid item code. Try again!");
		}
	}
	
	public void finishTransaction() {
		double currentBalance = balance.doubleValue();
		int quarter = 25;
		int dime = 10;
		int nickel = 5;
		if (currentBalance != 0) {
			int numOfQuarters = (int) ((currentBalance * 100) / quarter);
			int remainder = (int) ((currentBalance * 100) % quarter);
			int numOfDimes = remainder / dime;
			remainder = remainder % dime;
			int numOfNickels = remainder / nickel;
			System.out.println("Money to return: " + numOfQuarters + " quarters, " + numOfDimes + " dimes, " + numOfNickels + " nickels.");
			balance = balance.subtract(balance);
			System.out.println("Remaining balance: " + balance);
			addAuditEntry("GIVE CHANGE", balance.add(BigDecimal.valueOf(currentBalance)));
		} else {
			System.out.println("No money to return.");
		}
	}
	
	public void createAudit() throws FileNotFoundException {
		PrintWriter auditWriter = new PrintWriter("Log.txt");
		auditWriter.write(fullAudit);
		auditWriter.close();
	}
	
	@SuppressWarnings("deprecation")
	private void addAuditEntry(String action, BigDecimal actionAmount) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String amOrPm = "AM";
		if (calendar.getTime().getHours() > 11) {
			amOrPm = "PM";
		}
		String currentTime = formatter.format(calendar.getTime()) + " " + amOrPm;
		fullAudit += currentTime + " " + action + ": $" + actionAmount.toString() + " $" + balance.toString() + "\n";
	}
	
	@SuppressWarnings("deprecation")
	public void generateSalesReport() throws IOException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss");
		String amOrPm = "AM";
		if (calendar.getTime().getHours() > 11) {
			amOrPm = "PM";
		}
		String currentTime = formatter.format(calendar.getTime()) + "_" + amOrPm;

		String salesReportFilePath = "Sales Report_" + currentTime + ".txt";
		File salesReportFile = new File(salesReportFilePath);
		salesReportFile.createNewFile();

		PrintWriter salesReportWriter = new PrintWriter(salesReportFilePath);

		Set<String> theKeys = inventory.keySet();
		for(String aKey : theKeys) {
			String itemName = inventory.get(aKey).getAnItem().getItemName();
			int itemsSold = (5 - inventory.get(aKey).getNumberOfItems());
			salesReportWriter.println(itemName + "|" + itemsSold);
		}
		
		salesReportWriter.print("\n" + "Total Sales: $" + totalSales);
		salesReportWriter.close();
	}

	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the inventory
	 */
	public Map<String, Slot> getInventory() {
		return inventory;
	}
	
	
	
}
