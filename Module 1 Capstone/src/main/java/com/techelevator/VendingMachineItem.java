package com.techelevator;

public class VendingMachineItem {

	private String itemType;
	private String itemName;
	
	public VendingMachineItem(String itemType, String itemName) {
		this.itemType = itemType;
		this.itemName = itemName;
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return itemName + " (" + itemType + ")";
	}
	
}
