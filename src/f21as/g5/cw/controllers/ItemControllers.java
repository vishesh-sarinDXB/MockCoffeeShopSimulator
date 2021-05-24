package f21as.g5.cw.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import f21as.g5.cw.exceptions.DuplicateIDException;
import f21as.g5.cw.exceptions.InvalidIDException;
import f21as.g5.cw.models.Item;
import f21as.g5.cw.models.ItemMap;

class ItemControllers {
	
	private static final ItemMap itemMap = new ItemMap();
	
	static void readCSVItems(String fn) {
			
			String line = "";
			String splitBy = ",";
			
			try {
				
				BufferedReader br = new BufferedReader(new FileReader(fn));
	
				while ((line = br.readLine()) != null) {
					try {
						
						String[] itemList = line.split(splitBy);
						Item currItem = new Item(itemList[0], itemList[1], itemList[2], Double.parseDouble(itemList[3]));
						itemMap.addItem(currItem);
						
					} catch(InvalidIDException | DuplicateIDException | NumberFormatException ex) {
						System.err.println(ex.getMessage());
					}
				}
	
				br.close();	
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.out.println("There is some issue with ItemsCSV. Without it resolved there will be no menu. Exiting application");
				System.exit(1);
			}
		}
	
	static Item getItem(String iD) {
		
		return itemMap.getItem(iD);
		
	}
	
	static String getIDFromName(String name) {
		return itemMap.getIDFromName(name);
	}
	
	static String getMenuAdmin() {
		Set<String> keySet = itemMap.getItemMapIDs();
		String menuAdmin = "The Menu:\nItem                      Price\n\n";
		for (String key : keySet) {
			String name = itemMap.getNameItem(key);
			String cost = String.valueOf(itemMap.getCostItem(key));
			menuAdmin += String.format("%-26s", name) + cost + "\n";
		}
		return menuAdmin;
	}
	
	static String getItemsSoldAdmin() {
		Set<String> keySet = itemMap.getItemMapIDs();
		String itemsSoldAdmin = "Items sold statisitics:\nItem             Number of times sold\n\n";
		for (String key : keySet) {
			String name = itemMap.getNameItem(key);
			String amount = String.format("%-6s", String.valueOf(itemMap.getAmountSoldItem(key)));
			itemsSoldAdmin += String.format("%-26s", name) + amount + "\n";
		}
		return itemsSoldAdmin;
	}
	
	static ArrayList<String> getMenu() {
		
		String nameCost;
		ArrayList<String> menu = new ArrayList<String>();
		Set<String> keySet = itemMap.getItemMapIDs();
		
		for (String key : keySet) {
			nameCost = itemMap.getNameItem(key) + ", " + Double.toString(itemMap.getCostItem(key));
			menu.add(nameCost);
		}
		
		return menu;
		
	}
	
}
