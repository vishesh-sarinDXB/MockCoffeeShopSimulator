package f21as.g5.cw.models;
import java.text.DecimalFormat;
import java.util.*;
//i made a comment to show automatic closing of issues
public class OrderMap {
	
	private HashMap<Integer, Order> orderMap = new HashMap<Integer, Order>();
	private static double totalIncome;
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	private String [] rondomNames = {"Batatia","Abrar", "Hamdan", "Ahmed", "Vishis", "Fatima", "Aisha", "Salma", "Yusuf"};
	
	public OrderMap() {
		
	}
	
	public OrderMap(HashMap<Integer, Order> orderMap) {
		this.orderMap = orderMap;
	}
	
	public void addOrder(Order order) {
		totalIncome += order.getTotalCost();
		if(order.getCustomerName() == null) {
			order.setCustomerName(rondomNames[new Random().nextInt(7)]);
		}
		order.setOrderStatus("PENDING");
		this.orderMap.put(order.getID(), order);
	}
	
	public static double getTotalIncome() {
		return Double.parseDouble(df.format(totalIncome));
	}
	
	public Order getOrder(int iD) {
		return this.orderMap.get(iD);
	}
	public int getOrderMapSize() {//k
		return this.orderMap.size();
	}
	
	public Set<Integer>  getOrderMapIDs() {//k
		return this.orderMap.keySet();
	}
	
	
	public String getAllOrders () {
		String allOrders = "CustomerName  NumOfItems  OrderStatus";
		for(Map.Entry<Integer, Order> orders: orderMap.entrySet()) {			
			if(orders.getValue().getOrderStatus() != "COMPLETED") {
				allOrders += "\n"+ String.format("%1$-10s", orders.getValue().getCustomerName()) + "\t\t"+orders.getValue().getOrderSize()+" items\t\t"+orders.getValue().getOrderStatus();
			}
		}
	
		return allOrders;
	}
	
	public int getTotalPendingOrders() {
		int n= this.orderMap.size();
	
		for(Map.Entry<Integer, Order> orders: orderMap.entrySet()) {			
			String orderStatus = orders.getValue().getOrderStatus();
			if(orderStatus != "PENDING" && orderStatus != "PROCESSING") {
				n--;				
			}
		}		
		return n;
	}
}
