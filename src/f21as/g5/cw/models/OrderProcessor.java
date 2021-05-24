package f21as.g5.cw.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Queue;

import f21as.g5.cw.controllers.*;
import java.util.Map.Entry;

public class OrderProcessor extends Observable implements Runnable {
	
	private OrderMap orderMap;

	private static Queue<Order> orderQueue;
	private int orderQueueSize =10;
	// Number of servers(consumers) required
	private int numberOfServers = 5;
	private boolean isSimulatorStarted = false;
	// ==> number of milliseconds order stays in the queue before its consumed
	private int waitingTime = 4000;
	//==> number of milliseconds server processes an order
	private int processingTime = 6000;
	
	// to store instances of Staff Class
	private static ArrayList<Staff> staffList = new ArrayList<Staff>();

	
	//==> constructor method
	public OrderProcessor() {
		
		//==> getting orderMap that contains all orders loaded from file
		orderMap = PublicController.getAllOrderMap();
		
		//==> initializing orderQueue
		orderQueue = new LinkedList<Order>();				
	}
	public boolean isSimulatorStarted() {
		return this.isSimulatorStarted;
	}
	//==> sets number of servers(consumers) 
	public int getNumberOfServers() {
		return numberOfServers;
	}

	//==> gets number of servers(consumers) 
	public void setNumberOfServers(int numberOfServers) {
		this.numberOfServers = numberOfServers;
	}
	
	// ==> get processing time in seconds
	public int getProcessingTime() {
		return processingTime ;
	}
	//==> set processing time in milliseconds
	public void setProcessingTime(int processingTime) {
		System.out.println("setting processing time by: "+processingTime+" second");
		this.processingTime = processingTime * 1000;
	}
	
	//==> get waiting time
	public int getWaitingTime() {
		return waitingTime;
	}
	
	//==> set waiting time
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime * 1000;
	}

	//==> this is main run method for main thread
	@Override
	public void run () {
		//loading order file
		System.out.println("Starting Order Processor....");
		try {
			//loop through orders and add order to order queue			
			int i=1;			
			while(i <= orderMap.getOrderMapSize()) {				
				updateObservers();
				if(orderMap.getOrder(i).getOrderStatus() == "PENDING") {
					addOrderToOrderQueue(i);
				}
				Thread.sleep(this.waitingTime);
				i++;
			}
			isSimulatorStarted = true;
			//updateObservers();

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	// add order to order queue
	public synchronized void addOrderToOrderQueue(int key) throws InterruptedException {
		while(orderQueue.size() == orderQueueSize) {
			wait();
		}
		Order order = getNextOrder(key);
		orderQueue.add(order);
		notify();
	}
	//==> processes order and then removes it from order queue
	public synchronized void processOrder() throws InterruptedException {
		while(orderQueue.isEmpty()) {
			wait();
		}
		synchronized(orderQueue) {
			orderQueue.poll();
		};
		notify();
	}
	//=> will return orderMap
	public OrderMap getOrderList () {
		return orderMap;
	}
	//==> returns order queue to observers
	public synchronized Queue<Order> getOrderQueue () {
		return orderQueue;
	}
	//==> will return next pending order to the staff thread
	public synchronized Order getNextOrder(int key) {
		return orderMap.getOrder(key);
	}
	public synchronized Order getNextOrderInTheQueue() {
		while(orderQueue.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		synchronized(orderQueue) {
			for(Order order : orderQueue) {
				if(order != null) {
					if(order.getOrderStatus() == "PENDING") {
						order.setOrderStatus("PROCESSING");
						return  order;			
					}
				}
			}
			orderQueue.notifyAll();
		}
		return null;
	}
	//==> will update status of the order (call initiated by staff thread)
	public synchronized void updateOrderStatus(int orderId, String status) {
		Order order = orderMap.getOrder(orderId);
		order.setOrderStatus(status);
	}
	//==> will update observers on current change of order status
	public void updateObservers() {
		setChanged();
		notifyObservers();
		clearChanged();		
	}
	//==> will return list of staff (call initiated from view)
	public Staff getStaff(int staffId) {
		return staffList.get(staffId-1);
	}
	
	//==> will return order currently processed by staff thread using staffId
	public static Order getCurrentOrder(int staffId) {
		return staffList.get(staffId-1).getCurrentProcessingOrder();
	}
	//==> will return customer name of the order currently processed by staff thread using staffId
	public static String getCustomerProcessedByStaff(int staffId) {
		if(staffList.size() == 0) {
			return null;
		}
		try {
			return staffList.get(staffId-1).getCustomerName();
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	//==> will return staffId using its index in staffList
	public static int getCurrentStaffId(int index) {
		return staffList.get(index-1).getStaffId();
	}
	public void isStaffActive(int index, boolean isActive) {
		//if((index-1) < staffList.size()) {
			staffList.get(index-1).setIsActive(isActive);
		//}
	}
	public String getStaffState (int index) {
		if(staffList.size() == 0) {
			return null;
		}
		try {
			return staffList.get(index-1).getStaffState();
		} catch(IndexOutOfBoundsException e) {
			return null;
		}

	}
	//==> will return staffList (call initiated by view)
	public static ArrayList<Staff> getStaffList() {
		return staffList;
	}
	
	public static int getOrderQueueSize() {
		return orderQueue.size();
	}
	
	public synchronized static String getOrderQueueElemenets () {
		String allOrders = String.format("%-5s", "CustomerName")+"\tNumOfItems   OrderStatus";
		synchronized(orderQueue) {
			for(Order order : orderQueue) {
				allOrders += "\n"+ String.format("%-5s", order.getCustomerName()) + "\t\t"+order.getOrderSize()+" items\t\t"+order.getOrderStatus();
			}
			orderQueue.notifyAll();
		}
		return allOrders;
	}

}
