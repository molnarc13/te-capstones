package com.techelevator;

import java.math.BigDecimal;

public class Slot {

	private int numberOfItems;
	private BigDecimal itemPrice;
	private VendingMachineItem anItem;
	
	public Slot(VendingMachineItem anItem, int numberOfItems, BigDecimal itemPrice) {
		this.anItem = anItem;
		this.itemPrice = itemPrice;
		this.numberOfItems = numberOfItems;
	}

	/**
	 * @return the numberOfItems
	 */
	public int getNumberOfItems() {
		return numberOfItems;
	}

	/**
	 * @return the itemPrice
	 */
	public BigDecimal getItemPrice() {
		return itemPrice;
	}
	
	/**
	 * @return the anItem
	 */
	public VendingMachineItem getAnItem() {
		return anItem;
	}

	/**
	 * @param numberOfItems the numberOfItems to set
	 */
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	@Override
	public String toString() {
		return  anItem + " - $" + itemPrice + " - " + numberOfItems + " remaining.";
	}
	
}
