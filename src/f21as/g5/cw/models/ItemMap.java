package f21as.g5.cw.models;
import java.util.*;
import java.util.Map.Entry;

import f21as.g5.cw.exceptions.DuplicateIDException;


public class ItemMap {
	
	private HashMap<String, Item> itemMap = new HashMap<String, Item>();
	
	public ItemMap() {
		
	}
	
	public ItemMap(HashMap<String, Item> itemMap) {
		this.itemMap = itemMap;
	}
	
	public void addItem(Item item) throws DuplicateIDException{
		String iD = item.getID();
		if(this.itemMap.containsKey(iD)) {
			if(item.getName().equals(this.itemMap.get(iD).getName()) && item.getCost() == this.itemMap.get(iD).getCost()) {
				throw new DuplicateIDException(iD);
			} else {
				item.setID(iD.substring(0, 4) + Integer.toString((int)(Math.random() * 1000)));
			}
			
		}
		this.itemMap.put(item.getID(), item);
	}
	
	public Item getItem(String iD) {
		if(!this.itemMap.containsKey(iD)) {
			throw new NullPointerException("iD provided doesnt belong to any item");
		}
		return this.itemMap.get(iD);
	}
	
	public double getCostItem(String iD) {
		if(!this.itemMap.containsKey(iD)) {
			throw new NullPointerException("iD provided doesnt belong to any item");
		}
		return this.itemMap.get(iD).getCost();

	}
	
	public String getNameItem(String iD) {
		
		if(!this.itemMap.containsKey(iD)) {
			throw new NullPointerException("iD provided doesnt belong to any item");
		}
		return this.itemMap.get(iD).getName();
		
	}
	
	public String getTypeItem(String iD) {
		if(!this.itemMap.containsKey(iD)) {
			throw new NullPointerException("iD provided doesnt belong to any item");
		}
		return this.itemMap.get(iD).getType();
	}
	
	public String getIDFromName(String name) {

		for(Entry<String, Item> item : itemMap.entrySet()) {
			if (item.getValue().getName().equals(name)) {
				return item.getValue().getID();
			}
		}
		
		throw new NullPointerException("Name does not exist in ItemMap");
		
	}
	
	public int getAmountSoldItem(String iD) {
		return this.itemMap.get(iD).getAmountSold();
	}
	
	public Set<String>  getItemMapIDs() { 
		return this.itemMap.keySet();
	}
	
	
	public boolean isMapCreated(String iD) {
		return this.itemMap.containsKey(iD);
	}
}
