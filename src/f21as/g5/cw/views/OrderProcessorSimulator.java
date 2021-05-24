package f21as.g5.cw.views;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import java.awt.TextArea;
import javax.swing.JCheckBox;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Label;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import f21as.g5.cw.models.OrderMap;
import f21as.g5.cw.models.OrderProcessor;
import f21as.g5.cw.models.Staff;
import f21as.g5.cw.controllers.*;
public class OrderProcessorSimulator extends JFrame implements Observer {
	OrderProcessor oProcessor = null;
	ArrayList staffList;
//jpanel creation 
	private JPanel contentPane;
	private ButtonGroup orderRadioGroup, QueueRadioGroup;
	TextArea textArea = new TextArea();
	TextArea textArea_1 = new TextArea();
	String waiter1=""; String waiter2=""; String waiter3="";String waiter4="";
	TextArea textArea_2 = new TextArea();
	TextArea textArea_3 = new TextArea();
	TextArea textArea_4 = new TextArea();
	JCheckBox chckbxWaiter_3 = new JCheckBox("Server 3");
	JCheckBox chckbxWaiter_4 = new JCheckBox("Server 4");
	JRadioButton Queue1 = new JRadioButton("2 second");
	JRadioButton OrderQueue1 = new JRadioButton("1 second");
	JRadioButton Queue2 = new JRadioButton("4 seconds");
	JRadioButton OrderQueue2 = new JRadioButton("2 seconds");
	JRadioButton Queue3 = new JRadioButton("6 seconds");
	JRadioButton OrderQueue3 = new JRadioButton("4 seconds");
	JButton btnLog;
	
		/**
		 * Create the frame.
		 */
	
	public OrderProcessorSimulator(OrderProcessor oProcessor) {		
		this.oProcessor = oProcessor;
		oProcessor.addObserver(this);
		
		staffList = PublicController.getStaffList();
		
		//being of the desing by omar and khalid
		setTitle("OrderProcessorSimulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 872, 511);
		setLocation(200, 00);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblQueueTimeline = new JLabel("Waiter Processing Time");
		lblQueueTimeline.setBounds(267, 81, 142, 14);
		contentPane.add(lblQueueTimeline);
		
		JLabel lblOrderqueueTimeline = new JLabel("Order Queue Time");
		lblOrderqueueTimeline.setBounds(10, 81, 109, 14);
		contentPane.add(lblOrderqueueTimeline);
		//Queue1.setSelected(true);
		
		
		
		Queue1.setBounds(277, 100, 109, 23);
		contentPane.add(Queue1);
		//Queue1 = new JRadioButton("1", true);
		
		
		Queue1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				//insert the update method to change the queue to set the value		
				Logger.updateLog("Set Waiter Processing Time to 2 seconds");
				oProcessor.setProcessingTime(2);
			}
		});
		
		
		Queue2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				//insert the update method to change the queue to set the value		
				Logger.updateLog("Set Waiter Processing Time to 4 seconds");
				oProcessor.setProcessingTime(4);
			}
		});
		Queue3.setSelected(true);
		
		Queue3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				//insert the update method to change the queue to set the value		
				Logger.updateLog("Set Waiter Processing Time to 6 seconds");
				oProcessor.setProcessingTime(6);
			}
		});
		
		OrderQueue1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				//insert the update method to change the queue to set the value	
				Logger.updateLog("Set Order Queue Time to 1 second");
				oProcessor.setWaitingTime(1);
			}
		});
		
		
		OrderQueue2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				//insert the update method to change the queue to set the value		
				Logger.updateLog("Set Order Queue Time to 2 seconds");
				oProcessor.setWaitingTime(2);
			}
		});
		OrderQueue3.setSelected(true);
		
		OrderQueue3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				//insert the update method to change the queue to set the value		
				Logger.updateLog("Set Order Queue Time to 4 seconds");
				oProcessor.setWaitingTime(4);
			}
		});
        
       
        
		
		Queue2.setBounds(277, 122, 109, 23);
		contentPane.add(Queue2);
		
		Queue3.setBounds(277, 146, 109, 23);
		contentPane.add(Queue3);
		
		QueueRadioGroup = new ButtonGroup();
		QueueRadioGroup.add(Queue1);
	    QueueRadioGroup.add(Queue2);
	    QueueRadioGroup.add(Queue3);
		
	    orderRadioGroup = new ButtonGroup();
	    orderRadioGroup.add(OrderQueue1);
	    orderRadioGroup.add(OrderQueue2);
	    orderRadioGroup.add(OrderQueue3);
	    
		OrderQueue3.setBounds(10, 146, 109, 23);
		contentPane.add(OrderQueue3);
		
		
		OrderQueue2.setBounds(10, 122, 109, 23);
		contentPane.add(OrderQueue2);
		
		OrderQueue1.setBounds(10, 100, 109, 23);
		contentPane.add(OrderQueue1);
		
		
		textArea.setEditable(false);
		textArea.setBounds(10, 217, 345, 252);
		contentPane.add(textArea);
		
		JLabel lblWaitersAllocated = new JLabel("Waiters Console");
		lblWaitersAllocated.setBounds(609, 10, 142, 14);
		contentPane.add(lblWaitersAllocated);
		
		
		chckbxWaiter_3.setBounds(396, 284, 77, 23);
		contentPane.add(chckbxWaiter_3);
		

		chckbxWaiter_4.setBounds(396, 393, 77, 23);
		contentPane.add(chckbxWaiter_4);
		
		// added this be
		chckbxWaiter_3.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				oProcessor.isStaffActive(3, chckbxWaiter_3.isSelected());
				if(!chckbxWaiter_3.isSelected()) {
					textArea_3.setText("Server 3 is deactiavted");
					textArea_3.setBackground(Color.yellow);
					Logger.updateLog("Server 3 is deactivated");
				} else if (chckbxWaiter_3.isSelected()) {
					textArea_3.setText("Server 3 has been actiavted");
					textArea_3.setBackground(Color.GREEN);
					Logger.updateLog("Server 3 has been actiavted");
				}

			}
		});
		chckbxWaiter_4.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				oProcessor.isStaffActive(4, chckbxWaiter_4.isSelected());				
				if(!chckbxWaiter_4.isSelected()) {
					textArea_4.setText("Server 4 is deactiavted");
					textArea_4.setBackground(Color.yellow);
					Logger.updateLog("Server 4 is deactivated");
				} else if (chckbxWaiter_4.isSelected()) {
					textArea_4.setText("Server 4 has been actiavted");
					textArea_4.setBackground(Color.GREEN);
					Logger.updateLog("Server 4 has been actiavted");
				}
			}
		});
		/* commented below if condition because its never called, added above to listen to item selected cases
		if(chckbxWaiter_3.isSelected() && chckbxWaiter_4.isSelected()) {
			System.out.println("activating both 3 and 4");
			oProcessor.isStaffActive(3, true);
			oProcessor.isStaffActive(4, true);
		} else if (chckbxWaiter_3.isSelected()) {
			System.out.println("activating both 3");

			oProcessor.isStaffActive(3, true);
			oProcessor.isStaffActive(4, false);
		} else if (chckbxWaiter_4.isSelected()) {
			System.out.println("activating both  4");

			oProcessor.isStaffActive(3, false);
			oProcessor.isStaffActive(4, true);
		}
		*/
		Label label = new Label("Server 1");
		label.setBounds(411, 81, 62, 22);
		contentPane.add(label);
		
		Label label_1 = new Label("Server 2");
		label_1.setBounds(411, 187, 62, 22);
		contentPane.add(label_1);
		
		btnLog = new JButton("Start Simulation");
		btnLog.setBounds(10, 10, 142, 46);
		contentPane.add(btnLog);
		
		JLabel lblOrders = new JLabel("Orders In the Queue");
		lblOrders.setBounds(104, 197, 146, 14);
		contentPane.add(lblOrders);
		
		JLabel lblOrderId = new JLabel();//"Order ID"
		lblOrderId.setBounds(10, 197, 46, 14);
		contentPane.add(lblOrderId);
		
		JLabel lblOrderStatus = new JLabel();//"Order Status"
		lblOrderStatus.setBounds(258, 197, 86, 14);
		contentPane.add(lblOrderStatus);
		
		JLabel lblOrderSource = new JLabel();//"Order Source"
		lblOrderSource.setBounds(122, 197, 70, 14);
		contentPane.add(lblOrderSource);
		
		JLabel lblStaff = new JLabel();//"Staff"
		lblStaff.setBounds(202, 197, 46, 14);
		contentPane.add(lblStaff);
		
		JLabel lblItems = new JLabel();//"Items"
		lblItems.setBounds(66, 197, 46, 14);
		contentPane.add(lblItems);
		
		
		textArea_1.setEditable(false);
		textArea_1.setBounds(479, 28, 380, 111);
		contentPane.add(textArea_1);
		
		
		textArea_2.setEditable(false);
		textArea_2.setBounds(479, 137, 380, 111);
		contentPane.add(textArea_2);
		
		
		textArea_3.setEditable(false);
		textArea_3.setBounds(479, 250, 380, 111);
		contentPane.add(textArea_3);
		
		
		textArea_4.setEditable(false);
		textArea_4.setBounds(479, 364, 380, 101);
		contentPane.add(textArea_4);
		
		btnLog.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent a) {
				//System.out.println("Start SImulator pressed");
				try {
					btnLog.setEnabled(false);
					PublicController.startSimulator();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	public void startSimulation (ActionListener al) {
		btnLog.addActionListener(al);
	}
	
	
	private String menuOutput() {
		return PublicController.getMenuAdmin();
	}

	private String soldItemsOutput() {
		return PublicController.getItemsSoldAdmin();
	}

	private String totalOrdersOutput() {
		String outputTotalOrders = "Total number of orders =   ";
		outputTotalOrders += PublicController.getTotalOrders();
		return outputTotalOrders;
	}

	private String totalIncomeOutput() {
		String outputTotalIncome = "Total Income =   ";
		outputTotalIncome += PublicController.getTotalIncome();
		outputTotalIncome += "  AED";
		return outputTotalIncome;
	}

	private String fullReportOutput() {
		String output = "";
		output += menuOutput() + "\n\n" + soldItemsOutput() + "\n\n" + totalOrdersOutput() + "\n\n"
				+ totalIncomeOutput();
		return output;
	}

	/**
	 * writes supplied text to file
	 * 
	 * @param filename the name of the file to be written to
	 * @param report   the text to be written to the file
	 */
	//startSimulation
	public void update(Observable o, Object args) {
		String orderlist; 
		
		
		//get waiter status and print what he is doing
		if(oProcessor.getStaffState(1) == "PROCESSING") {
						
			waiter1 = "Server 1  is processing " + PublicController.getCustomerProcessedByStaff(1) +"'s order with items:" ;
			waiter1+= "\n"+PublicController.getCurrentOrder(1);
			textArea_1.setText(waiter1);
			textArea_1.setBackground(Color.PINK);
			Logger.updateLog(waiter1);

		} else {
			textArea_1.setText("Server 1 waiting for customers");
			textArea_1.setBackground(Color.GREEN);
			Logger.updateLog("Server 1 waiting for customers");

		}
		
		if(oProcessor.getStaffState(2) == "PROCESSING") {
			waiter2 = "Server 2 is processing " + PublicController.getCustomerProcessedByStaff(2) +"'s order with items:" ;
			waiter2 += "\n"+PublicController.getCurrentOrder(2);
			//System.out.println(waiter2);
			textArea_2.setText(waiter2);
			textArea_2.setBackground(Color.PINK);
			Logger.updateLog(waiter2);
			
		} else {
			textArea_2.setText("Server 2 waiting for customers");
			textArea_2.setBackground(Color.GREEN);
			Logger.updateLog("Server 2 waiting for customers");

		}
		if(oProcessor.getStaffState(3) == "PROCESSING" && chckbxWaiter_3.isSelected()) {
			waiter3 = "Server 3 is processing " + PublicController.getCustomerProcessedByStaff(3) +"'s order with items:" ;
			waiter3 += "\n"+PublicController.getCurrentOrder(3);
			textArea_3.setText(waiter3);
			textArea_3.setBackground(Color.PINK);
			Logger.updateLog(waiter3);
			
		} else if (chckbxWaiter_3.isSelected()){
			textArea_3.setText("Server 3 waiting for customers");
			textArea_3.setBackground(Color.GREEN);
			Logger.updateLog("Server 3 waiting for customers");
			
		}
		if(oProcessor.getStaffState(4) == "PROCESSING" && chckbxWaiter_4.isSelected()) {
			waiter4 = "Server 4 is processing " + PublicController.getCustomerProcessedByStaff(4) +"'s order with items:" ;
			waiter4 += "\n"+PublicController.getCurrentOrder(4);
			textArea_4.setText(waiter4);
			textArea_4.setBackground(Color.PINK);
			Logger.updateLog(waiter4);
			
		}
		else if (chckbxWaiter_4.isSelected()){
			textArea_4.setText("Server 4 waiting for customers");
			textArea_4.setBackground(Color.GREEN);
			Logger.updateLog("Server 4 waiting for customers");	
		}
		
		int numCustWaiting = PublicController.getOrderQueueSize();
		
		orderlist = "There are "+PublicController.getOrderQueueSize() + " customers waiting";
		orderlist = ""+ numCustWaiting + " Customers waiting";
		orderlist+="\n"+PublicController.getOrderQueueElemenets();
		textArea.setText(orderlist);

		if (numCustWaiting == 0 && oProcessor.isSimulatorStarted()) {
			Logger.updateLog("All customers served, closing shop");
			Logger.printLog(fullReportOutput());
			
			JOptionPane.showMessageDialog(this, "All customers served, closing shop! Details available in /logFile.txt");
			System.exit(0);
			
		}
		Logger.updateLog(orderlist);
	}
}
