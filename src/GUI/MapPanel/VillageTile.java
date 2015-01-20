package GUI.MapPanel;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import Crucial.Constants;
import DataStructure.NodeVillage;

public class VillageTile extends Tile {
	
	public int id;
	public Color color;
	public boolean walkable;
	public Image isoImage;
	public int decalImage;
	
	private NodeVillage villageReference;
	
	public VillageTile(int xCoord, int yCoord,String name){
		super(3,Constants.ORANGE,false, TileTypes.getImage(Constants.LOC_VILLAGE), 5);
		
		
		try {
			this.isoImage = ImageIO.read(Constants.LOC_VILLAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.villageReference = new NodeVillage(xCoord,yCoord,name);
	}
	
	public NodeVillage getVillageReference(){
		return this.villageReference;
	}

}
