package GUI.MapPanel;

import java.awt.Color;
import java.awt.Image;

import DataStructure.NodeVillage;

public class Tile {

	public int id;
	public Color color;
	public boolean walkable;
	public Image isoImage;
	public int decalImage;
	
	private NodeVillage villageReference = null;
	
	public Tile(int id,Color color,Boolean walkable, Image isoImage, int decalImage){
		this.id = id;
		this.color = color;
		this.walkable = walkable;
		this.isoImage = isoImage;
		this.decalImage = decalImage;
	}
	
	public NodeVillage getVillageReference(){
		return this.villageReference;
	}
	
}
