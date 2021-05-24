package f21as.g5.cw.models;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
//import java.time.format.DateTimeParseException;  

public class Order {
	
	private static int numOrders = 0;
	private int orderID;
	private String orderName;
	private String orderStatus;
	private ArrayList<Item> items = new ArrayList<Item>();
	private LocalDateTime timeStamp;
	private double totalCost;
//	private double totalCostAfterDiscount;
	
	private static boolean comboFlag = true;
	private static boolean lunchFlag = true;
	private static boolean breakfastFlag = true;

	private static double comboOfferValue = 0.80;
	private static double lunchOfferValue = 0.90;
	private static double breakfastOfferValue = 0.95;

	private static LocalDateTime startBreakfastTime = LocalDate.now().atTime(6, 0);
	private static LocalDateTime endBreakfastTime = LocalDate.now().atTime(10, 0);
	private static LocalDateTime startLunchTime = LocalDate.now().atTime(12, 0);
	private static LocalDateTime endLunchTime = LocalDate.now().atTime(14, 0);
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	public static void setBreakfastTime(int startHour, int startMinute, int endHour, int endMinute) throws DateTimeException{
		try {
			LocalDateTime startBreakfastTimeCheck = LocalDate.now().atTime(startHour, startMinute);
			LocalDateTime endBreakfastTimeCheck = LocalDate.now().atTime(endHour, endMinute);
			
			if (endBreakfastTimeCheck.isAfter(startLunchTime)) {
				throw new DateTimeException("Breakfast offer can't end after lunch offer has begun");
			} else {
				startBreakfastTime = startBreakfastTimeCheck;
				endBreakfastTime = endBreakfastTimeCheck;
			}
			
		} catch(DateTimeException e) {
			throw new DateTimeException("Invalid hour/minute");
		}
	}
	
	public static LocalDateTime getStartBreakfastTime() {
		return startBreakfastTime;
	}
	public static LocalDateTime getEndBreakfastTime() {
		return endBreakfastTime;
	}

	public static void setLunchTIme(int startHour, int startMinute, int endHour, int endMinute) throws DateTimeException{
		try {
			LocalDateTime startLunchTimeCheck = LocalDate.now().atTime(startHour, startMinute);
			LocalDateTime endLunchTimeCheck = LocalDate.now().atTime(endHour, endMinute);
			
			if (startLunchTimeCheck.isBefore(endBreakfastTime)) {
				throw new DateTimeException("Lunch offer can't start before breakfast offer has ended");
			} else {
				startLunchTime = startLunchTimeCheck;
				endLunchTime = endLunchTimeCheck;
			}
			
			
		} catch(DateTimeException e) {
			throw new DateTimeException(e.getMessage());
		}
	}
	public static LocalDateTime getStartLunchTime() {
		return startLunchTime;
	}
	
	public static LocalDateTime getEndLunchTime() {
		return endLunchTime;
	}
	
	public LocalDateTime getOrderTime() {
		return this.timeStamp;
	}

	public static void setComboFlag(boolean flag) {
		comboFlag = flag;
	}
	public static boolean getComboFlag() {
		return comboFlag;
	}
	
	public static void setLunchFlag(boolean flag) {
		lunchFlag = flag;
	}
	public static boolean getLunchFlag() {
		return lunchFlag;
	}
	
	public static void setBreakfastFlag(boolean flag) {
		breakfastFlag = flag;
	}
	public static boolean getBreakfastFlag() {
		return breakfastFlag;
	}
	/**
	 * sets Combo Offer Value
	 * @param oValue
	 */
	public static void setComboOfferValue(int oValue) {
		
		comboOfferValue =  ((100.0 - oValue) / 100.0);
	}
	public static double getComboOfferValue() {
		return comboOfferValue;
	}
	/**
	 * sets Lunch Offer Value
	 * @param oValue
	 */
	public static void setLunchOfferValue(int oValue) {
		lunchOfferValue = ((100.0 - oValue) / 100.0);
	}
	public static double getLunchOfferValue() {
		return lunchOfferValue;
	}
	/**
	 * Sets Breakfast offer value
	 * @param oValue
	 */
	public static void setBreakfastOfferValue(int oValue) {
		breakfastOfferValue = ((100.0 - oValue) / 100.0);
	}
	public static double getBreakfastOfferValue() {
		return breakfastOfferValue;
	}
	
	public ArrayList<Item> getAllItems() {
		return this.items;
	}
	
	public Order(/*String orderName*/) {
//		this.orderName = orderName;
		numOrders += 1;
		orderID = numOrders;
		timeStamp = LocalDateTime.now();
		totalCost = 0;
	}
	
	public void setCustomerName(String name) {
		this.orderName = name;
	}
	
	public void setOrderStatus(String status) {
		this.orderStatus = status;
	}
	public String getOrderStatus() {
		return this.orderStatus;
	}

	public String getCustomerName() {
		return this.orderName;
	}
	
	public double getTotalCost() {
		return Double.parseDouble(df.format(totalCost));
	}
	
//	public double getCostNoDiscount() {
//		return Double.parseDouble(df.format(totalCost));
//	}
	
	public void addToOrder(Item item) {
		totalCost += item.getCost();
		item.updateAmountSold();
		items.add(item);
	}
	
	public int getOrderSize() {
		return items.size();
	}
	
	public void removeFromOrder(Item item) {
		if (items.indexOf(item) != -1) {
			totalCost -= item.getCost();
		}
		
		items.remove(items.indexOf(item));
	}
	
//	public ArrayList<String[]> getBill() {
//		ArrayList<String[]> bill = new ArrayList<String[]>();
//		
//		for(Item item : items) {
//			String[] nameCost = new String[2];
//			nameCost[0] = item.getName();
//			nameCost[1] = Double.toString(item.getCost());
//			bill.add(nameCost);
//		}
//
//		this.timeStamp = LocalDateTime.now();
//		
//		return bill;
//	}
	
	public LocalDateTime getTime() {
		return this.timeStamp;
	}
	
	public int getID() {
		return this.orderID;
	}
	
//	public String getOrderItems () {
//		String orderItems = "";
//		int quantity = 1;
//		for(Item item : items) {
//			if(orderItems.indexOf(item.getName()) != -1) {
//				orderItems = orderItems.replace("1 x "+item.getName(), (quantity+1) + " x "+item.getName());
//			} else {
//				orderItems += "\n"+quantity+" x "+item.getName();
//			}
//		}
//		orderItems += "\n Total: AED"+getTotalCost() + " (with )";
//		return orderItems;
//	}
}
