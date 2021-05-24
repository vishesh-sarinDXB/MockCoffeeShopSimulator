package f21as.g5.cw.views;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.*;

import javax.swing.*;

//import f21as.g5.cw.models.ItemMap;
//import f21as.g5.cw.models.Order;
//import f21as.g5.cw.models.OrderMap;
import f21as.g5.cw.controllers.PublicController;

/**
 * This GUI is for listing the Competitors By ID or Name or Score. It filters
 * also by Game or Gender.
 */
public class AdminGUI extends JFrame implements ActionListener {
//	public AdminGUI() {
//	}

	// The Competitor list to be searched.
//	private ItemMap itemMap;
//	private OrderMap orderMap;
//	private Order order;
	
	// GUI components
	private JCheckBox comboBox;
	private JCheckBox breakfastBox;
	private JCheckBox lunchBox;
	private JTextField comboOffer = new JTextField("20"); // comboOffer = new JTextField("20");
	private JTextField breakfastOffer = new JTextField("15");
	private JTextField lunchOffer = new JTextField("10");
	private JComboBox comboStartHours, comboStartMinutes, comboEndHours, comboEndMinutes;
	private JComboBox breakfastStartHours, breakfastStartMinutes, breakfastEndHours, breakfastEndMinutes;
	private JComboBox lunchStartHours, lunchStartMinutes, lunchEndHours, lunchEndMinutes;
	
	private JScrollPane scrollList;
	
	private JButton menu, soldItems, close, totalOrders, totalIncome, fullReport, activateOffers;
	private JTextArea displayList;

	/**
	 * Create the frame with its panels.
	 * 
	 * @param list The Competitor list to be searched.
	 */
	public AdminGUI(/*ItemMap itemMap, OrderMap orderMap, Order order*/) {
//		this.itemMap = itemMap;
//		this.orderMap = orderMap;
//		this.order = order;

		// set up window title
		setTitle("Admin Control");
		// disable standard close button
		setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		setupSouthPanel();
		setupNorthPanel();
		setupCenterPanel();
		// set location of the window
		//setLocation(100, 740);
		setLocation(100, 540);
		// pack and set visible
		pack();
		setVisible(true);

		comboOffer.setText("20");
		breakfastOffer.setText("15");
		lunchOffer.setText("10");

	}

	private void setupCenterPanel() {
		displayList = new JTextArea(15, 20);
		displayList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		displayList.setEditable(false);
		scrollList = new JScrollPane(displayList);
		//getContentPane().add(scrollList, BorderLayout.CENTER);
	}

	private void setupSouthPanel() {
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new GridLayout(1, 3));
	}

	private void setupNorthPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(5, 15)); // (6,3)
		
		activateOffers = new JButton("Activate Offers");
		activateOffers.addActionListener(this);
		
		String[] hoursList = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", 
				              "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				              "20", "21", "22", "23"};
		
		String[] minutesList = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", 
	                            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
	                            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
	                            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
	                            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
	                            "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
		
		comboBox = new JCheckBox("Combo", true);
		comboBox.addActionListener(this);
		
		comboStartHours = new JComboBox(hoursList);
		comboStartMinutes = new JComboBox(minutesList);
		comboEndHours = new JComboBox(hoursList);
		comboEndMinutes = new JComboBox(minutesList);
		
		breakfastBox = new JCheckBox("Breakfast", true);
		breakfastBox.addActionListener(this);
		
		breakfastStartHours = new JComboBox(hoursList);
		breakfastStartMinutes = new JComboBox(minutesList);
		breakfastEndHours = new JComboBox(hoursList);
		breakfastEndMinutes = new JComboBox(minutesList);
		

		lunchBox = new JCheckBox("Lunch", true);
		lunchBox.addActionListener(this);
		
		lunchStartHours = new JComboBox(hoursList);
		lunchStartMinutes = new JComboBox(minutesList);
		lunchEndHours = new JComboBox(hoursList);
		lunchEndMinutes = new JComboBox(minutesList);

		menu = new JButton("Menu");
		menu.addActionListener(this);

		soldItems = new JButton("Sold Items");
		soldItems.addActionListener(this);

		close = new JButton("CLOSE");
		close.addActionListener(this);

		totalOrders = new JButton("Total Orders");
		totalOrders.addActionListener(this);

		totalIncome = new JButton("Total Income");
		totalIncome.addActionListener(this);

		fullReport = new JButton("Full Report");
		fullReport.addActionListener(this);
		
		northPanel.add(new JLabel("Active Offer"));
		northPanel.add(new JLabel("Offer Percentage"));
		northPanel.add(new JLabel("   "));
		northPanel.add(new JLabel("Start Time"));
		northPanel.add(new JLabel("   "));
		northPanel.add(new JLabel("   "));
		northPanel.add(new JLabel("End Time"));
		northPanel.add(new JLabel("   "));
		northPanel.add(comboBox);
		comboOffer = new JTextField(5);
		northPanel.add(comboOffer);
		northPanel.add(new JLabel("  %"));
		northPanel.add(new JLabel("Hours"));//
		northPanel.add(new JLabel("Minutes"));//
		//northPanel.add(new JLabel("   "));
		//northPanel.add(new JLabel("   "));
		//northPanel.add(comboStartHours);
		//northPanel.add(comboStartMinutes);
		northPanel.add(new JLabel("   "));
		northPanel.add(new JLabel("Hours"));//
		northPanel.add(new JLabel("Minutes"));//
		//northPanel.add(new JLabel("   "));
		//northPanel.add(new JLabel("   "));
		//northPanel.add(comboEndHours);
		//northPanel.add(comboEndMinutes);
		northPanel.add(breakfastBox);
		breakfastOffer = new JTextField(5);
		northPanel.add(breakfastOffer);
		northPanel.add(new JLabel("  %"));
		northPanel.add(breakfastStartHours);
		northPanel.add(breakfastStartMinutes);
		northPanel.add(new JLabel("   "));
		northPanel.add(breakfastEndHours);
		northPanel.add(breakfastEndMinutes);

		northPanel.add(lunchBox);
		lunchOffer = new JTextField(5);
		northPanel.add(lunchOffer);
		northPanel.add(new JLabel("  %"));
		northPanel.add(lunchStartHours);
		northPanel.add(lunchStartMinutes);
		northPanel.add(new JLabel("   "));
		northPanel.add(lunchEndHours);
		northPanel.add(lunchEndMinutes);
		///////////////////////////////////
		
		////////////////////////////////////
		northPanel.add(new JLabel("   "));
		northPanel.add(new JLabel("   "));
		northPanel.add(new JLabel("   "));
		northPanel.add(activateOffers);
		northPanel.add(new JLabel("   "));
		northPanel.add(new JLabel("   "));
		northPanel.add(new JLabel("   "));
		northPanel.add(new JLabel("   "));
		////////////////////////////////////
		//northPanel.add(activateOffers);
		////northPanel.add(new JLabel("   "));
		//northPanel.add(menu);
		////northPanel.add(new JLabel("   "));
		//northPanel.add(soldItems);
		//
		//northPanel.add(totalOrders);
		////northPanel.add(new JLabel("   "));
		//northPanel.add(totalIncome);
		////northPanel.add(new JLabel("   "));
		//northPanel.add(fullReport);
		//
		////northPanel.add(new JLabel("   "));
		//northPanel.add(new JLabel("   "));
		//northPanel.add(close);
		getContentPane().add(northPanel, BorderLayout.NORTH);
	}

	// come here when button is clicked
	// find which button and act accordingly

	public void actionPerformed(ActionEvent e) {
		int startHours, startMinutes, endHours, endMinutes;
		if (comboBox.isSelected()) {
			PublicController.setComboFlag(true);
//			order.setComboFlag(true);
			PublicController.setComboValue((int) Integer.parseInt(comboOffer.getText()));
			
			startHours = (int) Integer.parseInt((String) comboStartHours.getSelectedItem());
			startMinutes = (int) Integer.parseInt((String) comboStartMinutes.getSelectedItem());
			endHours = (int) Integer.parseInt((String) comboEndHours.getSelectedItem());
			endMinutes = (int) Integer.parseInt((String) comboEndMinutes.getSelectedItem());
			
			//PublicController.setComboTime(startHours, startMinutes, endHours, endMinutes);
		} else {
			PublicController.setComboFlag(false);
		}

		if (breakfastBox.isSelected()) {
			PublicController.setBreakfastFlag(true);
			PublicController.setBreakfastValue((int) Integer.parseInt(breakfastOffer.getText()));
			
			startHours = (int) Integer.parseInt((String) breakfastStartHours.getSelectedItem());
			startMinutes = (int) Integer.parseInt((String) breakfastStartMinutes.getSelectedItem());
			endHours = (int) Integer.parseInt((String) breakfastEndHours.getSelectedItem());
			endMinutes = (int) Integer.parseInt((String) breakfastEndMinutes.getSelectedItem());
			
			PublicController.setBreakfastTime(startHours, startMinutes, endHours, endMinutes);
			
		} else {
			PublicController.setBreakfastFlag(false);
		}

		if (lunchBox.isSelected()) {
			PublicController.setLunchFlag(true);
			PublicController.setLunchValue((int) Integer.parseInt(lunchOffer.getText()));
			
			startHours = (int) Integer.parseInt((String) lunchStartHours.getSelectedItem());
			startMinutes = (int) Integer.parseInt((String) lunchStartMinutes.getSelectedItem());
			endHours = (int) Integer.parseInt((String) lunchEndHours.getSelectedItem());
			endMinutes = (int) Integer.parseInt((String) lunchEndMinutes.getSelectedItem());
			
			PublicController.setLunchTime(startHours, startMinutes, endHours, endMinutes);
			
		} else {
			PublicController.setLunchFlag(true);
		}

		if (e.getSource() == menu) {
			displayList.setText(menuOutput());

		} else if (e.getSource() == soldItems) {
			displayList.setText(soldItemsOutput());

		} else if (e.getSource() == totalOrders) {
			displayList.setText(totalOrdersOutput());

		} else if (e.getSource() == totalIncome) {
			displayList.setText(totalIncomeOutput());

		} else if (e.getSource() == fullReport) {

			displayList.setText(fullReportOutput());
			
		} else if (e.getSource() == activateOffers) {
			PublicController.setComboValue((int) Integer.parseInt(comboOffer.getText()));
			PublicController.setBreakfastValue((int) Integer.parseInt(breakfastOffer.getText()));
			PublicController.setLunchValue((int) Integer.parseInt(lunchOffer.getText()));

		} else if (e.getSource() == close) {
			String fp = "report-FullDetails-output.txt";
			writeToFile(fp, fullReportOutput());
			Logger.printLog(fp);

			JOptionPane.showMessageDialog(this,
					"Full detailed report is saved in \"report-FullDetails-output.txt\".\n                                             Goodbye!");
			System.exit(0);
		}
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
		String output = menuOutput() + "\n\n" + soldItemsOutput() + "\n\n" + totalOrdersOutput() + "\n\n"
				+ totalIncomeOutput();
		return output;
	}

	/**
	 * writes supplied text to file
	 * 
	 * @param filename the name of the file to be written to
	 * @param report   the text to be written to the file
	 */
	public void writeToFile(String filename, String report) {
		FileWriter fw;
		try {
			fw = new FileWriter(filename);
			fw.write(report);
			fw.close();
		}
		// message and stop if file not found
		catch (FileNotFoundException fnf) {
			System.out.println(filename + " not found ");
			System.exit(0);
		}
		// stack trace here because we don't expect to come here
		catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
		}
	}
}
