package f21as.g5.cw.views;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//import f21as.g5.cw.exceptions.InvalidIDException;
//import f21as.g5.cw.models.Item;
//import f21as.g5.cw.models.ItemMap;
//import f21as.g5.cw.models.Order;
//import f21as.g5.cw.models.OrderMap;
import f21as.g5.cw.controllers.PublicController;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.util.ArrayList;

public class Customer extends JFrame implements ListSelectionListener {

	private JPanel contentPane;
	private JTextField totBeforeDisText;
	private JTextField totAfterDisText;
	private JTextField OfferText;
	private double cost ;
	private String ord;

	String split;
	JList <String> iList;
	JList <String> oList;
	String orders[]= {""};
	ArrayList <String> items;
	private JTextField textField;
	
	public Customer() {

		//GUI elements creation
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 150, 516, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblItems = new JLabel("Menu");
		lblItems.setBounds(95, 0, 64, 14);
		contentPane.add(lblItems);
		
		JLabel lblCart = new JLabel("Cart");
		lblCart.setBounds(393, 0, 42, 14);
		contentPane.add(lblCart);
		
		JLabel lblOfferApplied = new JLabel("Offer Applied");
		lblOfferApplied.setBounds(152, 364, 104, 14);
		contentPane.add(lblOfferApplied);
		
		OfferText = new JTextField();
		OfferText.setEditable(false);
		OfferText.setBounds(291, 358, 102, 20);
		contentPane.add(OfferText);
		OfferText.setColumns(10);
		
		iList = new JList<String>();
		iList.setBounds(35, 25, 142, 244);
		contentPane.add(iList);
		iList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		iList.addListSelectionListener(this);
		
		oList = new JList<String>();
		oList.setBounds(339, 25, 142, 244);
		contentPane.add(oList);
		
		JLabel lblTotalBeforeDiscount = new JLabel("Total Before discount");
		lblTotalBeforeDiscount.setBounds(152, 301, 129, 14);
		contentPane.add(lblTotalBeforeDiscount);
		
		
		totBeforeDisText = new JTextField();
		totBeforeDisText.setEditable(false);
		totBeforeDisText.setBounds(291, 298, 102, 20);
		contentPane.add(totBeforeDisText);
		totBeforeDisText.setColumns(10);
		
		JLabel lblTotalAfterDiscount = new JLabel("Total After Discount");
		lblTotalAfterDiscount.setBounds(152, 332, 136, 14);
		contentPane.add(lblTotalAfterDiscount);
		
		totAfterDisText = new JTextField();
		totAfterDisText.setEditable(false);
		totAfterDisText.setBounds(291, 329, 102, 20);
		contentPane.add(totAfterDisText);
		totAfterDisText.setColumns(10);
		
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textField.setBounds(183, 92, 156, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
				items = PublicController.getMenu();
				String itemlist = "";
								
				DefaultListModel<String> dim = new DefaultListModel<String>();
				for (int i = 0; i < items.size(); i++)
				{
					itemlist=items.get(i)+"\n";
					dim.addElement(itemlist);
					iList.setModel(dim);	
				}
		
		
		
		//creating a new list for the orderlist
		DefaultListModel<String> dim2 = new DefaultListModel<String>();
		JButton btnAdd = new JButton(">> Add");
		btnAdd.setBounds(191, 123, 119, 31);
		btnAdd.setFont(new Font("Dubai", Font.PLAIN, 13));
		contentPane.add(btnAdd);
		
		//the method when the button add to oderlist been clicked
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				//insert in olist;
				
				dim2.addElement(iList.getSelectedValue());
				if(iList.getSelectedValue()!=null)
				{
					
					PublicController.addToOrder(ord);
					cost = PublicController.getCostNoDiscount();
				totBeforeDisText.setText(String.valueOf(cost));}
				else {Component JFrame = null;
				JOptionPane.showMessageDialog(JFrame,"Please Select an Item","Alert",JOptionPane.WARNING_MESSAGE);;};
				oList.setModel(dim2);
				
			}
		});
				
		//the method when the button remove from orderlist been clicked	
		JButton btnRemove = new JButton("<< Remove");
		btnRemove.setFont(new Font("Dubai", Font.PLAIN, 13));
		btnRemove.setBounds(191, 179, 119, 31);
		contentPane.add(btnRemove);	
		btnRemove.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			try {
				if(cost>=0.0 ) //to assure the counter does not go to negative
				{	
					int index = iList.getSelectedIndex();
					if(index != -1) //to assure that item is selected 
					{
					

			if(iList.getSelectedValue().contains(/*iMap.getNameItem(itemid)*/ord)) //assure the selected item name exist in the orderlist
			{
				//remove the item from the orderlist then and update the cost
				dim2.removeElement(iList.getSelectedValue());
				PublicController.removeFromOrder(ord);
				cost = PublicController.getCostNoDiscount();
				totBeforeDisText.setText(String.valueOf(cost));
			}
			
					}
				}
				else {Component JFrame = null;
				JOptionPane.showMessageDialog(JFrame,"Cart Empty ","Alert",JOptionPane.WARNING_MESSAGE);}
			
			oList.setModel(dim2);
			}
			
			
			catch (NullPointerException er) {
				Component JFrame = null;
				JOptionPane.showMessageDialog(JFrame,"Please ensure item ","Alert",JOptionPane.WARNING_MESSAGE);
			} catch(IndexOutOfBoundsException ee) {
				Component JFrame = null;
				JOptionPane.showMessageDialog(JFrame,"Item not available in the CART ","Alert",JOptionPane.WARNING_MESSAGE);
				
			}
		}
	});
		
		//add the full order to the orderMap
		JButton btnNewButton_1 = new JButton("Bill");
		btnNewButton_1.setBounds(406, 301, 94, 77);
		btnNewButton_1.setFont(new Font("Dubai", Font.PLAIN, 13));
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				totBeforeDisText.setText(String.valueOf(cost));
				totAfterDisText.setText(String.valueOf(PublicController.getTotalCost()));
				OfferText.setText(String.valueOf(PublicController.getOfferName()));
				Component JFrame = null;
				JOptionPane.showMessageDialog(JFrame,"The total cost is: " + String.valueOf(PublicController.getTotalCost()),"Alert",JOptionPane.INFORMATION_MESSAGE);
				PublicController.closeOrder();
				
			}
		});
		
		
		
	}
	
	public void valueChanged(ListSelectionEvent ek) {
		// TODO Auto-generated method stub
		int index = iList.getSelectedIndex();
		if(index != -1) {
		try{
			String value = iList.getSelectedValue();
			String[] parts = value.split(",");
			split = parts[0];
			this.ord=  split;
		} 

			
		 catch(IndexOutOfBoundsException ep) {
			Component JFrame = null;
			JOptionPane.showMessageDialog(JFrame,"Error occured ","Alert",JOptionPane.WARNING_MESSAGE);
			ep.printStackTrace();
			
			}textField.setText(" Item Selected: " + items.get(index));
		
	
}}
}
