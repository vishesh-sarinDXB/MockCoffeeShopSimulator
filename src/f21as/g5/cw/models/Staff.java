package f21as.g5.cw.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;

public class Staff implements Runnable {
	private OrderProcessor orderProcessor;
	private int staffId;
	private String staffState;

	private int processingTime;
	private Order currentOrderInfo;
	private String customerName;
	private boolean isActive ;
	private int numOfOrderProcessed;
	public Staff(int staffId, OrderProcessor oProcessor) {
		this.staffId = staffId;
		this.orderProcessor = oProcessor;
		this.staffState = "WAITING";
		this.processingTime = oProcessor.getProcessingTime() * 1000;
		this.isActive = false;
		this.numOfOrderProcessed = 0;
		if(staffId == 1 || staffId == 2) {
			this.isActive = true;
		}
		OrderProcessor.getStaffList().add(this);
	}
	public boolean isActive(int staffId) {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		System.out.println("setting isActive for staff id: "+this.staffId + " to : "+isActive);
		this.isActive = isActive;
	}

	public Order getCurrentProcessingOrder() {
		return this.currentOrderInfo;
	}
	public String getCustomerName() {
		return this.customerName;
	}
	public int getStaffId() {
		return this.staffId;
	}
	public String getStaffState() {
		return this.staffState;
	}
	public void setStaffState(String staffState) {
		this.staffState = staffState;
	}
	@Override
	public void run () {
		OrderMap orderMap =  orderProcessor.getOrderList();
		//System.out.println(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())+ "=> staff id: "+staffId+" thread started");
		
		int i=1;			
		System.out.println("staff id: "+this.staffId+ " is active: "+this.isActive);
		while(i <= orderMap.getOrderMapSize()) {				
			if(!this.isActive || this.numOfOrderProcessed > 2) {
				try {
					Thread.sleep(orderProcessor.getProcessingTime());
					this.numOfOrderProcessed = 0;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(this.isActive) {
				Order order = orderProcessor.getNextOrderInTheQueue();
				if(order != null) {
					setStaffState("PROCESSING");
					this.numOfOrderProcessed += 1;
					this.currentOrderInfo = order;
					this.customerName = order.getCustomerName();
					orderProcessor.updateObservers();
							
					System.out.println(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())+ " => staff id: "+staffId+" starts processing order: "+this.customerName);
					
					try {
						//==> update order to processing
						orderProcessor.updateObservers();
						Thread.sleep(orderProcessor.getProcessingTime()/2);

						// ==> update order to completed
						orderProcessor.updateOrderStatus(order.getID(), "COMPLETED");
						orderProcessor.updateObservers();
						Thread.sleep(orderProcessor.getProcessingTime()/2);
						setStaffState("COMPLETED");

						try {
							orderProcessor.processOrder();
							orderProcessor.updateObservers();
						} catch (InterruptedException e2) {
							e2.printStackTrace();
						}
						i++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}				
				} 	
			} else {
				try {
					Thread.sleep(orderProcessor.getProcessingTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
