package DataStructure;

import Entity.Village;

public class NodeVillage implements NodeInterface {
	
	private Village data;
	private int[] coordinates = new int[2];
	
	public NodeVillage(int xCoord, int yCoord, String name){
		this.data = new Village(name);
		this.coordinates[0] = xCoord;
		this.coordinates[1] = yCoord;
	}
	
	public Village getData(){
		return this.data;
	}
}
