package f21as.g5.cw.models;

import f21as.g5.cw.exceptions.InvalidIDException;

public class Item  {
	
	private String iD;
	private String name;
	private String type;
	private double cost;
	private int amountSold;
	
	public Item(String iD, String name, String type, double cost) throws InvalidIDException {
		
		if(!iD.equals(null)) {
			//!iD.substring(0, 4).equals((type.isEmpty() || type.isBlank())?type:type.toUpperCase())
			boolean isValidPrefix = (iD.substring(0, 4).equals("FOOD") || iD.substring(0, 4).equals("BEVR") || iD.substring(0, 4).equals("OTHR"));
			if (iD.isEmpty() || iD.equals("") || Character.isDigit(iD.charAt(0)) || iD.length() > 7 || !isValidPrefix) {
				throw new InvalidIDException(iD); 
			}
		}
		this.iD = iD;
		this.name = name;
		this.type = type;
		this.cost = cost;
		this.amountSold = 0;
		
	}
	
	void updateAmountSold() {
		amountSold += 1;
	}
	
	public double getCost() {
		return cost;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String getID() {	
		return iD;
	}
	
	public int getAmountSold() {
		return amountSold;
	}
	
	public void setID(String iD) {	
		this.iD = iD;
	}
}
