package f21as.g5.cw.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;

import f21as.g5.cw.models.Item;
//import f21as.g5.cw.models.ItemMap;
import f21as.g5.cw.models.Order;
import f21as.g5.cw.models.OrderMap;
import f21as.g5.cw.models.OrderProcessor;
import f21as.g5.cw.models.Staff;

class OrderControllers {
	
	private static final OrderMap orderMap = new OrderMap();
	private static Order currOrder = new Order();
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	//==> Loads OrdersCSV file
	static void readCSVOrders(String fn) {
		
		String line = "";
		String splitBy = ",";

		try {
			
			BufferedReader br = new BufferedReader(new FileReader(fn));
			
			if ((line = br.readLine()) != null) {
				String[] itemOrderedList = line.split(splitBy);
				String custName = itemOrderedList[0];
				Item currItem = ItemControllers.getItem(itemOrderedList[1]);
				currOrder.setCustomerName(custName);
				currOrder.addToOrder(currItem);
				while ((line = br.readLine()) != null) {
					itemOrderedList = line.split(splitBy);
					if (itemOrderedList[0].equals(custName)) {
						currItem = ItemControllers.getItem(itemOrderedList[1]);
						currOrder.addToOrder(currItem);
					} else {
						closeOrder();
						custName = itemOrderedList[0];
						currItem = ItemControllers.getItem(itemOrderedList[1]);
						currOrder.setCustomerName(custName);
						currOrder.setOrderStatus("PENDING");
						currOrder.addToOrder(currItem);
					}
					
				}
				closeOrder();
				
			}
			br.close();
		} catch (NullPointerException | DateTimeException e) {
			
			System.err.println(e.getMessage());
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.out.println("There is some issue with OrdersCSV. Without it resolved there can be no customers in the simulation. Exiting Application.");
			System.exit(1);
			
		}
	}
	//==> gets number of orders
	static int getOrderSize() {
		return currOrder.getOrderSize();
	}
	//==> closes order and creates new instance of Order class
	static void closeOrder() {
		orderMap.addOrder(currOrder);
		currOrder = new Order();
	}
	
	//==> adds item to an order
	static void addToOrder(String name) {
		String iD = ItemControllers.getIDFromName(name);
		Item item = ItemControllers.getItem(iD);
		currOrder.addToOrder(item);
	}
	//==> removes an item from order
	static void removeFromOrder(String name) {
		String iD = ItemControllers.getIDFromName(name);
		Item item = ItemControllers.getItem(iD);
		currOrder.removeFromOrder(item);
	}
	//==> gets applicable offer name 
	static String getOfferName() {
//		return currOrder.getOfferName();
		boolean food  = false;
		boolean drink = false;
		boolean other = false;
		
		ArrayList<Item> items = currOrder.getAllItems();
		
		for (Item item : items) {
			String iD = item.getID();
			if (iD.substring(0, 4).equals("FOOD")) {
				food = true;
			} else if (iD.substring(0, 4).equals("BEVR")) {
				drink = true;
			} else if (iD.substring(0, 4).equals("OTHR")) {
				other = true;
			}
		}
		
		LocalDateTime currTime = currOrder.getOrderTime();
		LocalDateTime breakfastStart = Order.getStartBreakfastTime();
		LocalDateTime breakfastEnd = Order.getEndBreakfastTime();
		LocalDateTime lunchStart = Order.getStartLunchTime();
		LocalDateTime lunchEnd = Order.getEndLunchTime();
		
		
		boolean combo = (Order.getComboFlag() && food && drink && other);
		boolean breakfast = (Order.getBreakfastFlag() && currTime.isAfter(breakfastStart) && currTime.isBefore(breakfastEnd));
		boolean lunch = (Order.getLunchFlag() && currTime.isAfter(lunchStart) && currTime.isBefore(lunchEnd));
		
		if (breakfast) {
			if ((Order.getComboOfferValue() <= Order.getBreakfastOfferValue()) && combo) return "COMBO";
			else return "BREAKFAST";
		} else if (lunch) {
			if ((Order.getComboOfferValue() <= Order.getLunchOfferValue()) && combo) return "COMBO";
			else return "LUNCH";
		} else if (combo) return "COMBO";
		
		return "NONE";
		
	}
	//==> applies offer to an order if applicable
	private static double applyOffers() {
		String offer = getOfferName();
		if (offer.equals("COMBO")) return (currOrder.getTotalCost() * Order.getComboOfferValue());
		else if (offer.equals("BREAKFAST")) return currOrder.getTotalCost() * Order.getLunchOfferValue();
		else if(offer.equals("LUNCH")) return currOrder.getTotalCost() * Order.getBreakfastOfferValue();
		else return currOrder.getTotalCost();
	}
	//==> gets cost before offer is applied
	static double getCostNoDiscount() {
		return currOrder.getTotalCost();
	}
	//==> gets total cost after offer is applied
	static double getTotalCost() {
		return Double.parseDouble(df.format(applyOffers()));
	}
	//==> sets Combo offer flag
	static void setComboFlag(boolean flag) {
		Order.setComboFlag(flag);
	}
	//==> sets Breakfast offer flag
	static void setBreakfastFlag(boolean flag) {
		Order.setBreakfastFlag(flag);
	}
	//==> sets lunch offer flag
	static void setLunchFlag(boolean flag) {
		Order.setLunchFlag(flag);
	}
	//==> sets Combo offer value
	static void setComboValue(int oValue) {
		Order.setComboOfferValue(oValue);
	}
	//==> sets Breakfast offer value
	static void setBreakfastValue(int oValue) {
		Order.setBreakfastOfferValue(oValue);
	}
	//==> sets Lunch offer value
	static void setLunchValue(int oValue) {
		Order.setLunchOfferValue(oValue);
	}
	//==> sets breakfast offer time
	static void setBreakfastTime(int startHour, int startMinute, int endHour, int endMinute) {
		Order.setBreakfastTime(startHour, startMinute, endHour, endMinute);
	}
	//==> sets lunch offer time
	static void setLunchTime(int startHour, int startMinute, int endHour, int endMinute) {
		Order.setLunchTIme(startHour, startMinute, endHour, endMinute);
	}
	//==> gets number of orders in the system
	static int getTotalOrders() {
		return orderMap.getOrderMapSize();
	}
	
	static int getTotalPendingOrders( ) {
		return orderMap.getTotalPendingOrders();
		
	}
	//==> gets total income of the coffeeshop
	static double getTotalIncome() {
		return OrderMap.getTotalIncome();
	}
	

	//==> gets customer name
	static String getCustomerName() {
		return currOrder.getCustomerName();
	}
	//==> sets Customer name
	static void setCustomerName(String name) {
		currOrder.setCustomerName(name);
	}
	//==> set Order Status
	static void setOrderStatus(String status) {
		currOrder.setOrderStatus(status);
	}

	//==> get Order Status
	static String getOrderStatus() {
		return currOrder.getOrderStatus();
	}
	//==> get AllOrders
	static String getOrderList() {
		return orderMap.getAllOrders();
	}
	
	//==> gets order map back
	static OrderMap getOrderMap() {
		return orderMap;
	}
	
	//==> gets list of Staff
	static ArrayList<Staff> getStaffList () {
		return OrderProcessor.getStaffList();
	}
	//==> gets staff id
	static int getCurrentStaffId(int index) {
		return OrderProcessor.getCurrentStaffId(index);
	}
	//==> get customer name being handled by current staff
	static String getCustomerProcessedByStaff(int staffId) {
		return OrderProcessor.getCustomerProcessedByStaff(staffId);
	}
	
	static String getCurrentOrder(int staffId) {
		String orderItems = "";
		ArrayList<String> items;
		Order order = OrderProcessor.getCurrentOrder(staffId);
		//items =  order.getOrderItems();
		currOrder = order;
		int beforeQuantity = 1;
		int afterQuantity = 1;
		if(order != null ) {
			for(Item item : order.getAllItems()) {
				
				if(orderItems.indexOf(item.getName()) != -1) {
					afterQuantity++;
					orderItems = orderItems.replace(beforeQuantity +" x "+item.getName(), (afterQuantity) + " x "+item.getName());
					beforeQuantity++;
				} else {
					orderItems += "\n1"+" x "+item.getName();
				
				}
			}
			orderItems += "\n"+" Total: AED"+getTotalCost() + " (with AED "+  Double.parseDouble(df.format((getCostNoDiscount() - getTotalCost())))+" discount)";			
		}

		return orderItems;
	}
	
	static int getOrderQueueSize( ) {
		return OrderProcessor.getOrderQueueSize();	
	}
	static String getOrderQueueElemenets() {
		return OrderProcessor.getOrderQueueElemenets();
	}

}
