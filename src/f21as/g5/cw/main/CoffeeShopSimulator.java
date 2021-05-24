package f21as.g5.cw.main;
import java.awt.EventQueue;
import java.io.BufferedReader;
//import java.util.*;

import f21as.g5.cw.views.AdminGUI;
import f21as.g5.cw.views.Customer;
import f21as.g5.cw.views.OrderProcessorSimulator;
import f21as.g5.cw.exceptions.DuplicateIDException;
import f21as.g5.cw.exceptions.InvalidIDException;
import f21as.g5.cw.controllers.PublicController;
import f21as.g5.cw.models.Item;
import f21as.g5.cw.models.ItemMap;
import f21as.g5.cw.models.Order;
import f21as.g5.cw.models.OrderMap;
import f21as.g5.cw.models.OrderProcessor;

//import java.io.FileNotFoundException;
import java.io.FileReader;  
import java.io.IOException;
import java.time.DateTimeException;  

public class CoffeeShopSimulator {

	public static void main(String[] args) {
				
		PublicController.startThreads("ItemsCSV.csv", "OrdersCSV.csv");
		
		
		
		
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Customer gui = new Customer();
					gui.setVisible(true);

					AdminGUI gui2 = new AdminGUI(); //(entries);
					gui2.setVisible(true);	
					
					OrderProcessorSimulator gui3 = new OrderProcessorSimulator(oProcessor);
					gui3.setVisible(true);


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
	}
}
