package f21as.g5.cw.controllers;

import java.awt.EventQueue;
import java.util.*;

import f21as.g5.cw.models.OrderMap;
//import java.util.HashMap;
import f21as.g5.cw.models.OrderProcessor;
import f21as.g5.cw.models.Staff;
import f21as.g5.cw.views.AdminGUI;
import f21as.g5.cw.views.Customer;
import f21as.g5.cw.views.OrderProcessorSimulator;



//import f21as.g5.cw.models.Item;

public class PublicController {
	
//	private static final ItemMap itemMap;
	
	public static void createItemMap(String fn) {
		ItemControllers.readCSVItems(fn);
	}
	
	public static void createOrderMap(String fn) {
		OrderControllers.readCSVOrders(fn);
	}
	
	public static ArrayList<String> getMenu() {
		return ItemControllers.getMenu();
	}
	
	public static double getTotalCost() {
		return OrderControllers.getTotalCost();
	}
	
	public static double getCostNoDiscount() {
		return OrderControllers.getCostNoDiscount();
	}
	
	public static void addToOrder(String name) {
		OrderControllers.addToOrder(name);
	}
	
	public static void removeFromOrder(String name) {
		OrderControllers.removeFromOrder(name);
	}
	
	public static void closeOrder() {
		OrderControllers.closeOrder();
	}
	
	public static String getOfferName() {
		return OrderControllers.getOfferName();	
	}
	
	public static void setComboFlag(boolean flag) {
		OrderControllers.setComboFlag(flag);
	}
	
	public static void setBreakfastFlag(boolean flag) {
		OrderControllers.setBreakfastFlag(flag);
	}
	
	public static void setLunchFlag(boolean flag) {
		OrderControllers.setLunchFlag(flag);
	}
	
	public static void setComboValue(int oValue) {
		OrderControllers.setComboValue(oValue);
	}
	
	public static void setBreakfastValue(int oValue) {
		OrderControllers.setBreakfastValue(oValue);
	}
	
	public static void setLunchValue(int oValue) {
		OrderControllers.setLunchValue(oValue);
	}
	
	public static void setBreakfastTime(int startHour, int startMinute, int endHour, int endMinute) {
		OrderControllers.setBreakfastTime(startHour, startMinute, endHour, endMinute);
	}
	
	public static void setLunchTime(int startHour, int startMinute, int endHour, int endMinute) {
		OrderControllers.setLunchTime(startHour, startMinute, endHour, endMinute);
	}
	
	public static int getTotalOrders() {
		return OrderControllers.getTotalOrders();
	}
	
	public static int getTotalPendingOrders() {
		return OrderControllers.getTotalPendingOrders();
		
	}
	
	public static double getTotalIncome() {
		return OrderControllers.getTotalIncome();
	}
	
	public static String getMenuAdmin() {
		return ItemControllers.getMenuAdmin();
	}
	
	public static String getItemsSoldAdmin() {
		return ItemControllers.getItemsSoldAdmin();
	}
	
	public static int getOrderSize() {
		return OrderControllers.getOrderSize();
	}
	
	public static void setCustomerName(String name) {
		OrderControllers.setCustomerName(name);
	}
	
	public static String getCustomerName() {
		return OrderControllers.getCustomerName();
	}
	
	//==> gets String containing list of orders
	public static String getAvailableOrders() {
		return OrderControllers.getOrderList();
	}
	
	//==> gets all orders in orderMap
	public static OrderMap getAllOrderMap() {
		return OrderControllers.getOrderMap();
	}
	
	//==>gets list of staff available
	
	public static ArrayList getStaffList() {
		return OrderControllers.getStaffList();
	}
	public static int getCurrentStaffId(int index) {
		return OrderControllers.getCurrentStaffId(index);
	}
	public static String getCustomerProcessedByStaff(int staffId) {
		return OrderControllers.getCustomerProcessedByStaff(staffId);
	}
	public static String getCurrentOrder(int staffId) {
		return OrderControllers.getCurrentOrder(staffId);
	}
	public static int getOrderQueueSize () {
		return OrderControllers.getOrderQueueSize();
	}
	
	public static String getOrderQueueElemenets() {
		return OrderControllers.getOrderQueueElemenets();
	}
	
	
	private static OrderProcessor oProcessor = new OrderProcessor();
	public static void startThreads(String itemFileName, String orderFileName) {
		
		PublicController.createItemMap(itemFileName);
		PublicController.createOrderMap(orderFileName);

		//==> starting producer
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					/*
					Customer gui = new Customer();
					gui.setVisible(true);
					OrderProcessor oProcessor = new OrderProcessor();
					Thread thread = new Thread (oProcessor);
					
					thread.start();

//					Customer gui = new Customer();
//					gui.setVisible(true);

					AdminGUI gui2 = new AdminGUI(); //(entries);
					gui2.setVisible(true);	
					*/
					AdminGUI gui2 = new AdminGUI(); //(entries);
					gui2.setVisible(true);
					
					OrderProcessorSimulator gui3 = new OrderProcessorSimulator(oProcessor);
					gui3.setVisible(true);


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//==> fire up the simulator
		//create staff instances and store it in stafflist; 		
		for(int staffId = 1; staffId < oProcessor.getNumberOfServers(); staffId++) {
			new Staff(staffId, oProcessor);
		}

	}
	
	public static void startSimulator() {
		Thread thread = new Thread (oProcessor);
		
		thread.start();
		Thread [] staffThreads;
		
		ArrayList<Staff> staffList = OrderControllers.getStaffList();
		//==> initializing number of staff threads required
		staffThreads = new Thread [oProcessor.getNumberOfServers()];
		
		//==> starting all staff threads (consumers) to start processing orders 
		//==> to avoid startSimulator button to freeze due to below recursive loop, we have added a new thread to do the looping
		new Thread () {
			public void run () {
				for(int staffId = 1; staffId < oProcessor.getNumberOfServers(); staffId++) {
					System.out.println("staffList size: "+staffList.size());
					staffThreads[staffId] = new Thread(staffList.get(staffId-1));
					try {
						//Thread.sleep(oProcessor.getProcessingTime());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					staffThreads[staffId].start();
					try {
					} catch(Exception e) {
						System.out.println(e.getStackTrace());
					}
				}
				
			}
		}.start();
	}
}
